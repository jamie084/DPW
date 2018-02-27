package project.deepwateroiltools_001.Fragments.HomeScreen;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.HTTPDataHandler;
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools.HTTP.ProcessListenerListView;
import project.deepwateroiltools_001.HomeScreen;
import project.deepwateroiltools_001.R;
import project.deepwateroiltools_001.Registration;
import project.deepwateroiltools_001.SeaCure;
import project.dto.SeaCure_job;
import project.dto.user.User;

/**
 * Created by janos on 06/02/2018.
 */

public class FragmentHistory extends Fragment implements View.OnClickListener {
    View view;
    Button btn_history;
    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);
        Log.d("History","LOADED");
        btn_history  = (Button) view.findViewById(R.id.btn_history);
        btn_history.setOnClickListener(this);

        // Find the ListView resource.
        mainListView = (ListView)view. findViewById( R.id.mainListView );

        user = ((HomeScreen) this.getActivity()).getUser();

//        // Create and populate a List of planet names.
//        String[] planets = new String[] { "Mercury", "Venus", "Earth", "Mars",
//                "Jupiter", "Saturn", "Uranus", "Neptune"};
//
//
//        ArrayList<String> planetList = new ArrayList<String>();
//        planetList.addAll( Arrays.asList(planets) );
//        //my chnge
//
//
//        // Create ArrayAdapter using the planet list.
//
//        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simplerow, planetList);
//
//
//        // Add more planets. If you passed a String[] instead of a List<String>
//        // into the ArrayAdapter constructor, you must not add more items.
//        // Otherwise an exception will occur.
//        listAdapter.add( "Ceres" );
////        listAdapter.add( "Pluto" );
////        listAdapter.add( "Haumea" );
////        listAdapter.add( "Makemake" );
////        listAdapter.add( "Eris" );
//
//        // Set the ArrayAdapter as the ListView's adapter.
//        mainListView.setAdapter( listAdapter );
        //





        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final ArrayList<String> jobList  = new ArrayList<String>();
        RunDbQueryToGetJobsByUser runDbQueryToGetJobsByUser = new RunDbQueryToGetJobsByUser("");

        runDbQueryToGetJobsByUser.setProcessListenerListView(new ProcessListenerListView() {
            @Override
            public void ProcessingIsDone(List<String> result) {
                // lbl_info.setText(result);
              //  jobList.addAll( Arrays.asList(result) );
                listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simplerow, result);
                mainListView.setAdapter( listAdapter );
            }
        });
        runDbQueryToGetJobsByUser.execute();

    }

    @Override
    public void onClick(View v) {

        if (v == btn_history){
            RunDbQueryToGetJobsByUser runDbQueryToGetJobsByUser = new RunDbQueryToGetJobsByUser("");
            runDbQueryToGetJobsByUser.execute();

            Toast.makeText(getActivity(), "history btn Click", Toast.LENGTH_LONG).show();
        }
    }

    private class RunDbQueryToGetJobsByUser extends AsyncTask<String, Void, String> {
        String urlString;
        String url = Common.getUrlSeaCureJobs() + Common.getApiKey() ;//+ "&q={\"_user_id\":\"" + user.get_id().toString() + "\"}";
        ProcessListenerListView processListener;
        private ProgressDialog dialog;

        public void setProcessListenerListView(ProcessListenerListView processListener){
            this.processListener = processListener;
        }

        private RunDbQueryToGetJobsByUser(String url){
            this.urlString = url;
            dialog = new ProgressDialog(getContext(), R.style.DialogBoxStyle);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Searching...");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HTTPDataHandler http = new HTTPDataHandler();

//            String par = "{\"name\":\"myname\",\"age\":\"20\"}";
//
//            String urlString = Common.getBaseURL() + Common.getApiKey();
//            http.PostHTTPData(urlString,par);
            return http.GetHTTPData(url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<SeaCure_job>>(){}.getType();
            List<SeaCure_job> jobs = gson.fromJson(s, listType);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            //Display icon according to search results
            if (!jobs.isEmpty()) {
                Log.d("JOBS not empty", jobs.get(0).toString());
                List<String> result = new ArrayList<>();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Log.d("userId", user.get_id().getOid());
                for (int i=0; i<jobs.size(); i++){
                    if (jobs.get(i).get_user_id().getOid().equals(user.get_id().getOid())) {
                        result.add(formatter.format(new Date(jobs.get(i).getStartDate())) + " " + jobs.get(i).getClientOperator());
                    }
                }


                processListener.ProcessingIsDone(result);
            }
            else{
//                emailIsValid = true;
//                img_email.setImageResource(R.drawable.true_img);
//                img_email.setVisibility(View.VISIBLE);
            }
        }
    }
}