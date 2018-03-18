//package project.deepwateroiltools_001.Fragments;
package project.deepwateroiltools_001.Fragments.HomeScreen;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
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
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools.HTTP.RunDBQueryWithDialog;
import project.deepwateroiltools_001.HomeScreen;
import project.deepwateroiltools_001.R;
import project.deepwateroiltools_001.SeaCure;
import project.dto.DotSerail;
import project.dto.SeaCure_job;
import project.dto.user.User;

/**
 * Created by janos on 06/02/2018.
 */

public class FragmentAdminArea extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private View view;
    private Button btn_adminTest;
    private RunDBQueryWithDialog runDBQuery;
    private List<User> users;
    private DotSerail dotSerail;
    private ListView mainListView;
    private List<SeaCure_job> jobs;
    private User user;

    int check = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_area, container, false);

        Log.d("AdminScreenFragemtn","LOADED");

        btn_adminTest = (Button) view.findViewById(R.id.btn_adminTest);
        btn_adminTest.setOnClickListener(this);

        HomeScreen homeScreen = (HomeScreen)getActivity();
        dotSerail = homeScreen.getDotSerail();
        user = homeScreen.getUser();



        // Find the ListView resource.
        mainListView = (ListView)view. findViewById( R.id.mainListView );

        //url to get all the users, but only including the id and the name fields
        String url = Common.getUrlUser() + Common.getApiKey() + "&f={\"_id\":1,\"userInfo.firstName\":1,\"userInfo.secondName\":1}";;

        //run an async method to populate the procedureslide list
        runDBQuery = new RunDBQueryWithDialog(getActivity(), url, "Loading...");
        runDBQuery.setProcessListener(new ProcessListener() {
            @Override
            public void ProcessingIsDone(String result) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<User>>() {
                }.getType();
                users = gson.fromJson(result, listType);

                if (!users.isEmpty()) {
                    Log.d("users", result);
                    List<String> elements = new ArrayList<>();

                    for (User user:users) {
                        elements.add(user.getUserInfo().getFullName());
                    }

                    createDdl(elements, "Users", 0);

                    elements = new ArrayList<>();
                    for (Integer i:dotSerail.getSeaCureToolSerials()) {
                        elements.add(String.valueOf(i));
                    }
                    createDdl(elements, "SeaCure Tool Serials: ", 1);

                }
                else{
                    Toast.makeText(getActivity(), "An error occured, please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });

        runDBQuery.execute();

        return view;

    }

    public void createDdl(List<String> elements, String title, Integer id){
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (320 * scale + 0.5f);



        TextView tv = new TextView(getContext());
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        elements.add("Please select");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, elements ){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner s = new Spinner(getContext());
        s.setPopupBackgroundResource(R.color.md_black_1000);

        s.setAdapter(adapter);
        s.setSelection(adapter.getCount());
        s.setMinimumWidth(pixels/2);
        s.setGravity(Gravity.CENTER_HORIZONTAL);
        s.setOnItemSelectedListener(this);

        s.setId(id);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        layoutParams.setMargins(30, 20, 30, 0);
        linearLayout.addView(tv, layoutParams);
        linearLayout.addView(s, layoutParams);


        linearLayout.setMinimumHeight(pixels);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onClick(View v) {

        if (v == btn_adminTest){
            Toast.makeText(getActivity(), "Admin btn Click", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(++check > 2) {
            String url = "";
            Toast.makeText(getContext(), String.valueOf(parent.getId()), Toast.LENGTH_SHORT).show();
            //url to seacure procedure db entries
            for (User user : users) {

            }
            switch (parent.getId()) {
                case 0:
                    if (position < users.size()) {
                        url = Common.getUrlSeaCureJobs() + Common.getApiKey() + "&q={\"_user_id\":\"" + users.get(position).get_id().getOid() + "\"}";
                    }
                    break;
                case 1:
                    if (position < dotSerail.getSeaCureToolSerials().size()) {
                        url = Common.getUrlSeaCureJobs() + Common.getApiKey() + "&q={\"tool_serial\":" + dotSerail.getSeaCureToolSerials().get(position) + "}";
                    }
                    break;
            }

            //run an async method to populate the procedureslide list
            runDBQuery = new RunDBQueryWithDialog(getActivity(), url, "Loading...");
            runDBQuery.setProcessListener(new ProcessListener() {
                @Override
                public void ProcessingIsDone(String result) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<SeaCure_job>>() {
                    }.getType();

                    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
                    jobs = gson.fromJson(result, listType);

                    if (!jobs.isEmpty()) {
                        Log.d("users", result);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        for (int i = 0; i < jobs.size(); i++) {
                            Map<String, String> datum = new HashMap<String, String>(2);
                            datum.put("title", "Start: " + formatter.format(new Date(jobs.get(i).getStartDate())) + (jobs.get(i).isFinished() ?  " Completed" : " Not-Completed"));
                            datum.put("date", "Operator: " + jobs.get(i).getClientOperator() + "\nCompany: " ); //TODO get the user from user list+  user.getUserInfo().getCompany());
                            data.add(datum);
                        }



                    } else {
                        Toast.makeText(getActivity(), "No job found with the selected criteria.", Toast.LENGTH_SHORT).show();
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
                            check = 0;
                            FragmentJobDetails fragmentJobDetails = new FragmentJobDetails();
                            fragmentJobDetails.setSeaCure_job(jobs.get(position));

                            loadFragment(fragmentJobDetails);
                        }
                    });

                }
            });

                runDBQuery.execute();



            if (id == 0) {
                if (position < users.size()) {
                    Toast.makeText(parent.getContext(),
                            "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString() + " test: " + users.get(position).get_id().getOid(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
