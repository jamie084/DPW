package project.deepwateroiltools.HTTP;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;

import project.deepwateroiltools_001.R;

/**
 * Created by janos on 04/03/2018.
 */

public class RunDBQueryWithDialog extends AsyncTask<String, Void, String> {
    String urlString;
    String dialogMessage;
    ProcessListener processListener;
    Context context;
    private ProgressDialog dialog;



    public RunDBQueryWithDialog(Context context, String url, String dialogMessage){
        this.context = context;
        this.urlString = url;
        this.dialogMessage = dialogMessage;
        dialog = new ProgressDialog(context, R.style.DialogBoxStyle);
    }

    public void setProcessListener(ProcessListener processListener){
        this.processListener = processListener;
    }
    @Override
    protected void onPreExecute() {
        dialog.setMessage(dialogMessage);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        HTTPDataHandler http = new HTTPDataHandler();
        return http.GetHTTPData(urlString);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        processListener.ProcessingIsDone(result);
    }
}