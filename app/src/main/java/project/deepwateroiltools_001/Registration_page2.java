package project.deepwateroiltools_001;

import android.app.Activity;
import android.content.Intent;
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

import project.deepwateroiltools.HTTP.User;
import project.deepwateroiltools.HTTP.UserInfo;


//TODO input validation for phone number

public class Registration_page2 extends Activity implements View.OnClickListener, View.OnFocusChangeListener {
    Button btn_next;
    EditText firstName, secondName, companyName, phoneNumber;
    TextView lbl_warning;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FULL SCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_registration_page2);

        //BUTTONS
        btn_next = (Button)this.findViewById(R.id.btn_next_reg2);
        btn_next.setOnClickListener(this);

        //Edittext items and the listeners
        firstName = (EditText)this.findViewById(R.id.inpField_firstname_reg2);
        firstName .setOnFocusChangeListener(this);

        secondName = (EditText)this.findViewById(R.id.inpField_sec_name_reg);
        secondName.setOnFocusChangeListener(this);

        companyName = (EditText)this.findViewById(R.id.inpField_company_reg2);
        companyName.setOnFocusChangeListener(this);

        phoneNumber = (EditText)this.findViewById(R.id.inpField_phone_reg2);
        phoneNumber.setOnFocusChangeListener(this);

        //TextView
        lbl_warning = (TextView)this.findViewById(R.id.lbl_warning_reg) ;
        lbl_warning.setText("Please fill in the requested fields");

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
                firstName.clearFocus();
                secondName.clearFocus();
                companyName.clearFocus();
                phoneNumber.clearFocus();
                return true;
            }
        });

        //get the user obj from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = new Gson().fromJson(extras.getString("user"), User.class);
            Log.d("userObject", user.getUser());
        }
        else{
            //TODO error handling
            Log.d("EEEh", "Not happening");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_next){
            UserInfo userInfo = new UserInfo();
            userInfo.setFirstName(firstName.getText().toString());
            userInfo.setSecondName(secondName.getText().toString());
            userInfo.setCompany(companyName.getText().toString());
            userInfo.setPhoneNumber(phoneNumber.getText().toString());
            user.setUserInfo(userInfo);
        //    userInfo.set_id(user.get_id());

            Log.d("userInfo To String", userInfo.toString());

            Intent reg3 = new Intent(this, Registration_page3.class);
            reg3.putExtra("user", (new Gson()).toJson(user));
            //reg3.putExtra("userInfo", (new Gson()).toJson(userInfo));
            startActivity(reg3);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
}
