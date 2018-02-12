package project.Drawer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.crossfader.util.UIUtils;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.MaterializeBuilder;

import project.deepwateroiltools_001.SeaCure;
import project.dto.user.User;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentAdminArea;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentContact;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentExport;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentHistory;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentHomeScreen;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentSettings;
import project.deepwateroiltools_001.R;

/**
 * Created by janos on 05/02/2018.
 */

public class SeaCureMenuDrawer extends Activity {
    SeaCure activity;
    User user;
    private Context context;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private AccountHeader headerResult = null;
    private Crossfader crossFader = null;
    private Bundle savedInstanceState;


    public SeaCureMenuDrawer(SeaCure activity, Context context, User user, Bundle savedInstanceState){
        this.activity =activity;
        this.user = user;
        this.context = context;
        this.savedInstanceState = savedInstanceState;


    }

    public void myMenu(){
        new MaterializeBuilder(activity).build();
        final IProfile profile = new ProfileDrawerItem().withName(user.getUserInfo().getFullName()).withEmail(user.getUser()).withIcon(context.getResources().getDrawable(R.drawable.logo));
    }


    public IProfile<ProfileDrawerItem> getProfile(){

        final IProfile profile = new ProfileDrawerItem().withName(user.getUserInfo().getFullName()).withEmail(user.getUser()).withIcon(context.getResources().getDrawable(R.drawable.logo));
        return profile;
    }

    public AccountHeader getHeader(){
        // Create the AccountHeader
        AccountHeaderBuilder headerBuilder = new AccountHeaderBuilder()
                .withActivity(activity)
                .withTranslucentStatusBar(true)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(getProfile())
                .withCloseDrawerOnProfileListClick(false)
                .withCompactStyle(true)
                .withSavedInstance(savedInstanceState);


        headerResult = headerBuilder.build();

        headerResult.setBackground(context.getResources().getDrawable(R.drawable.drawer5_vert2));
        return headerResult;
    }

    public DrawerBuilder getDrawerBuilder(){
        DrawerBuilder builder = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(getHeader()) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Next").withIcon(new IconicsDrawable(activity)
                                .icon(FontAwesome.Icon.faw_arrow_circle_o_right)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(1),
                        new PrimaryDrawerItem().withName("Previous").withIcon(new IconicsDrawable(activity)
                                .icon(FontAwesome.Icon.faw_arrow_circle_left)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(2),
                        new PrimaryDrawerItem().withName("Save").withIcon(new IconicsDrawable(activity)
                                .icon(FontAwesome.Icon.faw_database)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(3),
                        new PrimaryDrawerItem().withName("Camera").withIcon(new IconicsDrawable(activity)
                                .icon(FontAwesome.Icon.faw_camera)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(4),
                        new PrimaryDrawerItem().withName("Exit").withIcon(new IconicsDrawable(activity)
                                .icon(FontAwesome.Icon.faw_stop_circle_o)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(5)

                ) // add the items we want to use with our Drawer

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Log.d("DRAWERITEM", String.valueOf(position));
                        if (drawerItem instanceof Nameable) {
                            if (drawerItem.getIdentifier() == 1){

                                activity.stepNextProcedureSlide();
                            }
                            else if (drawerItem.getIdentifier() == 2){
                                activity.stepPreviousProcedureSlide();
                            }
                            else if (drawerItem.getIdentifier() == 3) {
                                loadFragment(new FragmentSettings());
                            }
                            else if (drawerItem.getIdentifier() == 4){
                                loadFragment(new FragmentHistory());
                            }
                            else if (drawerItem.getIdentifier() == 5){
                                loadFragment(new FragmentContact());
                            }
                            else if (drawerItem.getIdentifier() == 6){
                                loadFragment(new FragmentAdminArea());
                            }

                            Log.d("DRAWERITEM", String.valueOf(drawerItem.getIdentifier()));
                            //   Toast.makeText(activity, ((Nameable) drawerItem).getName().getText(activity), Toast.LENGTH_SHORT).show();

                        }
                        else{

                        }
                        //TODO will need this
                        miniResult.onItemClick(drawerItem);

                        return true;
                    }
                })

                .withSelectedItem(-1)
                .withSavedInstance(savedInstanceState);

        if (!user.getAdmin()){
            builder.addDrawerItems(
                    new PrimaryDrawerItem().withName("Admin Area").withIcon(new IconicsDrawable(activity)
                            .icon(FontAwesome.Icon.faw_user_secret)
                            .color(Color.WHITE))
                            .withIconTintingEnabled(true)
                            .withIdentifier(6)
            );
        }
        return builder;
    }

    public Crossfader getCrossFader(Drawer result, MiniDrawer miniResult){
        this.result = result;
        this.miniResult = miniResult;

        //IMPORTANT Crossfader specific implementation starts here (everything above is MaterialDrawer):

        //get the widths in px for the first and second panel
        int firstWidth = (int) UIUtils.convertDpToPixel(200, activity);
        int secondWidth = (int) UIUtils.convertDpToPixel(72, activity);


        //FrameLayout mCrossFadePanel = (FrameLayout) mCrossFadeSlidingPaneLayout.findViewById(R.id.panel);

        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
        crossFader = new Crossfader()
                .withContent(activity.findViewById(R.id.crossfade_content))
                .withFirst(result.getSlider(), firstWidth)
                .withSecond(miniResult.build(activity), secondWidth)
                .withGmailStyleSwiping()
                .withSavedInstance(savedInstanceState);


        crossFader.getFirst().setBackgroundColor(Color.BLACK);
        crossFader.getSecond().setBackgroundColor(Color.BLACK);

        crossFader.build();
        return crossFader;
    }



    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = activity.getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.crossfade_content, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}
