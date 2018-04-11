package project.deepwateroiltools_001;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.crossfader.app.util.CrossfadeWrapper;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;

import java.lang.reflect.Type;
import java.util.List;

import project.Drawer.MainMenuDrawer;
import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools.HTTP.RunDBQueryWithDialog;
import project.dto.DotSerail;
import project.dto.user.User;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentHomeScreen;


public class HomeScreen extends Activity  {
    private Drawer drawer = null;
    private MiniDrawer miniDrawer = null;
    private AccountHeader headerDrawer = null;
    private Crossfader crossFader = null;
    public User user;
    private RunDBQueryWithDialog runDBQueryWithDialog;
    private DotSerail dotSerail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FULL SCREEN
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home_screen);

        //get the user obj from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            setUser(new Gson().fromJson(extras.getString("user"), User.class));
        }
        else{
            //TODO error handling
            Log.d("EEEh", "Not happening");
        }


        //defining the menu drawer
        MainMenuDrawer mainMenuDrawer = new MainMenuDrawer(this, getApplicationContext(), user, savedInstanceState);

        headerDrawer = mainMenuDrawer.getHeader();

        DrawerBuilder builder = mainMenuDrawer.getDrawerBuilder();

        drawer = builder.buildView();
        miniDrawer = new MiniDrawer().withDrawer(drawer);

       //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
        crossFader = mainMenuDrawer.getCrossFader(drawer, miniDrawer);

       //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniDrawer.withCrossFader(new CrossfadeWrapper(crossFader));

       //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);



        findViewById(R.id.crossfade_content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

                //Touch event handling, closes the drawer menu in case of touch event
                if (crossFader.isCrossFaded()){
                    crossFader.crossFade();
                }
                //Touch event handling, closes keypad, removes focus from fields
                if (getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                else{
                    Log.d("onTouchListener","would be NULL POINT");
                }

                return true;
            }
        });

        runDBQueryWithDialog = new RunDBQueryWithDialog(this, Common.getUrlDotSerial() + Common.getApiKey(), "");

        runDBQueryWithDialog.setProcessListener(new ProcessListener() {
            @Override
            public void ProcessingIsDone(final String result) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<DotSerail>>() {
                }.getType();
                List<DotSerail> dotSerials = gson.fromJson(result, listType);

                if (dotSerials != null) {
                    if (!dotSerials.isEmpty()) {
                        setDotSerail(dotSerials.get(0));
                        loadFragment(new FragmentHomeScreen());

                    } else {
                        Toast.makeText(getApplicationContext(), "An error occured, please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please verify you internet connection", Toast.LENGTH_SHORT).show();
                }


            }
        });
        runDBQueryWithDialog.execute();



    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user= user;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = drawer.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerDrawer.saveInstanceState(outState);
        //add the values which need to be saved from the crossFader to the bundle
        outState = crossFader.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        //menu.findItem(R.id.menu_1).setIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_sort).color(Color.WHITE).actionBar());
        return true;
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (crossFader.isCrossFaded()){
            crossFader.crossFade();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        switch (item.getItemId()) {

//            case R.id.menu_1:
//                crossFader.crossFade();
//                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public DotSerail getDotSerail() {
        return dotSerail;
    }

    public void setDotSerail(DotSerail dotSerail) {
        this.dotSerail = dotSerail;
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

    public Drawer getDrawer() {
        return drawer;
    }
}
