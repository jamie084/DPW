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

public class FragmentExport extends Fragment implements View.OnClickListener {
    View view;
    Button btn_export;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_export, container, false);
        Log.d("WelcomScreenFragemtn","LOADED");
        btn_export = (Button) view.findViewById(R.id.btn_export);
        btn_export.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {

        if (v == btn_export){
            Toast.makeText(getActivity(), "Export btn Click", Toast.LENGTH_LONG).show();
        }
    }
}