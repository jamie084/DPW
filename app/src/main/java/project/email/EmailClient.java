package project.email;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Contact;
import com.mailjet.client.resource.Csvimport;
import com.mailjet.client.resource.Email;
import com.mailjet.client.resource.Sender;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.HTTPDataHandler;
import project.deepwateroiltools_001.HomeScreen;
import project.deepwateroiltools_001.LoginScreen;
import project.deepwateroiltools_001.R;


/**
 * Created by janos on 13/02/2018.
 */

public class EmailClient extends AsyncTask<String, Void, String> {
    private String subject;
    private ArrayList<String> toList;
    private String textpart;
    private ProgressDialog dialog;
    private  JSONArray recepientsArray;
    MailjetClient client;
    MailjetRequest request;
    MailjetResponse response;
    Context context;
    Activity activity;
    TextView lbl_info;

    public EmailClient(Context context, String subject, ArrayList<String> toList, String textpart){
        this.subject = subject;
        this.toList = toList;
        this.textpart = textpart;
        this.context =context;
        this.dialog = new ProgressDialog(context, R.style.DialogBoxStyle);
        this.activity = (Activity) context;


    }
    @Override
    protected void onPreExecute() {
        dialog.setMessage("Sending your request...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.show();
    }

   @Override
    protected String doInBackground(String... strings) {
       client = new MailjetClient(Common.getMailjetPrivatekey(), Common.getMailjetSecretkey());
       recepientsArray = new JSONArray();
       response = null;
       lbl_info = (TextView)this.activity.findViewById(R.id.lbl_info);

       for (int i=0; i<toList.size(); i++){
            try {
                recepientsArray.put(new JSONObject().put("Email", toList.get(i)));
            }
            catch (Exception e){};
        }
        try {

        }
        catch (Exception e){}

        client.setDebug(1);
        try {
            request = new MailjetRequest(Email.resource)
                    .property(Email.FROMEMAIL, Common.SUPPORT_EMAIL)
                    .property(Email.FROMNAME, Common.SUPPORT_NAME)
                    .property(Email.SUBJECT, subject)
                    .property(Email.TEXTPART, textpart)
                   // .property(Email.HTMLPART, "<h3>HTML part Dear passenger, welcome to Mailjet!</h3><br />May the delivery force be with you!")
                    .property(Email.RECIPIENTS, recepientsArray);
            response = client.post(request);
        }
        catch(Exception jsonex){
            lbl_info.setText("An error occured when submitting your request.\n Please try again later");

        }

        return null;



    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (response != null){
            if (response.getStatus() == 200){
                lbl_info.setText("Your request has been submitted. Thank you");
            }
            else {
                lbl_info.setText("An error occured when submitting your request.\n Please try again later");
            }
        }
        //Log.d("response from email ", s);

    }


}
