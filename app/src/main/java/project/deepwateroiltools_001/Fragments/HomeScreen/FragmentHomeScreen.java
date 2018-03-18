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
    DotSerail dotSerail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_screen, container, false);

        btn_startSeaCure = (Button) view.findViewById(R.id.btn_startSeaSecure);
        btn_startSeaCure.setOnClickListener(this);

        HomeScreen homeScreen = (HomeScreen)getActivity();
        user = homeScreen.getUser();
        dotSerail = homeScreen.getDotSerail();



       return view;

    }

    @Override
    public void onClick(View v) {


        if (v == btn_startSeaCure){
            Intent intent = new Intent(getActivity(), SeaCure.class);
            intent.putExtra("user", (new Gson()).toJson(user));
            intent.putExtra("dotserial", (new Gson().toJson(dotSerail)));
            startActivity(intent);
        }
    }
}
