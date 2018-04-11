package project.deepwateroiltools_001.Fragments.SeaCure;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools.HTTP.RunDBQueryWithDialog;
import project.deepwateroiltools_001.R;
import project.deepwateroiltools_001.SeaCure;
import project.dto.DotSerail;
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
    DotSerail dotSerail;


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
        dotSerail = ( ((SeaCure) this.getActivity()).getDotSerail());


        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == btn_goto){
            if (subType != null) {
                if (subType.equals("threadDamage")) {
                    seaCure_job.setPin_thread_damage(true);
                }
                if (subType.equals("finish")){
                    seaCure_job.setFinished(true);
                    seaCure_job.setFinishDate(System.currentTimeMillis());
                    dotSerail.setDOT_SCR_INB(seaCure_job.getSn_out_DOT_SCR_INB());
                    dotSerail.setDOT_SCR_IBH(seaCure_job.getSn_out_DOT_SCR_IBH());
                    dotSerail.setDOT_SCR_PBR(seaCure_job.getSn_out_DOT_SCR_PBR());
                    dotSerail.setDOT_SCR_TRB(seaCure_job.getSn_out_DOT_SCR_TRB());
                    String url = Common.getUrlDotSerial() + Common.getApiKey();
                    RunDBQueryWithDialog runDBQueryWithDialog = new RunDBQueryWithDialog(getContext(), url, "", new Gson().toJson(dotSerail));
                    runDBQueryWithDialog.setProcessListener(new ProcessListener() {
                        @Override
                        public void ProcessingIsDone(String result) {

                        }
                    });
                    runDBQueryWithDialog.execute();
                    SeaCure seaCure = (SeaCure) this.getActivity();
                    seaCure.upLoadSeaCureJob();
                }
            }

            SeaCure seaCure = (SeaCure)getActivity();
            seaCure.setChildId(this.procedureSlide.getAltChildId());
            seaCure.stepNextProcedureSlide();
        }
    }
}