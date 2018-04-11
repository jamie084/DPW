package project.deepwateroiltools_001;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.mikepenz.materialdrawer.Drawer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentAdminArea;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentHistory;
import project.dto.DotSerail;
import project.dto.user.User;
import project.dto.user.UserInfo;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by janos on 02/04/2018.
 */
public class HomeScreenTest {

    private HomeScreen mActivity = null;
    private Drawer drawer;

    User user;
    UserInfo userInfo;


    @Rule
    public ActivityTestRule<HomeScreen> mActivityTestRule = new ActivityTestRule<HomeScreen>(HomeScreen.class){
        @Override
        protected Intent getActivityIntent() {
            userInfo = new UserInfo();
            userInfo.setFirstName("j");
            userInfo.setSecondName("m");

            user = new User();
            user.setUserInfo(userInfo);
            user.setAdmin(true);
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, HomeScreen.class);
            result.putExtra("user", (new Gson()).toJson(user));
            return result;
        }
    };

    Instrumentation.ActivityMonitor monitorHistory = getInstrumentation().addMonitor(FragmentHistory.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {

        mActivity = mActivityTestRule.getActivity();
        drawer = mActivity.getDrawer();
    }

    @Test
    public void fragmentLaunchingTest() throws Exception{

        //same layout id used for all the fragments
        FrameLayout rlContainer = (FrameLayout) mActivity.findViewById(R.id.crossfade_content);
        assertNotNull(rlContainer);

        FragmentAdminArea fragment = new FragmentAdminArea();

        mActivity.getFragmentManager().beginTransaction().add(rlContainer.getId(), fragment).commitAllowingStateLoss();

        DotSerail dotSerial = new DotSerail();
        dotSerial.setDOT_SCR_IBH(222);
        mActivity.setDotSerail(dotSerial);

        View view = fragment.getView().findViewById(R.id.crossfade_content);
        assertNotNull(view);


    }

    @Test
    public void menuClicks() throws Exception{

//        onView(withId(getId("Home"))).perform(click());
        //onView(withId(FontAwesome.Icon.faw_home)).perform(click());
    //    onView(withText("Settings")).perform(click());

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCreate() throws Exception {
    }

    private static int getId(String id) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        String packageName = targetContext.getPackageName();
        return targetContext.getResources().getIdentifier(id, "id", packageName);
    }
}