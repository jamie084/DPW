package project.deepwateroiltools_001.Fragments.SeaCure;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import project.deepwateroiltools_001.R;
import project.deepwateroiltools_001.SeaCure;
import project.dto.SeaCure_job;
import project.dto.service.ProcedureDdl;
import project.dto.service.ProcedureSlide;

/**
 * Created by janos on 25/02/2018.
 */

public class Fragment_procedure_ddl extends Fragment implements  View.OnClickListener {
    View view;
    Button btn_left;
    ProcedureDdl procedureSlide;
    TextView lbl_procTitle;

    List<String> labels;
    List<List<String>> elements;
    String ddlType;
    LinearLayout linearLayout;
Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_procedure_ddl, container, false);

        button  = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(this);

        lbl_procTitle = (TextView) view.findViewById(R.id.lbl_procTitle);
        lbl_procTitle.setText(procedureSlide.getTitle());

        labels = procedureSlide.getLabels();
        elements = procedureSlide.getElements();
        ddlType = procedureSlide.getDdlType();

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (320 * scale + 0.5f);

        for (int i=0; i< labels.size(); i++) {

            TextView tv = new TextView(getContext());
            tv.setText(labels.get(i));
            tv.setGravity(Gravity.CENTER);
            elements.get(i).add("Please select");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item, elements.get(i) ){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    View v = super.getView(position, convertView, parent);
                    if (position == getCount()) {
                        ((TextView)v.findViewById(android.R.id.text1)).setText("");
                        ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                    }

                    return v;
                }

                @Override
                public int getCount() {
                    return super.getCount()-1; // you dont display last item. It is used as hint.
                }

            };


            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            Spinner s = new Spinner(getContext());
            s.setPopupBackgroundResource(R.color.md_black_1000);

            s.setAdapter(adapter);
            s.setSelection(adapter.getCount());
            s.setMinimumWidth(pixels/2);
            s.setGravity(Gravity.CENTER_HORIZONTAL);

            s.setId(i);


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            layoutParams.setMargins(30, 20, 30, 0);
            linearLayout.addView(tv, layoutParams);
            linearLayout.addView(s, layoutParams);
        }

        linearLayout.setMinimumHeight(pixels);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (int i=0;i<elements.size();i++){
            elements.get(i).remove(elements.get(i).size()-1);
        }
    }

    public boolean isAllSelected(){
        for (int i=0; i< elements.size(); i++){
            Spinner s = (Spinner)getView().findViewById(i);
            if (s.getSelectedItemPosition() == elements.get(i).size()-1){
                return false;
            }
        }
        return true;
    }

    public void saveValues(){
        SeaCure_job seaCure_job = ((SeaCure) this.getActivity()).getSeaCure_job();
        try {
            if (ddlType.equals("seaCure_in_serials")) {
                Spinner s0 = (Spinner) getView().findViewById(0);
                seaCure_job.setTool_serial(Integer.valueOf(s0.getSelectedItem().toString()));
                Spinner s1 = (Spinner) getView().findViewById(1);
                seaCure_job.setSn_in_DOT_SCR_TRB(Integer.valueOf(s1.getSelectedItem().toString()));
                Spinner s2 = (Spinner) getView().findViewById(2);
                seaCure_job.setSn_in_DOT_SCR_PBR(Integer.valueOf(s2.getSelectedItem().toString()));
                Spinner s3 = (Spinner) getView().findViewById(3);
                seaCure_job.setSn_in_DOT_SCR_INB(Integer.valueOf(s3.getSelectedItem().toString()));
                Spinner s4 = (Spinner) getView().findViewById(4);
                seaCure_job.setSn_in_DOT_SCR_IBH(Integer.valueOf(s4.getSelectedItem().toString()));
            }
        }
        catch (Exception e){
            Log.d("DDL-Parse-Exception", e.toString());
        }
    }



    @Override
    public void onClick(View v) {
        if (v == button){
            if (isAllSelected()){
                saveValues();
                SeaCure_job seaCure_job = ((SeaCure) this.getActivity()).getSeaCure_job();

                Log.d("Job" , seaCure_job.toString());
            }
        }

    }

    public ProcedureSlide getProcedureSlide() {
        return procedureSlide;
    }

    public void setProcedureSlide(ProcedureDdl procedureSlide) {
        this.procedureSlide = procedureSlide;
    }


}

