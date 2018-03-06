package project.deepwateroiltools_001;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools.HTTP.RunDBQueryWithDialog;
import project.dto.user.User;


//TODO Input validation

public class Registration extends Activity implements OnClickListener, View.OnFocusChangeListener {
    EditText email, password, passwordRe;
    Button btn_next;
    ImageView img_email, img_pw, img_pw2;
    TextView lbl_warning;
    String lastSearchedEmail = "";
    String lastCheckedPassword = "";
    boolean emailIsValid, pwIsValid, pw2IsValid;
    RunDBQueryWithDialog runDBQueryWithDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //FULL SCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_registration);


        //BUTTONS
        btn_next = (Button)this.findViewById(R.id.btn_next_reg);
        btn_next.setOnClickListener(this);

        //Edittext items and the listeners
        email = (EditText)this.findViewById(R.id.inpField_sec_name_reg);
        email.setOnFocusChangeListener(this);

        password = (EditText)this.findViewById(R.id.inpField_company_reg2);
        password.setOnFocusChangeListener(this);

        passwordRe = (EditText)this.findViewById(R.id.inpField_phone_reg2);
        passwordRe.setOnFocusChangeListener(this);

        //Image view
        img_email = (ImageView)this.findViewById(R.id.img_email);
        img_email.setVisibility(View.INVISIBLE);

        img_pw = (ImageView)this.findViewById(R.id.img_pw);
        img_pw.setVisibility(View.INVISIBLE);

        img_pw2 = (ImageView)this.findViewById(R.id.img_pwRe);
        img_pw2.setVisibility(View.INVISIBLE);

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
                email.clearFocus();
                password.clearFocus();
                passwordRe.clearFocus();
                return true;
            }
        });
        emailIsValid = false;
        pwIsValid = false;
        pw2IsValid =false;

    }

    @Override
    public void onClick(View v) {
        if (v == btn_next){
            if (emailIsValid && pwIsValid && pw2IsValid) {
                Intent reg2 = new Intent(this, Registration_page2.class);
                User user = new User();
                user.setUser(email.getText().toString());
                user.setPassword(password.getText().toString());
                reg2.putExtra("user", (new Gson()).toJson(user));
                startActivity(reg2);
            }
            else{
                makeToast("Please verify your inputs");
            }
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!email.hasFocus()) {

            //To verify any user is registered with this email address
            if (!email.getText().toString().equals(lastSearchedEmail)) {
                lastSearchedEmail = email.getText().toString();
                if (isValidEmail(email.getText().toString())) {
                    String url = Common.getUrlUser() + Common.getApiKey() + "&q={\"user\":\"" + email.getText().toString() + "\"}";
                    runDBQueryWithDialog = new RunDBQueryWithDialog(this, url, "Verifying email address...");
                    runDBQueryWithDialog.setProcessListener(new ProcessListener() {
                        @Override
                        public void ProcessingIsDone(String result) {
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<User>>(){}.getType();
                            List<User> users = gson.fromJson(result, listType);

                            //Display icon according to search results
                            if (!users.isEmpty()) {
                                makeToast("Email address used by another user");

                                img_email.setImageResource(R.drawable.false_img);
                                img_email.setVisibility(View.VISIBLE);
                                emailIsValid = false;
                            }
                            else{
                                emailIsValid = true;
                                img_email.setImageResource(R.drawable.true_img);
                                img_email.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    runDBQueryWithDialog.execute();

                } else {
                    img_email.setImageResource(R.drawable.false_img);
                    img_email.setVisibility(View.VISIBLE);
                    emailIsValid = false;
                    makeToast("Invalid address format");
                }
            }
        }
        if (!password.hasFocus()){
            if (!password.getText().toString().equals(lastCheckedPassword)) {
                lastCheckedPassword = password.getText().toString();
                if (isValidPassword(password.getText().toString())) {
                    img_pw.setImageResource(R.drawable.true_img);
                    img_pw.setVisibility(View.VISIBLE);
                    pwIsValid = true;
                } else {
                    img_pw.setImageResource(R.drawable.false_img);
                    img_pw.setVisibility(View.VISIBLE);
                    pwIsValid = false;
                    makeToast("Password is not strong enough");
                }
            }
        }
        //TODO tidy up, make one if statement
        if  (!passwordRe.hasFocus() & pwIsValid)  {

                if (passwordRe.getText().toString().equals(password.getText().toString())) {
                    img_pw2.setImageResource(R.drawable.true_img);
                    img_pw2.setVisibility(View.VISIBLE);
                    pw2IsValid = true;
                } else {
                    pw2IsValid = false;
                    img_pw2.setImageResource(R.drawable.false_img);
                    img_pw2.setVisibility(View.VISIBLE);
                }

        }

        if (email.hasFocus()){
            lbl_warning.setText("Please type in your email address");
        }
        if (password.hasFocus()){
            lbl_warning.setText("Password must be 8 charaters including at least one uppercase letter and number");
        }
        if (passwordRe.hasFocus()){
            lbl_warning.setText("Passwords must match");

        }
    }

    public void makeToast(String msg){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        //one digit, one uppercase, one lowercase, between 8-16 character
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,16}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    //email address validator
    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
