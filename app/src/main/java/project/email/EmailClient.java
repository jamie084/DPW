package project.email;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Email;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import project.Error;
import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools_001.R;


/**
 * Created by janos on 13/02/2018.
 */

public class EmailClient extends AsyncTask<String, Void, String> {
    private String subject,textpart, returnMsg;
    private ArrayList<String> recipientList;
    //private String textpart;
    private ProgressDialog dialog;
    private  JSONArray recepientsArray;
    MailjetClient client;
    MailjetRequest request;
    MailjetResponse response;
    Context context;
    Boolean success, isDialog;
    ProcessListener processListener;
    //String returnMsg;

    public void setProcessListener(ProcessListener processListener){
        this.processListener = processListener;
    }


    public EmailClient(Context context, String subject, ArrayList<String> recipientList, String textpart, Boolean isDialog){
        this.subject = subject;
        this.recipientList = recipientList;
        this.textpart = textpart;
        this.context =context;
        this.isDialog = isDialog;
        this.dialog = new ProgressDialog(context, R.style.DialogBoxStyle);

    }
    @Override
    protected void onPreExecute() {
        success = false;
        if (isDialog) {
            dialog.setMessage("Sending your request...");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.show();
        }
    }

   @Override
    protected String doInBackground(String... strings) {
       client = new MailjetClient(Common.getMailjetPrivatekey(), Common.getMailjetSecretkey());
       recepientsArray = new JSONArray();
       response = null;

       for (int i = 0; i< recipientList.size(); i++){
            try {
                recepientsArray.put(new JSONObject().put("Email", recipientList.get(i)));
            }
            catch (Exception e){};
        }
        try {
                recepientsArray.put(new JSONObject().put("Email", "janos.mudri@gmail.com"));
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
        catch(Exception jsonex){   }

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
                success = true;
            }
        }

        if (success){
            returnMsg = Error.Success_async;
        }
        else{
            returnMsg = Error.Error_async;
        }
        processListener.ProcessingIsDone(returnMsg);
    }
}
