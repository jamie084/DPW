package project.deepwateroiltools_001;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.HTTPDataHandler;
import project.deepwateroiltools.HTTP.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


//TODO input validation

public class LoginScreen extends Activity implements View.OnClickListener, View.OnFocusChangeListener {
    Button btn_login, btn_reg;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FULL SCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_screen);



        //BUTTONS
        btn_login = (Button)this.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_reg = (Button)this.findViewById(R.id.btn_register);
        btn_reg.setOnClickListener(this);

        //Edit text fields
        email = (EditText)this.findViewById(R.id.inpField_email);

        email.setOnFocusChangeListener(this);
        password = (EditText)this.findViewById(R.id.inpField_pw);
        password.setOnFocusChangeListener(this);

         //Touch event handling, closes keypad, removes focus from fields
        findViewById(R.id.background_welcomeScreen).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                email.clearFocus();
                password.clearFocus();
                return true;
            }
        });


    }



    @Override
    public void onClick(View v){
        if (v == btn_login){
            String url = Common.getBaseURL() + Common.getApiKey() + "&q={\"password\":\"" + password.getText().toString() + "\",\"user\":\"" + email.getText().toString() + "\",\"isActive\":true}";
            new RunDbQuery(url, this).execute();
//            Intent reg = new Intent(this, HomeScreen.class);
//            startActivity(reg);
        }
        if (v == btn_reg){
            Intent reg = new Intent(this, Registration.class);
            startActivity(reg);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (email.hasFocus()) {

            email.setText("");
        }
        if (password.hasFocus()){
            password.setHint("password");
            password.setText("");
        }
    }

    private class RunDbQuery extends AsyncTask<String, Void, String> {
        String urlString = "";
        private ProgressDialog dialog;
        Context context;

        private RunDbQuery(String urlString, Context context){
            this.context = context.getApplicationContext();
            this.urlString = urlString;
            dialog = new ProgressDialog(LoginScreen.this, R.style.DialogBoxStyle);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Connecting to the server...");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HTTPDataHandler httpDataHandler = new HTTPDataHandler();
            String stream = httpDataHandler.GetHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>(){}.getType();
            List<User> users = gson.fromJson(s, listType);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (!users.isEmpty()) {
                User user = users.get(0);
                Intent intentWelcome = new Intent(context, HomeScreen.class);
                intentWelcome.putExtra("user", (new Gson()).toJson(user));
                startActivity(intentWelcome);

                Log.d("REDIRECT TO welcome","****************SUCCESS for: " + user.getAdmin());//, users.get(0).get_id().getOid().toString() );//+ " " + format1.format(lastLog.getTime()));
            }
            else{
                AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginScreen.this, R.style.LightDialogTheme);
                builder1.setMessage("Email address or password is not correct.");
                builder1.setCancelable(true);

                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {}
                });

                AlertDialog alert11 = builder1.create();
                alert11.show();

                //position of OK button
                final Button positiveButton = alert11.getButton(AlertDialog.BUTTON_POSITIVE);
                LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
                positiveButton.setLayoutParams(positiveButtonLL);

                Log.d("User","no entries");
            }
        }
    }
}


