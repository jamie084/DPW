package project.deepwateroiltools_001.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import project.deepwateroiltools_001.R;

/**
 * Created by janos on 06/02/2018.
 */

public class FragmentHomeScreen extends Fragment implements View.OnClickListener {
    View view;
    Button btn_startSeaSecure;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        Log.d("WelcomScreenFragemtn","LOADED");
        btn_startSeaSecure = (Button) view.findViewById(R.id.btn_startSeaSecure);
        btn_startSeaSecure.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {

        if (v == btn_startSeaSecure){
            Toast.makeText(getActivity(), "WelcomeScreen btn Click", Toast.LENGTH_LONG).show();
        }
    }
}
