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
import project.Drawer.MainMenuDrawer;
import project.Drawer.SeaCureMenuDrawer;
import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.HTTPDataHandler;

import project.deepwateroiltools_001.Fragments.SeaCure.Fragment_procedure_checklist;
import project.deepwateroiltools_001.Fragments.SeaCure.Fragment_procedure_general;
import project.deepwateroiltools_001.Fragments.SeaCure.Fragment_procedure_inp;
import project.dto.SeaCure_job;
import project.dto.service.ProcedureChecklist;
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
    private SeaCure_job seaCure_job;
    private ProcedureSlide currentProcedureSlide;
    private Fragment currentFragment;

    public SeaCure_job getSeaCure_job() {
        return seaCure_job;
    }

    public void setSeaCure_job(SeaCure_job seaCure_job) {
        this.seaCure_job = seaCure_job;
    }

    List<ProcedureSlide> procedureSlideList;
    List<ProcedureSlide> visitedProcuderSlides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FULL SCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sea_cure);


        SeaCure_job seaCure_job = new SeaCure_job();

        //get the user obj from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = new Gson().fromJson(extras.getString("user"), User.class);
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
        new SeaCure.RunDbQuery(this, url).execute();

    }

    public void startSeaCureProcedure(List<ProcedureSlide> procedureSlideList){
        this.procedureSlideList = procedureSlideList;

        seaCure_job = new SeaCure_job();
        seaCure_job.setStartDate(System.currentTimeMillis());
        seaCure_job.set_user_id(user.get_id());

        for (int i=0; i< procedureSlideList.size(); i++){
            if (procedureSlideList.get(i).getProcId() == 1){
                Fragment_procedure_img fragmentProcedureImg = new Fragment_procedure_img();
                fragmentProcedureImg.setProcedureImg((ProcedureImg)procedureSlideList.get(i));
                //fragmentProcedureImg.setProcedureSlideList(procedureSlideList);
                this.currentProcedureSlide = procedureSlideList.get(i);
                childId = procedureSlideList.get(i).getChildId();
                visitedProcuderSlides = new ArrayList<ProcedureSlide>() ;
                visitedProcuderSlides.add(procedureSlideList.get(i));
                loadFragment(fragmentProcedureImg);
                break;
            }
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
        //TODO comment out the line below for live version
        isValid = true;
        if  (isValid) {
            for (int i = 0; i < procedureSlideList.size(); i++) {
                if (childId == procedureSlideList.get(i).getProcId()) {
                    stepTo(procedureSlideList.get(i), true);
                    break;
                }
            }
        }
        else {
            //TODO otuput to user about invalid input
        }
    }

    public void stepPreviousProcedureSlide(){
        drawer.setSelection(-1);
        if (visitedProcuderSlides.size()>1) {
            visitedProcuderSlides.remove(visitedProcuderSlides.size() - 1);
            //stepTo(visitedProcuderSlides.get(visitedProcuderSlides.size() - 1));
          //  currentProcedureSlide = visitedProcuderSlides.get(visitedProcuderSlides.size() - 1);
            stepTo(visitedProcuderSlides.get(visitedProcuderSlides.size() - 1), false);
        }

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
        else if (procedureSlide.getClass().equals(ProcedureSlide.class)){
            Fragment_procedure_general fragment = new Fragment_procedure_general();
            currentFragment = fragment;
            fragment.setProcedureSlide(procedureSlide);
        }
          //just add the list the list in forward steps
        if (addToVisited) {
            visitedProcuderSlides.add(procedureSlide);
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

    private class RunDbQuery extends AsyncTask<String, Void, String> {
        String urlString= null;
        private ProgressDialog dialog;
        private List<ProcedureSlide> procedureSlides;
        public SeaCure activity;

        private RunDbQuery(SeaCure a, String urlString){
            this.urlString = urlString;
            this.activity = a;
            dialog = new ProgressDialog(SeaCure.this, R.style.DialogBoxStyle);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Connecting to the server...");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HTTPDataHandler httpDataHandler = new HTTPDataHandler();
            String stream = httpDataHandler.GetHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

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
                            }
                            else {
                                return null; //returning null will trigger Gson's default behavior
                            }
                        }
                    });



            Gson gson = builder.createGson();
            Type listType = new TypeToken<List<ProcedureSlide>>(){}.getType();
            procedureSlides =  gson.fromJson(s, listType);

            for (int i=0; i<procedureSlides.size(); i++){
                Log.d("CLASS TYPE", procedureSlides.get(i).getClass().toString());
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (!procedureSlides.isEmpty()) {
                activity.startSeaCureProcedure(procedureSlides);

            }
            else{
                //TODO error handling
            }
        }
    }
}
