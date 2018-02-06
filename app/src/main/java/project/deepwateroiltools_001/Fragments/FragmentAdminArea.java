//package project.deepwateroiltools_001.Fragments;
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

public class FragmentAdminArea extends Fragment implements View.OnClickListener {
    View view;
    Button btn_adminTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_area, container, false);

        Log.d("AdminScreenFragemtn","LOADED");

        btn_adminTest = (Button) view.findViewById(R.id.btn_adminTest);
        btn_adminTest.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {

        if (v == btn_adminTest){
            Toast.makeText(getActivity(), "Admin btn Click", Toast.LENGTH_LONG).show();
        }
    }
}
