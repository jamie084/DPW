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
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.crossfader.app.util.CrossfadeWrapper;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;

import project.Drawer.MainMenuDrawer;
import project.dto.user.User;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentHomeScreen;


public class HomeScreen extends Activity implements View.OnClickListener {
    private Drawer drawer = null;
    private MiniDrawer miniDrawer = null;
    private AccountHeader headerDrawer = null;
    private Crossfader crossFader = null;
    public User user;
    private Button btn_startProcedure;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FULL SCREEN
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home_screen);


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


//        //BUTTONS
//        btn_startProcedure = (Button)this.findViewById(R.id.btn_startSeaSecure);
//        btn_startProcedure.setOnClickListener(this);


        //get the user obj from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = new Gson().fromJson(extras.getString("user"), User.class);
        }
        else{
            //TODO error handling
            Log.d("EEEh", "Not happening");
        }

      //  new MaterializeBuilder(this).build();

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

        //Touch event handling, closes keypad, removes focus from fields
        findViewById(R.id.crossfade_content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

                if (getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                else{
                    Log.d("onTouchListener","would be NULL POINT");
                }

                return true;
            }
        });

        loadFragment(new FragmentHomeScreen());

    }

    public User getUser(){
        return user;
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
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(HomeScreen.this, "ITEM " +  item.toString(), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onClick(View v) {
//        if (v == btn_startProcedure){
//            if (crossFader.isCrossFaded()){
//                Toast.makeText(HomeScreen.this,String.valueOf(crossFader.isCrossFaded()), Toast.LENGTH_SHORT).show();
//                crossFader.crossFade();
//            }
//            else{
//                Toast.makeText(HomeScreen.this, "ButtonClick", Toast.LENGTH_SHORT).show();
//            }
//            loadFragment(new FirstFragment());
//        }
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

}
