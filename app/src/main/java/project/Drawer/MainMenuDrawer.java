package project.Drawer;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.crossfader.app.util.CrossfadeWrapper;
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

import project.deepwateroiltools.HTTP.User;
import project.deepwateroiltools_001.R;
import project.deepwateroiltools_001.WelcomeScreen;

/**
 * Created by janos on 05/02/2018.
 */

public class MainMenuDrawer extends Activity {
    Activity activity;
    User user;
    private Context context;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private AccountHeader headerResult = null;
    private Crossfader crossFader = null;
    private Bundle savedInstanceState;


    public MainMenuDrawer(Activity activity, Context context, User user, Bundle savedInstanceState){
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
                        new PrimaryDrawerItem().withName("Home").withIcon(new IconicsDrawable(activity)
                                .icon(FontAwesome.Icon.faw_home)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(1),
                        new PrimaryDrawerItem().withName("Export/Upload").withIcon(new IconicsDrawable(activity)
                                .icon(FontAwesome.Icon.faw_cloud_upload)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(2),
                        new PrimaryDrawerItem().withName("Settings").withIcon(new IconicsDrawable(activity)
                                .icon(FontAwesome.Icon.faw_cogs)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(3),
                        new PrimaryDrawerItem().withName("History").withIcon(new IconicsDrawable(activity)
                                .icon(FontAwesome.Icon.faw_history)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(4),
                        new PrimaryDrawerItem().withName("Contact").withIcon(new IconicsDrawable(activity)
                                .icon(FontAwesome.Icon.faw_at)
                                .color(Color.WHITE))
                                .withIconTintingEnabled(true)
                                .withIdentifier(5)

                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                         //   Toast.makeText(activity, ((Nameable) drawerItem).getName().getText(activity), Toast.LENGTH_SHORT).show();

                        }
                        else{

                        }
                        //TODO will need this
                        miniResult.onItemClick(drawerItem);

                        return true;
                    }
                })
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


public void createMainMenu(){
    new MaterializeBuilder(activity).build();
    headerResult = this.getHeader();
    DrawerBuilder builder = this.getDrawerBuilder();
    result = builder.buildView();

    miniResult = new MiniDrawer().withDrawer(result);


    //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
    crossFader = this.getCrossFader(result, miniResult);


    //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
    miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

    //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
    crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);
//    R.drawable.material_drawer_shadow_left
//    context.getResources().getDrawable(R.drawable.logo))

}

}
