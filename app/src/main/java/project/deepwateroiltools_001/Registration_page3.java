package project.deepwateroiltools_001;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import project.Messages;
import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools.HTTP.RunDBQueryWithDialog;
import project.dto.user.User;
import project.dto.user.UserInfo;
import project.email.EmailClient;

public class Registration_page3 extends Activity implements View.OnClickListener, View.OnFocusChangeListener {
    User user;
    UserInfo userInfo;
    Button btn_submit_reg3;
    EditText houseNumber, street, city, country, postCode;
    TextView lbl_warning;
    Context context;
    public static Activity activity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FULL SCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_registrartion_page3);

        //Button
        btn_submit_reg3 = (Button)findViewById(R.id.btn_submit_reg3);
        btn_submit_reg3.setOnClickListener(this);

        //EditText
        houseNumber = (EditText)findViewById(R.id.inpField_houseNumber_reg3);
        houseNumber.setOnFocusChangeListener(this);

        street = (EditText)findViewById(R.id.inpField_street_reg3);
        street.setOnFocusChangeListener(this);

        city = (EditText)findViewById(R.id.inpField_city_reg3);
        city.setOnFocusChangeListener(this);

        country = (EditText)findViewById(R.id.inpField_country_reg3);
        country.setOnFocusChangeListener(this);

        postCode = (EditText)findViewById(R.id.inpField_postCode_reg3);
        postCode.setOnFocusChangeListener(this);

        context = this;


        //Touch event handling, closes keypad, removes focus from fields
        findViewById(R.id.background_welcomeScreen).setOnTouchListener(new View.OnTouchListener() {
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

        //get the user obj from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = new Gson().fromJson(extras.getString("user"), User.class);
            //TODO as long as admin page is not implemented keep it
            user.setActive(true);
            user.setAdmin(true);
            userInfo = user.getUserInfo();
        }
        else{
            //TODO error handling
            Log.d("EEEh", "Not happening");
        }

    }

    @Override
    public void onClick(View v) {
        if (v == btn_submit_reg3){
            userInfo.setHouseNumber(houseNumber.getText().toString());
            userInfo.setStreet(street.getText().toString());
            userInfo.setCity(city.getText().toString());
            userInfo.setCountry(country.getText().toString());
            userInfo.setPostCode(postCode.getText().toString());

            sendEmail();
            //String url = Common.getUrlUser() + Common.getApiKey() + "&q={\"user\":\"" + user.getUser() + "\"}";
            String postUrl = Common.getUrlUser() + Common.getApiKey();
            String jsonParam = new Gson().toJson(user);
            RunDBQueryWithDialog runDBQueryWithDialog = new RunDBQueryWithDialog(this, postUrl, "Submit request...", jsonParam);
            runDBQueryWithDialog.setProcessListener(new ProcessListener() {
                @Override
                public void ProcessingIsDone(String result) {
                    makeToast(Messages.Success_async_registration);
                    Intent login = new Intent((Registration_page3.this), LoginScreen.class);
                    startActivity(login);
                }
            });

            runDBQueryWithDialog.execute();


        }
    }

    public void makeToast(String msg){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    public void sendEmail(){
        ArrayList<String> recipients = new ArrayList<>();
        recipients.add(user.getUser());
        String emailBody = Messages.Success_async_registration + "\n" + userInfo.toString();
        EmailClient  emailClient = new EmailClient(getApplicationContext(), "New Registration Request", recipients, emailBody, false);
        emailClient.setProcessListener(new ProcessListener() {
            @Override
            public void ProcessingIsDone(String result) {

            }
        });
        emailClient.execute();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }


}
