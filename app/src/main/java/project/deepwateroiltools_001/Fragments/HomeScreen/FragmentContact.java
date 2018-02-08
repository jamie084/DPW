package project.deepwateroiltools_001.Fragments.HomeScreen;

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

public class FragmentContact extends Fragment implements View.OnClickListener {
    View view;
    Button btn_contact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        Log.d("WelcomScreenFragemtn","LOADED");
        btn_contact  = (Button) view.findViewById(R.id.btn_contact);
        btn_contact.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {

        if (v == btn_contact){
            Toast.makeText(getActivity(), "contact btn Click", Toast.LENGTH_LONG).show();
        }
    }
}