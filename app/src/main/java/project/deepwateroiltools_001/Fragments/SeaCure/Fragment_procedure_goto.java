package project.deepwateroiltools_001.Fragments.SeaCure;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import project.deepwateroiltools_001.R;
import project.deepwateroiltools_001.SeaCure;
import project.dto.SeaCure_job;
import project.dto.service.ProcedureGoto;
import project.dto.service.ProcedureSlide;

/**
 * Created by janos on 27/02/2018.
 */

public class Fragment_procedure_goto extends Fragment implements  View.OnClickListener {
    View view;
    Button btn_goto;
    ProcedureGoto procedureSlide;
    TextView lbl_procGoto;
    String subType;
    SeaCure_job seaCure_job;

    public ProcedureGoto getProcedureSlide() {
        return procedureSlide;
    }

    public void setProcedureSlide(ProcedureGoto procedureSlide) {
        this.procedureSlide = procedureSlide;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_procedure_goto, container, false);

        btn_goto  = (Button) view.findViewById(R.id.btn_goto);
        btn_goto.setOnClickListener(this);
        btn_goto.setText(procedureSlide.getBtnLabel());

        lbl_procGoto = (TextView) view.findViewById(R.id.lbl_procGoto);
        lbl_procGoto.setText(procedureSlide.getTitle());

        subType = procedureSlide.getSubType();

        seaCure_job = ((SeaCure) this.getActivity()).getSeaCure_job();


        Log.d("fragmant created", "GOTO");

        return view;



    }

    @Override
    public void onClick(View v) {
        if (v == btn_goto){
            if (subType != null) {
                if (subType.equals("threadDamage")) {
                    seaCure_job.setPin_thread_damage(true);
                }
            }

            SeaCure seaCure = (SeaCure)getActivity();
            seaCure.setChildId(this.procedureSlide.getAltChildId());
            seaCure.stepNextProcedureSlide();
        }
    }
}