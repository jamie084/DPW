package project.deepwateroiltools_001;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.HTTPDataHandler;
import project.deepwateroiltools.HTTP.User;


//TODO Input validation

public class Registration extends Activity implements OnClickListener, View.OnFocusChangeListener {
    EditText email, password, passwordRe;
    Button btn_next;
    ImageView img_email, img_pw, img_pw2;
    TextView lbl_warning;
    String lastSearchEmail = "";


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
        email = (EditText)this.findViewById(R.id.inpField_email_reg);
        email.setOnFocusChangeListener(this);

        password = (EditText)this.findViewById(R.id.inpField_pw_reg);
        password.setOnFocusChangeListener(this);

        passwordRe = (EditText)this.findViewById(R.id.inpField_pw_reg2);
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
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                email.clearFocus();
                password.clearFocus();
                passwordRe.clearFocus();
                return true;
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == btn_next){

        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!email.hasFocus()){
            //To verify any user is registered with this email address
            if (!email.getText().toString().equals(lastSearchEmail)) {
                lastSearchEmail = email.getText().toString();
                String url = Common.getBaseURL() + Common.getApiKey() + "&q={\"user\":\"" + email.getText().toString() + "\"}";
                new RunDbQueryToVerifyEmail(url).execute();
            }
        }

    }

    private class RunDbQueryToVerifyEmail extends AsyncTask<String, Void, String> {
        String urlString;

        private RunDbQueryToVerifyEmail(String url){
            this.urlString = url;
        }

        @Override
        protected String doInBackground(String... params) {
            HTTPDataHandler http = new HTTPDataHandler();

//            String par = "{\"name\":\"myname\",\"age\":\"20\"}";
//
//            String urlString = Common.getBaseURL() + Common.getApiKey();
//            http.PostHTTPData(urlString,par);
            return http.GetHTTPData(urlString);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>(){}.getType();
            List<User> users = gson.fromJson(s, listType);
            //Display icon according to search results
            if (!users.isEmpty()) {
                img_email.setImageResource(R.drawable.false_img);
                lbl_warning.setText("Email is used by another user");
                img_email.setVisibility(View.VISIBLE);
            }
            else{
                lbl_warning.setText("Please fill in the requested fields");
                img_email.setImageResource(R.drawable.true_img);
                img_email.setVisibility(View.VISIBLE);
            }
        }
    }
}
