//package project.deepwateroiltools_001.Fragments;
package project.deepwateroiltools_001.Fragments.HomeScreen;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools.HTTP.RunDBQueryWithDialog;
import project.deepwateroiltools_001.HomeScreen;
import project.deepwateroiltools_001.R;
import project.dto.DotSerail;
import project.dto.user.User;

/**
 * Created by janos on 06/02/2018.
 */

public class FragmentAdminArea extends Fragment implements View.OnClickListener {
    private View view;
    private Button btn_adminTest;
    private RunDBQueryWithDialog runDBQuery;
    private List<User> users;
    private DotSerail dotSerail;

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
        Log.d("dotSerial", String.valueOf(dotSerail.getSeaCureToolSerials().get(0)));

        List<String> elements = new ArrayList<>();
        for (Integer i:dotSerail.getSeaCureToolSerials()) {
            elements.add(String.valueOf(i));
        }
        createDdl(elements, "SeaCure Tool Serials: ");

        //url to seacure procedure db entries
        String url = Common.getUrlUser() + Common.getApiKey();




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

                    createDdl(elements, "Users");

                }
                else{
                    Toast.makeText(getActivity(), "An error occured, please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });

        runDBQuery.execute();

        return view;

    }

    public void createDdl(List<String> elements, String title){
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

        //s.setId(i);


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
}
