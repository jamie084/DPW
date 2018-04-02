package project.deepwateroiltools_001.Fragments.HomeScreen;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import project.deepwateroiltools_001.SeaCure;
import project.dto.DotSerail;
import project.helpers.PermissionManager;
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
    Button  btn_exportPDF, btn_load;
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

        //pdf export uses
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //btn declarations
        btn_exportPDF = (Button) view.findViewById(R.id.btn_exportPDF);
        btn_load = (Button) view.findViewById(R.id.btn_load);

        //btn listeners
        btn_exportPDF.setOnClickListener(this);
        btn_load.setOnClickListener(this);

        crossfade_text = (TextView) view.findViewById(R.id.crossfade_text);
        crossfade_text.setText(seaCure_job.toString());

        //get the logged in user
        user = ((HomeScreen) this.getActivity()).getUser();

        //override the back key to go back to the previous fragment
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStackImmediate();
                    return true;
                }
                return false;
            }
        });

        return view;

    }

    public void setSeaCure_job(SeaCure_job seaCure_job) {
        this.seaCure_job = seaCure_job;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.btn_exportPDF:
                permissionManager = new PermissionManager(getActivity());
                if (permissionManager.WriteAndReadExternal()){
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.HH:mm");
                    String date = formatter.format(new Date(seaCure_job.getStartDate()));
                    createPDF = new CreatePDF(getContext(), seaCure_job,  user.getUserInfo().getFullName() + "_" + date);
                    createPDF.execute();
                }
                break;

            case R.id.btn_load:
                Intent intent = new Intent(getContext(), SeaCure.class);
                intent.putExtra("loadedSeaCureJob", (new Gson().toJson(seaCure_job)));
                intent.putExtra("user", (new Gson().toJson(user)));

                //put the latest serial numbers into the loadable file
                DotSerail dotSerail = ((HomeScreen   ) this.getActivity()).getDotSerail();
                intent.putExtra("dotserial", (new Gson().toJson(dotSerail)));

                startActivity(intent);
                break;
        }
    }
}
