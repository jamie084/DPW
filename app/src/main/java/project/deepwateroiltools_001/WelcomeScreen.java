package project.deepwateroiltools_001;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.crossfader.app.util.CrossfadeWrapper;
import com.mikepenz.crossfader.util.UIUtils;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

//import com.mikepenz.crossfader.app.util.CrossfadeWrapper;
//import com.mikepenz.crossfader.Crossfader;
////import com.mikepenz.crossfader.app.util2.CrossfadeWrapper;
//import com.mikepenz.crossfader.util.UIUtils;
//import com.mikepenz.materialdrawer.Drawer;
//import com.mikepenz.materialdrawer.DrawerBuilder;
//import com.mikepenz.materialdrawer.MiniDrawer;
//import com.mikepenz.materialdrawer.model.DividerDrawerItem;
//import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
//import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
//import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class WelcomeScreen extends Activity  {
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private Drawer result = null;
    private MiniDrawer miniResult = null;

    private Crossfader crossFader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FULL SCREEN
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome_screen);

        DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                //.withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("111").withIdentifier(1),
//                        new PrimaryDrawerItem().withName(R.string.drawer_item_second).withIcon(FontAwesome.Icon.faw_home).withIdentifier(2),
//                        new PrimaryDrawerItem().withName(R.string.drawer_item_third).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
//                        new PrimaryDrawerItem().withName(R.string.drawer_item_fourth).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(4),
//                        new PrimaryDrawerItem().withDescription("A more complex sample").withName(R.string.drawer_item_fifth).withIcon(GoogleMaterial.Icon.gmd_adb).withIdentifier(5),
//                        new PrimaryDrawerItem().withName(R.string.drawer_item_sixth).withIcon(GoogleMaterial.Icon.gmd_camera).withIdentifier(6),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("22222")//.withIcon(FontAwesome.Icon.faw_github)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(WelcomeScreen.this, ((Nameable) drawerItem).getName().getText(WelcomeScreen.this), Toast.LENGTH_SHORT).show();
                        }
                        miniResult.onItemClick(drawerItem);

                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState);

        // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
        result = builder.buildView();
        // create the MiniDrawer and deinfe the drawer and header to be used (it will automatically use the items from them)
        miniResult = new MiniDrawer().withDrawer(result);//.withAccountHeader(headerResult);


        //IMPORTANT Crossfader specific implementation starts here (everything above is MaterialDrawer):

        //get the widths in px for the first and second panel
        int firstWidth = (int) UIUtils.convertDpToPixel(200, this);
        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
        crossFader = new Crossfader()
                .withContent(findViewById(R.id.crossfade_content))
                .withFirst(result.getSlider(), firstWidth)
                .withSecond(miniResult.build(this), secondWidth)
                .withGmailStyleSwiping()
                .withSavedInstance(savedInstanceState)
                .build();

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
        crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);

//        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("firstItem");
//        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("secondItem");
//
//        //create the drawer and remember the `Drawer` result object
//        Drawer result = new DrawerBuilder()
//                .withActivity(this)
//                //.withToolbar(toolbar)
//                .withTranslucentStatusBar(false)
//                .withActionBarDrawerToggle(false)
//                .addDrawerItems(  item1
//                       //pass your items here
//                )
//                .addDrawerItems(
//                        item1,
//                        new DividerDrawerItem(),
//                        item2,
//                        new SecondaryDrawerItem().withName("dunno")
//                )
//                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//                    @Override
//                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//                        Toast.makeText(getApplicationContext(), "Time for an upgrade!", Toast.LENGTH_SHORT).show();
//                        // do something with the clicked item :D
//                        return true;
//                    }
//                })
//                .build();
//
//        Crossfader crossFader = new Crossfader()
//                //provide the view which should be the main content
//                .withContent(findViewById(R.id.crossfade_content))
//                //provide the view and it's width (in pixels) which should be displayed first
//                .withFirst(result.getSlider(), firstWidth)
//                //provide the view and it's width (in pixels) which should be displayed after fading
//                .withSecond(miniResult.build(this), secondWidth)
//                //provde the saved instance state to store the previous state of the crossfade view
//                .withSavedInstance(savedInstanceState)
//                //build and inflate everything
//                .build();


        //Awesome everything is set and working
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

     //   addDrawerItems();
//        new DrawerBuilder().withActivity(this).build();
//        //setupDrawer();
//

//
//        // build the miniDrawer
//        MiniDrawer miniResult = new MiniDrawer().withDrawer(result).withInnerShadow(true);
//
////define the width of the normal drawer, and the minidrawer
//        int first = (int) UIUtils.convertDpToPixel(300, this);
//        int second = (int) UIUtils.convertDpToPixel(72, this);
//
////create the Crossfader used to hook the MiniDrawer and the normal drawer together. This also handles the crossfade effect.
//        Crossfader crossFader = new Crossfader()
//                .withContent(findViewById(R.id.crossfade_content))
//                .withFirst(result.getSlider(), first)
//                .withSecond(miniResult.build(this), second)
//                .withSavedInstance(savedInstanceState)
//                .build();
//
//// inform the MiniDrawer about the crossfader.
//        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
      //  outState = headerResult.saveInstanceState(outState);
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



}
