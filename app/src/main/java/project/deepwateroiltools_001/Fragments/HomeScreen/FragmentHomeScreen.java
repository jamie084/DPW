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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        Log.d("WelcomScreenFragemtn","LOADED");
        btn_startSeaCure = (Button) view.findViewById(R.id.btn_startSeaSecure);
        btn_startSeaCure.setOnClickListener(this);

       HomeScreen ss = (HomeScreen)getActivity();
       user = ss.getUser();

        return view;

    }

    @Override
    public void onClick(View v) {

        if (v == btn_startSeaCure){
            Toast.makeText(getActivity(), user.getUser(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getActivity(), SeaCure.class);

//            user.setUser(email.getText().toString());
//            user.setPassword(password.getText().toString());
            intent.putExtra("user", (new Gson()).toJson(user));
            startActivity(intent);
        }
    }
}
