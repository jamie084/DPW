package project.deepwateroiltools_001;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.HTTPDataHandler;
import project.deepwateroiltools.HTTP.User;

import android.app.Activity;
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
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class WelcomeScreen extends Activity implements View.OnClickListener {
    Button btn_login, btn_reg;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FULL SCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome_screen2);



        //BUTTONS
        btn_login = (Button)this.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_reg = (Button)this.findViewById(R.id.btn_register);
        btn_reg.setOnClickListener(this);

        //Edit text fields
        email = (EditText)this.findViewById(R.id.inpField_email);
        password = (EditText)this.findViewById(R.id.inpField_pw);


        //Touch event handling, closes keypad
        findViewById(R.id.background_welcomeScreen).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });


    }

    @Override
    public void onClick(View v){
        if (v == btn_login){
            String emailS = email.getText().toString();
            String pwS = password.getText().toString();
            Log.d("LogInclicked; email ", emailS + " pw: " +pwS);
            new RunDbQuery().execute();
        }
    }
    private class RunDbQuery extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HTTPDataHandler httpDataHandler = new HTTPDataHandler();
            String stream = httpDataHandler.GetHTTPData(Common.getAddressAPI());
            return stream;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<User>>(){}.getType();
            List<User> users = gson.fromJson(s, listType);
            if (users.size() > 0) {
                Log.d("done", users.get(0).getUser().toString());
            }
            else{
                Log.d("User","no entries");
            }
        }
    }
}


