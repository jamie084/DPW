package project.deepwateroiltools_001.Fragments.HomeScreen;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.HTTPDataHandler;
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools.HTTP.RunDBQueryWithDialog;
import project.deepwateroiltools_001.HomeScreen;
import project.deepwateroiltools_001.R;
import project.dto.SeaCure_job;
import project.dto.user.User;

/**
 * Created by janos on 06/02/2018.
 */

public class FragmentHistory extends Fragment implements View.OnClickListener{
    private View view;
    private Button btn_history, dialog_btn;
    private List<SeaCure_job> jobs;
    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;
    private ArrayList<String> jobList;
    private User user;
    private RunDBQueryWithDialog runDBQueryWithDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);
//        btn_history  = (Button) view.findViewById(R.id.btn_history);
//        btn_history.setOnClickListener(this);

        // Find the ListView resource.
        mainListView = (ListView)view. findViewById( R.id.mainListView );

        user = ((HomeScreen) this.getActivity()).getUser();
        jobList = new ArrayList<>();

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //url to run run the db query, filtered by the logged in user id
        String url =  Common.getUrlSeaCureJobs() + Common.getApiKey() + "&q={\"_user_id\":\"" + user.get_id().getOid() + "\"}";
        runDBQueryWithDialog = new RunDBQueryWithDialog(getContext(), url, "Searching...");

        runDBQueryWithDialog.setProcessListener(new ProcessListener() {
                @Override
                public void ProcessingIsDone(final String result) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<SeaCure_job>>() {
                    }.getType();
                    jobs = gson.fromJson(result, listType);
                    List<Map<String, String>> data = new ArrayList<Map<String, String>>();

                    if (!jobs.isEmpty()) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        for (int i = 0; i < jobs.size(); i++) {
                            Map<String, String> datum = new HashMap<String, String>(2);
                            datum.put("title", "Start: " + formatter.format(new Date(jobs.get(i).getStartDate())) + (jobs.get(i).isFinished() ?  " Completed" : " Not-Completed"));
                            datum.put("date", "Operator: " + jobs.get(i).getClientOperator() + "\nCompany: " +  user.getUserInfo().getCompany());
                            data.add(datum);
                        }
                    } else {
                        Log.d("jobs empty", "rrrr");
                    }


                    SimpleAdapter adapter = new SimpleAdapter (getActivity(), data,
                            android.R.layout.simple_list_item_2,
                            new String[] {"title", "date"},
                            new int[] {android.R.id.text1,
                                    android.R.id.text2,
                                    });

                    mainListView.setAdapter(adapter);

                    //onclick listener on the jobs to load the fragment with the job detials
                    mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            FragmentJobDetails fragmentJobDetails = new FragmentJobDetails();
                            fragmentJobDetails.setSeaCure_job(jobs.get(position));
                            loadFragment(fragmentJobDetails);
                        }
                    });
                }
            });
        runDBQueryWithDialog.execute();

    }

    @Override
    public void onClick(View v) {
//
//        if (v == btn_history){
//            Toast.makeText(getActivity(), "history btn Click", Toast.LENGTH_LONG).show();
//        }
    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.crossfade_content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}