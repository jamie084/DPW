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

import project.deepwateroiltools_001.Fragments.SeaCure.Fragment_procedure_inp;
import project.dto.service.ProcedureImg;
import project.dto.service.ProcedureInput;
import project.dto.service.ProcedureSlide;
import project.deepwateroiltools_001.Fragments.SeaCure.Fragment_procedure_img;
import project.dto.user.User;

public class SeaCure extends Activity {
    private int currentId;
    private int childId;
    private Drawer drawer = null;
    private MiniDrawer miniDrawer = null;
    private AccountHeader headerDrawer = null;
    private Crossfader crossFader = null;
    private User user;
    private ProcedureSlide currentProcedureSlide;
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

        //get the user obj from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = new Gson().fromJson(extras.getString("user"), User.class);
        }
        else{
            //TODO error handling
            Log.d("EEEh", "Not happening");
        }

        SeaCureMenuDrawer seaCureMenuDrawer = new SeaCureMenuDrawer(this, getApplicationContext(), user, savedInstanceState);
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
        for (int i=0; i< procedureSlideList.size(); i++){
            if (procedureSlideList.get(i).getProcId() == 1){
                Fragment_procedure_img fragmentProcedureImg = new Fragment_procedure_img();
                fragmentProcedureImg.setProcedureImg((ProcedureImg)procedureSlideList.get(i));
                //fragmentProcedureImg.setProcedureSlideList(procedureSlideList);
                childId = procedureSlideList.get(i).getChildId();
                visitedProcuderSlides = new ArrayList<ProcedureSlide>() ;
                visitedProcuderSlides.add(procedureSlideList.get(i));
                loadFragment(fragmentProcedureImg);
                break;
            }
        }
    }

    public void stepNextProcedureSlide(){

       // Log.d("next step called", String.valueOf(this.currentProcedureSlide.getProcId()));

        for (int i=0; i < procedureSlideList.size(); i++){
            if (childId == procedureSlideList.get(i).getProcId()){
                Log.d("nextStep success", procedureSlideList.get(i).getTitle());
                if (procedureSlideList.get(i).getClass().equals(ProcedureImg.class)) {
                    Fragment_procedure_img fragment = new Fragment_procedure_img();
                    fragment.setProcedureImg((ProcedureImg) procedureSlideList.get(i));
                    visitedProcuderSlides.add(procedureSlideList.get(i));
                    loadFragment(fragment);
                    childId = procedureSlideList.get(i).getChildId();
                    break;
                }
                else if (procedureSlideList.get(i).getClass().equals(ProcedureInput.class)){
                    Fragment_procedure_inp fragment = new Fragment_procedure_inp();
                    fragment.setProcedureSlide((ProcedureInput)procedureSlideList.get(i));
                    Log.d("Procedure input class", "INPUT class");
                    visitedProcuderSlides.add(procedureSlideList.get(i));
                    childId = procedureSlideList.get(i).getChildId();
                    loadFragment(fragment);

                    break;
                }
            }
        }
    }

    public void stepPreviousProcedureSlide(){
        Fragment_procedure_img fragment = new Fragment_procedure_img();
        fragment.setProcedureImg((ProcedureImg) visitedProcuderSlides.get(visitedProcuderSlides.size()-2));
        childId = visitedProcuderSlides.get(visitedProcuderSlides.size()-2).getChildId();
        visitedProcuderSlides.remove(visitedProcuderSlides.size()-1);
       // visitedProcuderSlides.add(procedureSlideList.get(i));
        loadFragment(fragment);
    }



    private void loadFragment(Fragment fragment) {
        drawer.setSelection(-1);
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
            //this.procedureSlides = procedureSlides;
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

            GsonFireBuilder builder = new GsonFireBuilder()
                    .registerTypeSelector(ProcedureSlide.class, new TypeSelector<ProcedureSlide>() {
                        @Override
                        public Class<? extends ProcedureSlide> getClassForElement(JsonElement readElement) {
                            String type = readElement.getAsJsonObject().get("type").getAsString();
                            if(type.equals("ProcedureImg")){
                                return ProcedureImg.class;
                            } else if(type.equals("ProcedureInput")) {
                                return ProcedureInput.class;
                            } else {
                                return null; //returning null will trigger Gson's default behavior
                            }
                        }
                    });

            Gson gson = builder.createGson();
            Type listType = new TypeToken<List<ProcedureSlide>>(){}.getType();
            procedureSlides =  gson.fromJson(s, listType);

            for (int i=0; i<procedureSlides.size(); i++){
                String type = procedureSlides.get(i).getClass().toString();
                Log.d("CLASS TYPE IN LIST", type);
            }
       //     ProcedureInput procedureInput = (ProcedureInput)procedureSlides.get(2);
     //       Log.d("LABELS", procedureInput.getLabelList().get(0).toString());
          //  Log.d(" First class is: " + procedureSlides.get(0).getClass().toString(), "SEcond class type is:" + procedureSlides.get(1).getClass().toString());


            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (!procedureSlides.isEmpty()) {
                activity.startSeaCureProcedure(procedureSlides);

            }
            else{

                Log.d("procedureSlides","no entries");
            }
        }
    }
}
