package project.deepwateroiltools_001;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.crossfader.app.util.CrossfadeWrapper;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;
import project.Drawer.SeaCureMenuDrawer;
import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.HTTPDataHandler;

import project.deepwateroiltools.HTTP.PostNewJob;
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools.HTTP.RunDBQueryWithDialog;
import project.deepwateroiltools_001.Fragments.SeaCure.Fragment_procedure_checklist;
import project.deepwateroiltools_001.Fragments.SeaCure.Fragment_procedure_ddl;
import project.deepwateroiltools_001.Fragments.SeaCure.Fragment_procedure_general;
import project.deepwateroiltools_001.Fragments.SeaCure.Fragment_procedure_goto;
import project.deepwateroiltools_001.Fragments.SeaCure.Fragment_procedure_inp;
import project.dto.DotSerail;
import project.dto.SeaCure_job;
import project.dto.service.ProcedureChecklist;
import project.dto.service.ProcedureDdl;
import project.dto.service.ProcedureGoto;
import project.dto.service.ProcedureImg;
import project.dto.service.ProcedureInput;
import project.dto.service.ProcedureSlide;
import project.deepwateroiltools_001.Fragments.SeaCure.Fragment_procedure_img;
import project.dto.user.User;

public class SeaCure extends Activity {
    private int childId;
    private Drawer drawer = null;
    private MiniDrawer miniDrawer = null;
    private AccountHeader headerDrawer = null;
    private Crossfader crossFader = null;
    private SeaCureMenuDrawer seaCureMenuDrawer;
    private User user;
    private SeaCure_job seaCure_job, loadedSeaCureJob;
    private ProcedureSlide currentProcedureSlide;
    private Fragment currentFragment;
    private RunDBQueryWithDialog runDBQuery;
    private DotSerail dotSerail;


