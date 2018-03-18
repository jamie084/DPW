package project.deepwateroiltools_001;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

import java.io.File;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private Uri imageUri;

    private static final int REQUEST_TAKE_PHOTO = 1888;


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

        //Touch event handling, closes the miniDrawer in case of touch event
        findViewById(R.id.crossfade_content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (crossFader.isCrossFaded()){
                    crossFader.crossFade();
                }
                return true;
            }
        });


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

        //url to seacure procedure db entries
        String url = Common.getUrlSeaCureProcedure() + Common.getApiKey();

        //run an async method to populate the procedureslide list
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

    //sets the default values on the seacure job object
    public void setSeaCureJobValues(){
        seaCure_job = new SeaCure_job();
        //if there is no loaded job
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
          //add to the list the list in forward steps
        if (addToVisited) {
            seaCure_job.getVisited().add(procedureSlide.getProcId());
        }
        childId = procedureSlide.getChildId();
        loadFragment(currentFragment);
    }

    //triggered by the drawer camera btn, verifies the app permissions if granted call startCameraInent function
    public void openCamera(){
        drawer.setSelection(-1);
        StrictMode.VmPolicy.Builder newbuilder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(newbuilder.build());
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO);
        }
        else{
            startCameraIntent();
        }

    }

    //Start a camera intent with a default saving path (pictures dir) and filename (username and current date&time)
    public void startCameraIntent(){
        Intent cameraIntent = new Intent( android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.HH:mm");
        String date = formatter.format(new Date());
        String userName = user.getUserInfo().getFullName();
        File photo = new File(path,  userName + "_" + date + ".jpg");
        imageUri = FileProvider.getUriForFile(SeaCure.this,
                BuildConfig.APPLICATION_ID + ".provider",
                photo);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,  Uri.fromFile(photo));

        startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction.addToBackStack(null);
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.crossfade_content, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    public void finishProcedure(){
        super.onBackPressed();
    }

    //create a db connection and uploads/updates the seacure job entry
    public void upLoadSeaCureJob(){
        drawer.setSelection(-1);
        String url = Common.getUrlSeaCureJobs() + Common.getApiKey() ;
        seaCure_job.setSavedId(currentProcedureSlide.getProcId());
        runDBQuery = new RunDBQueryWithDialog(this, url, "Saving details...", new Gson().toJson(seaCure_job));
        runDBQuery.setProcessListener(new ProcessListener() {
            @Override
            public void ProcessingIsDone(String result) {
                Toast.makeText(getApplicationContext(), "Successfully saved", Toast.LENGTH_SHORT).show();
            }
        });
        runDBQuery.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        startCameraIntent();
                } else {
                }
                return;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //camera request
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    try {
                        Toast.makeText(this, "File saved: "+selectedImage.toString(),
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }

    }

    public DotSerail getDotSerail() {
        return dotSerail;
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
       // super.onBackPressed();  // optional depending on your needs
        this.stepPreviousProcedureSlide();
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
