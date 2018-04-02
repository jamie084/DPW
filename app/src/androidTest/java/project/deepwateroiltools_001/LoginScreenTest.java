package project.deepwateroiltools_001;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by janos on 02/04/2018.
 */
public class LoginScreenTest {

    @Rule
    public ActivityTestRule<LoginScreen> mActivityTestRule = new ActivityTestRule<LoginScreen>(LoginScreen.class);

    private LoginScreen mActivity = null;

    Instrumentation.ActivityMonitor monitorHome = getInstrumentation().addMonitor(HomeScreen.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorRegistration = getInstrumentation().addMonitor(Registration.class.getName(), null, false);

    @Before
    public void setUp() throws  Exception{
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLoginBtn(){


        //perform onclick event
        onView(withId(R.id.btn_login)).perform(click());

        //starts second activity
        Activity secondactivty = getInstrumentation().waitForMonitorWithTimeout(monitorHome, 3000);

        assertNotNull(secondactivty);

        secondactivty.finish();
    }

    @Test
    public void testRegBtn(){


        //perform onclick event
        onView(withId(R.id.btn_register)).perform(click());

        //starts second activity
        Activity secondactivty = getInstrumentation().waitForMonitorWithTimeout(monitorRegistration, 3000);

        assertNotNull(secondactivty);

        secondactivty.finish();
    }

    @Test
    public void onCreate() throws Exception {
        View viewBtnReg = mActivity.findViewById(R.id.btn_register);
        View viewBtnLogin = mActivity.findViewById(R.id.btn_login);

        //verify against nulls
        assertNotNull(viewBtnReg);
        assertNotNull(viewBtnLogin);
    }



    @Test
    public void onFocusChange() throws Exception {
    }

    @After
    public void tearDown() throws  Exception{
        mActivity = null;
    }

}