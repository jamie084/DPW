package project.deepwateroiltools_001.Fragments.SeaCure;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools_001.HomeScreen;
import project.deepwateroiltools_001.R;
import project.deepwateroiltools_001.SeaCure;
import project.dto.SeaCure_job;
import project.dto.user.User;
import project.helpers.PermissionManager;

/**
 * Created by janos on 12/03/2018.
 */

public class Fragment_note extends Fragment implements View.OnClickListener {
    View view;
    Button btn_submit;

    SeaCure_job seaCure_job;
    EditText inp_note;
    TextView lbl_info;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_note, container, false);

        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);


        inp_note = (EditText) view.findViewById(R.id.inp_note);

        lbl_info = (TextView) view.findViewById(R.id.lbl_info);

        final SeaCure seaCure = (SeaCure) getActivity();
        seaCure_job = seaCure.getSeaCure_job();

        return view;

    }

    @Override
    public void onClick(View v) {
        if (v == btn_submit){
            seaCure_job.addNote(inp_note.getText().toString());
            Toast.makeText(getContext(), "Note saved...", Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
        }
    }
}
