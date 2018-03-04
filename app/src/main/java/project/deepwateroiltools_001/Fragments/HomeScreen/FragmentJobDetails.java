package project.deepwateroiltools_001.Fragments.HomeScreen;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import project.deepwateroiltools.HTTP.PermissionManager;
import project.deepwateroiltools_001.HomeScreen;
import project.deepwateroiltools_001.R;
import project.dto.SeaCure_job;
import project.dto.user.User;
import project.iTextPDF.CreatePDF;

/**
 * Created by janos on 04/03/2018.
 */

public class FragmentJobDetails extends Fragment implements View.OnClickListener{
    View view;
    Button btn_back, btn_exportPDF;
    SeaCure_job seaCure_job;
    User user;
    TextView crossfade_text;
    PermissionManager permissionManager;
    CreatePDF createPDF;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_job_details, container, false);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Log.d("WelcomScreenFragemtn","LOADED");
        btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_exportPDF = (Button) view.findViewById(R.id.btn_exportPDF);
        btn_back.setOnClickListener(this);
        btn_exportPDF.setOnClickListener(this);

        crossfade_text = (TextView) view.findViewById(R.id.crossfade_text);
        crossfade_text.setText(seaCure_job.toString());

        user = ((HomeScreen) this.getActivity()).getUser();

        return view;

    }

    public void setSeaCure_job(SeaCure_job seaCure_job) {
        this.seaCure_job = seaCure_job;
    }
    @Override
    public void onClick(View v) {
        if (v == btn_back){
            getFragmentManager().popBackStackImmediate();
        }
        else if (v == btn_exportPDF){
            permissionManager = new PermissionManager(getActivity());
            if (permissionManager.WriteAndReadExternal()){
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.HH:mm");
                String date = formatter.format(new Date(seaCure_job.getStartDate()));
                createPDF = new CreatePDF(getContext(), seaCure_job.toString(), user.getUserInfo().getFullName() + "_" + date);
                createPDF.createandDisplayPdf();
            }
        }
    }
}
