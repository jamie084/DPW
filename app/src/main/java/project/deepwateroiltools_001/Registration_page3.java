package project.deepwateroiltools_001;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.HTTPDataHandler;
import project.deepwateroiltools.HTTP.User;
import project.deepwateroiltools.HTTP.UserInfo;

public class Registration_page3 extends Activity implements View.OnClickListener, View.OnFocusChangeListener {
    User user;
    UserInfo userInfo;
    Button btn_submit_reg3;
    EditText houseNumber, street, city, country, postCode;
    TextView lbl_warning;


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
//                firstName.clearFocus();
//                secondName.clearFocus();
//                companyName.clearFocus();
//                phoneNumber.clearFocus();
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

            RunDbQueryToPostNewUser db = new RunDbQueryToPostNewUser();
            db.execute();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    private class RunDbQueryToPostNewUser extends AsyncTask<String, Void, String> {
        String urlString;
        private ProgressDialog dialog;

        private RunDbQueryToPostNewUser() {

            dialog = new ProgressDialog(Registration_page3.this, R.style.DialogBoxStyle);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Submit request...");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.show();
        }

        //TODO try catch, error handling
        @Override
        protected String doInBackground(String... params) {
            HTTPDataHandler http = new HTTPDataHandler();
            String par = new Gson().toJson(user);
            String urlString = Common.getBaseURL() + Common.getApiKey();
            http.PostHTTPData(urlString,par);
            String url = Common.getBaseURL() + Common.getApiKey() + "&q={\"user\":\"" + user.getUser() + "\"}";
            //String userString = http.GetHTTPData(url) ;
            //Gson gson = new Gson();
            //Type listType = new TypeToken<List<User>>(){}.getType();
            //List<User> users = gson.fromJson(userString, listType);
            //User user = users.get(0);
            //userInfo.set_id(user.get_id());
           // http.PostHTTPData(Common.getUrlUserInfo(), new Gson().toJson(userInfo));
            String test = http.GetHTTPData(url);
            Log.d("RESPONSE", test);
            return http.GetHTTPData(url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Log.d("response? ", s);

        }
    }
}
