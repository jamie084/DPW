package project.deepwateroiltools_001.Fragments.HomeScreen;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools.HTTP.RunDBQueryWithDialog;
import project.dto.DotSerail;
import project.dto.user.User;
import project.deepwateroiltools_001.HomeScreen;
import project.deepwateroiltools_001.R;
import project.deepwateroiltools_001.SeaCure;

/**
 * Created by janos on 06/02/2018.
 */

public class FragmentHomeScreen extends Fragment implements View.OnClickListener {
    View view;
    Button btn_startSeaCure;
    User user;
    List<DotSerail> dotSerails;
    RunDBQueryWithDialog runDBQueryWithDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        Log.d("WelcomScreenFragemtn","LOADED");
        btn_startSeaCure = (Button) view.findViewById(R.id.btn_startSeaSecure);
        btn_startSeaCure.setOnClickListener(this);

       HomeScreen homeScreen = (HomeScreen)getActivity();
       user = homeScreen.getUser();

        runDBQueryWithDialog = new RunDBQueryWithDialog(getContext(), Common.getUrlDotSerial() + Common.getApiKey(), "");

        runDBQueryWithDialog.setProcessListener(new ProcessListener() {
            @Override
            public void ProcessingIsDone(final String result) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<DotSerail>>() {
                }.getType();
                dotSerails = gson.fromJson(result, listType);

                if (!dotSerails.isEmpty()) {
                    Log.d("serial" , dotSerails.get(0).getTool_type());

                } else {
                    Log.d("jobs empty", "rrrr");
                }


            }
        });
        runDBQueryWithDialog.execute();

       return view;

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), SeaCure.class);
        intent.putExtra("user", (new Gson()).toJson(user));

        if (v == btn_startSeaCure){
            Log.d("intent in", String.valueOf(dotSerails.size()));
            for (int i=0; i< dotSerails.size(); i++){
                Log.d("intent in", dotSerails.get(i).getTool_type());
                if (dotSerails.get(i).getTool_type().equals("SeaCure")){
                        intent.putExtra("dotserial", (new Gson().toJson(dotSerails.get(i))));

                }
            }

        startActivity(intent);
        }
    }
}