    List<ProcedureSlide> procedureSlideList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FULL SCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sea_cure);


        //get the user obj from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = new Gson().fromJson(extras.getString("user"), User.class);
            dotSerail = new Gson().fromJson(extras.getString("dotserial"), DotSerail.class);
            loadedSeaCureJob = new Gson().fromJson(extras.getString("loadedSeaCureJob"), SeaCure_job.class);
        }
        else{
            //TODO error handling
            Log.d("EEEh", "Not happening");
        }


        seaCureMenuDrawer = new SeaCureMenuDrawer(this, getApplicationContext(), user, savedInstanceState);
        headerDrawer = seaCureMenuDrawer.getHeader();
        DrawerBuilder builder = seaCureMenuDrawer.getDrawerBuilder();
        drawer = builder.buildView();
        miniDrawer = new MiniDrawer().withDrawer(drawer);
        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
        crossFader = seaCureMenuDrawer.getCrossFader(drawer, miniDrawer);
        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniDrawer.withCrossFader(new CrossfadeWrapper(crossFader));
        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

        String url = Common.getUrlSeaCureProcedure() + Common.getApiKey();

        runDBQuery = new RunDBQueryWithDialog(this, url, "Connecting to the server...");
        runDBQuery.setProcessListener(new ProcessListener() {
            @Override
            public void ProcessingIsDone(String result) {
                procedureSlideList = generateProcedureList(result);

//                for (int i=0; i<procedureSlideList.size(); i++){
//                    Log.d("CLASS TYPE", procedureSlideList.get(i).getClass().toString());
//                }

                setSeaCureJobValues();
                if (!procedureSlideList.isEmpty()) {
                    stepTo(getProcedureSlideById(seaCure_job.getSavedId()), loadedSeaCureJob == null ? true : false);
                }
                else{
                    Toast.makeText(getApplicationContext(), "An error occured, please try again later", Toast.LENGTH_SHORT);
                }
            }
        });

        runDBQuery.execute();

    }

    public List<ProcedureSlide> generateProcedureList(String result){
        //To create the extended class elements from the JSON response
        GsonFireBuilder builder = new GsonFireBuilder()
                .registerTypeSelector(ProcedureSlide.class, new TypeSelector<ProcedureSlide>() {
                    @Override
                    public Class<? extends ProcedureSlide> getClassForElement(JsonElement readElement) {
                        String type = readElement.getAsJsonObject().get("type").getAsString();
                        if(type.equals("ProcedureImg")){
                            return ProcedureImg.class;
                        } else if(type.equals("ProcedureInput")) {
                            return ProcedureInput.class;
                        } else if (type.equals("ProcedureChecklist")){
                            return ProcedureChecklist.class;
                        } else if (type.equals("ProcedureDdl")){
                            return ProcedureDdl.class;
                        } else if (type.equals("ProcedureGoto")){
                            return ProcedureGoto.class;
                        }
                        else {
                            return null; //returning null will trigger Gson's default behavior
                        }
                    }
                });
        Gson gson = builder.createGson();
        Type listType = new TypeToken<List<ProcedureSlide>>(){}.getType();
        return  gson.fromJson(result, listType);
    }

    public void setSeaCureJobValues(){
        seaCure_job = new SeaCure_job();
        if (loadedSeaCureJob == null) {

            seaCure_job.setStartDate(System.currentTimeMillis());
            seaCure_job.setToolType("SeaCure");
            seaCure_job.set_user_id(user.get_id().getOid());
            seaCure_job.setSavedId(1);
            seaCure_job.setVisited(new ArrayList<Integer>());

            //set the in dot serial numbers from the dotSerial object
            seaCure_job.setSn_in_DOT_SCR_IBH(dotSerail.getDOT_SCR_IBH());
            seaCure_job.setSn_in_DOT_SCR_INB(dotSerail.getDOT_SCR_INB());
            seaCure_job.setSn_in_DOT_SCR_PBR(dotSerail.getDOT_SCR_PBR());
            seaCure_job.setSn_in_DOT_SCR_TRB(dotSerail.getDOT_SCR_TRB());
        }
        else{
            seaCure_job = loadedSeaCureJob;
        }
    }


    public void stepNextProcedureSlide(){
        drawer.setSelection(-1);
        boolean isValid = true;
        if (currentProcedureSlide.getClass().equals(ProcedureInput.class)){
            Fragment_procedure_inp fragment = (Fragment_procedure_inp)currentFragment;
            isValid = fragment.isValid();
            fragment.saveValues();
        }

        if (currentProcedureSlide.getClass().equals(ProcedureDdl.class)){
            Fragment_procedure_ddl fragment = (Fragment_procedure_ddl)currentFragment;
            isValid = fragment.isAllSelected();
            fragment.saveValues();
        }
        //TODO comment out the line below for live version
        isValid = true;
        if  (isValid) {

            stepTo(getProcedureSlideById(childId), true);
        }
        else {
            //TODO otuput to user about invalid input
        }
    }

    public void stepPreviousProcedureSlide(){
        drawer.setSelection(-1);
        if (seaCure_job.getVisited().size()>1){
            seaCure_job.getVisited().remove(seaCure_job.getVisited().size()-1);
            stepTo(getProcedureSlideById(seaCure_job.getVisited().get(seaCure_job.getVisited().size()-1)), false);
        }
    }

    //Finds and returns the required procedure slide
    public ProcedureSlide getProcedureSlideById(int id){
        for (int i=0; i<procedureSlideList.size();i++){
            if (procedureSlideList.get(i).getProcId() == id){
                return procedureSlideList.get(i);
            }
        }
        return null;
    }

    public void stepTo(ProcedureSlide procedureSlide, Boolean addToVisited){
        currentProcedureSlide = procedureSlide;

        if (procedureSlide.getClass().equals(ProcedureImg.class)) {
            Fragment_procedure_img fragment = new Fragment_procedure_img();
            currentFragment = fragment;
            fragment.setProcedureImg((ProcedureImg) procedureSlide);
        }
        else if (procedureSlide.getClass().equals(ProcedureInput.class)){
            Fragment_procedure_inp fragment = new Fragment_procedure_inp();
            currentFragment = fragment;
            fragment.setProcedureSlide((ProcedureInput)procedureSlide);
        }
        else if (procedureSlide.getClass().equals(ProcedureChecklist.class)){
            Fragment_procedure_checklist fragment = new Fragment_procedure_checklist();
            currentFragment = fragment;
            fragment.setProcedureSlide((ProcedureChecklist)procedureSlide);
        }
        else if (procedureSlide.getClass().equals(ProcedureDdl.class)){
            Fragment_procedure_ddl fragment = new Fragment_procedure_ddl();
            currentFragment = fragment;
            fragment.setProcedureSlide((ProcedureDdl)procedureSlide);
        }
        else if (procedureSlide.getClass().equals(ProcedureGoto.class)){
            Fragment_procedure_goto fragment = new Fragment_procedure_goto();
            currentFragment = fragment;
            fragment.setProcedureSlide((ProcedureGoto)procedureSlide);
        }
        else if (procedureSlide.getClass().equals(ProcedureSlide.class)){
            Fragment_procedure_general fragment = new Fragment_procedure_general();
            currentFragment = fragment;
            fragment.setProcedureSlide(procedureSlide);
        }
          //just add to the list the list in forward steps
        if (addToVisited) {
            seaCure_job.getVisited().add(procedureSlide.getProcId());
        }
        childId = procedureSlide.getChildId();
        loadFragment(currentFragment);
    }


    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.crossfade_content, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    public void upLoadSeaCureJob(){
        String url = Common.getUrlSeaCureJobs() + Common.getApiKey() ;
        seaCure_job.setSavedId(currentProcedureSlide.getProcId());
        runDBQuery = new RunDBQueryWithDialog(this, url, "Saving details...", new Gson().toJson(seaCure_job));
        runDBQuery.setProcessListener(new ProcessListener() {
            @Override
            public void ProcessingIsDone(String result) {
                Toast.makeText(getApplicationContext(), "Successfully saved", Toast.LENGTH_SHORT);
            }
        });
        runDBQuery.execute();
    }

    public SeaCure_job getSeaCure_job() {
        return seaCure_job;
    }

    public void setSeaCure_job(SeaCure_job seaCure_job) {
        this.seaCure_job = seaCure_job;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

}
