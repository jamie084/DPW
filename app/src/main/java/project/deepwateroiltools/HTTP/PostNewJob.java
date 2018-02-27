package project.deepwateroiltools.HTTP;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import project.deepwateroiltools_001.R;
import project.deepwateroiltools_001.SeaCure;
import project.dto.SeaCure_job;
import project.dto.user.User;

/**
 * Created by janos on 27/02/2018.
 */

public class PostNewJob extends AsyncTask<String, Void, String> {
    String urlString;
    private ProgressDialog dialog;
    SeaCure_job seaCure_job;
    Context context;

    public PostNewJob(Context context, SeaCure_job seaCure_job) {
        this.urlString = urlString;
        this.context = context;
        this.seaCure_job = seaCure_job;
        dialog = new ProgressDialog(context, R.style.DialogBoxStyle);
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
        Log.d("Seacure job", seaCure_job.toString());
        HTTPDataHandler http = new HTTPDataHandler();
        String par = new Gson().toJson(seaCure_job);
        String urlString = Common.getUrlSeaCureJobs() + Common.getApiKey();
        http.PostHTTPData(urlString,par);
        String url = Common.getUrlSeaCureJobs() + Common.getApiKey() ;//+ "&q={\"user\":\"" + user.getUser() + "\"}";

        return http.GetHTTPData(url);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        SeaCure_job seaCure_jobReply = new SeaCure_job();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<SeaCure_job>>(){}.getType();
        List<SeaCure_job> jobs = gson.fromJson(s, listType);
        seaCure_jobReply = jobs.get(0);
        SeaCure ss = (SeaCure)context;
        ss.getSeaCure_job().set_id(seaCure_jobReply.get_id());


        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        Log.d("response? ", s);

    }
}