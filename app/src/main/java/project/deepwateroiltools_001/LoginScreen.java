package project.deepwateroiltools_001;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools.HTTP.RunDBQueryWithDialog;
import project.dto.user.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


//TODO input validation

public class LoginScreen extends Activity implements View.OnClickListener, View.OnFocusChangeListener {
    Button btn_login, btn_reg;
    EditText email, password;
    RunDBQueryWithDialog runDBQueryWithDialog;
    List <User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FULL SCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_screen);


        //BUTTONS
        btn_login = (Button) this.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_reg = (Button) this.findViewById(R.id.btn_register);
        btn_reg.setOnClickListener(this);

        //Edit text fields
        email = (EditText) this.findViewById(R.id.inpField_email);

        email.setOnFocusChangeListener(this);
        password = (EditText) this.findViewById(R.id.inpField_pw);
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
    public void onClick(View v) {
        if (v == btn_login) {
            String url = Common.getUrlUser() + Common.getApiKey() + "&q={\"password\":\"" + password.getText().toString() + "\",\"user\":\"" + email.getText().toString() + "\",\"isActive\":true}";
            runDBQueryWithDialog = new RunDBQueryWithDialog(this, url, "Connecting to the server...");
            runDBQueryWithDialog.setProcessListener(new ProcessListener() {
                @Override
                public void ProcessingIsDone(final String result) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<User>>() {
                    }.getType();
                    users = gson.fromJson(result, listType);
                    if (users != null) {
                        if (!users.isEmpty()) {
                            User user = users.get(0);
                            Intent intentWelcome = new Intent(getApplicationContext(), HomeScreen.class);
                            intentWelcome.putExtra("user", (new Gson()).toJson(user));
                            startActivity(intentWelcome);

                        } else {
                            Toast.makeText(getApplicationContext(), "Email address or password is not correct", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please verify you internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            runDBQueryWithDialog.execute();

        }
        if (v == btn_reg) {
            Intent reg = new Intent(this, Registration.class);
            startActivity(reg);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (email.hasFocus()) {

            email.setText("");
        }
        if (password.hasFocus()) {
            password.setHint("password");
            password.setText("");
        }
    }

}




