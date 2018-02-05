package project.deepwateroiltools_001;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.crossfader.app.util.CrossfadeWrapper;
import com.mikepenz.crossfader.util.UIUtils;
import com.mikepenz.crossfader.view.CrossFadeSlidingPaneLayout;
import com.mikepenz.fastadapter.listeners.OnTouchListener;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.MaterializeBuilder;
import com.mikepenz.materialize.color.Material;

import project.deepwateroiltools.HTTP.User;


public class WelcomeScreen extends Activity implements View.OnClickListener {
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private AccountHeader headerResult = null;
    private Crossfader crossFader;
    private User user;
    private Button startProcedure;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FULL SCREEN
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome_screen);

        //BUTTONS
        startProcedure = (Button)this.findViewById(R.id.btn_startSeaSecure);
        startProcedure.setOnClickListener(this);

        //get the user obj from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = new Gson().fromJson(extras.getString("user"), User.class);
        }
        else{
            //TODO error handling
            Log.d("EEEh", "Not happening");
        }


       // RelativeLayout view = new RelativeLayout(this);
       // Log.d("VIEW", view.toString());


        //Touch event handling, closes keypad, removes focus from fields





        //handle the style
        new MaterializeBuilder(this).build();


        final IProfile profile = new ProfileDrawerItem().withName(user.getUserInfo().getFullName()).withEmail(user.getUser()).withIcon(getResources().getDrawable(R.drawable.logo));


        // Create the AccountHeader
        AccountHeaderBuilder headerBuilder = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(profile)
                .withCloseDrawerOnProfileListClick(false)
                .withCompactStyle(true)
                .withSavedInstance(savedInstanceState);


        headerResult = headerBuilder.build();

        headerResult.setBackground(getResources().getDrawable(R.drawable.drawer5_vert2));



        DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(new IconicsDrawable(this)
                                .icon(FontAwesome.Icon.faw_home)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(1),
                        new PrimaryDrawerItem().withName("Export/Upload").withIcon(new IconicsDrawable(this)
                                .icon(FontAwesome.Icon.faw_cloud_upload)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(2),
                        new PrimaryDrawerItem().withName("Settings").withIcon(new IconicsDrawable(this)
                                .icon(FontAwesome.Icon.faw_cogs)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(3),
                        new PrimaryDrawerItem().withName("History").withIcon(new IconicsDrawable(this)
                                .icon(FontAwesome.Icon.faw_history)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(4),
                        new PrimaryDrawerItem().withName("Contact").withIcon(new IconicsDrawable(this)
                                .icon(FontAwesome.Icon.faw_at)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(5)

                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(WelcomeScreen.this, ((Nameable) drawerItem).getName().getText(WelcomeScreen.this), Toast.LENGTH_SHORT).show();

                        }
                        else{

                        }
                       //
                        miniResult.onItemClick(drawerItem);

                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState);



        if (!user.getAdmin()){
            builder.addDrawerItems(
                    new PrimaryDrawerItem().withName("Admin Area").withIcon(new IconicsDrawable(this)
                            .icon(FontAwesome.Icon.faw_user_secret)
                            .color(Color.WHITE))
                            .withIconTintingEnabled(true)
                            .withIdentifier(6)
            );
        }

        // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())


        result = builder.buildView();

        miniResult = new MiniDrawer().withDrawer(result);


        //IMPORTANT Crossfader specific implementation starts here (everything above is MaterialDrawer):

        //get the widths in px for the first and second panel
        int firstWidth = (int) UIUtils.convertDpToPixel(200, this);
        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);


        //FrameLayout mCrossFadePanel = (FrameLayout) mCrossFadeSlidingPaneLayout.findViewById(R.id.panel);

        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
        crossFader = new Crossfader()
                .withContent(findViewById(R.id.crossfade_content))
                .withContent(findViewById(R.id.btn_startSeaSecure))

                .withFirst(result.getSlider(), firstWidth)
                .withSecond(miniResult.build(this), secondWidth)
                .withGmailStyleSwiping()
                .withSavedInstance(savedInstanceState)


                .build();


        crossFader.getFirst().setBackgroundColor(Color.BLACK);
        crossFader.getSecond().setBackgroundColor(Color.BLACK);
        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);




    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = headerResult.saveInstanceState(outState);
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
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
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


    @Override
    public void onClick(View v) {
        if (v == startProcedure){
            if (crossFader.isCrossFaded()){
                Toast.makeText(WelcomeScreen.this,String.valueOf(crossFader.isCrossFaded()), Toast.LENGTH_SHORT).show();
                crossFader.crossFade();
            }
        }

    }

}
