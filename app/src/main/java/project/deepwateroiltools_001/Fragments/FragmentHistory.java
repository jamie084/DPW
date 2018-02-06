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

public class FragmentHistory extends Fragment implements View.OnClickListener {
    View view;
    Button btn_history;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);
        Log.d("History","LOADED");
        btn_history  = (Button) view.findViewById(R.id.btn_history);
        btn_history.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {

        if (v == btn_history){
            Toast.makeText(getActivity(), "history btn Click", Toast.LENGTH_LONG).show();
        }
    }
}