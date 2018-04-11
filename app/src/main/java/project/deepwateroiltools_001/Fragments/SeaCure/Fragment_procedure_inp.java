package project.deepwateroiltools_001.Fragments.SeaCure;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import project.deepwateroiltools_001.R;
import project.deepwateroiltools_001.SeaCure;
import project.dto.DotSerail;
import project.dto.SeaCure_job;
import project.dto.service.ProcedureInput;

/**
 * Created by janos on 06/02/2018.
 */

public class Fragment_procedure_inp extends Fragment  {
    View view;
    ProcedureInput procedureInput;
    TextView lbl_procInp;
    String inputTo;
    List<String> inputList;
    List<String> inputTypes;
    LinearLayout linearLayout;
    SeaCure_job seaCure_job;
    DotSerail dotSerail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_procedure_input, container, false);


        lbl_procInp = (TextView) view.findViewById(R.id.lbl_procInputTitle);
        lbl_procInp.setText(procedureInput.getTitle());

        inputList = procedureInput.getInputList();
        inputTo = procedureInput.getInputTo();
        inputTypes = procedureInput.getInputTypes();

        seaCure_job = ((SeaCure) this.getActivity()).getSeaCure_job();
        dotSerail = ((SeaCure) this.getActivity()).getDotSerail();


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (320 * scale + 0.5f);

        for (int i=0; i< inputList.size(); i++) {

            TextView tv = new TextView(getContext());
            tv.setText(inputList.get(i));

            EditText et = (EditText)getActivity().getLayoutInflater().inflate(R.layout.et_template, null);

            et.setId(i);
            tv.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            layoutParams.setMargins(30, 20, 30, 0);
            linearLayout.addView(tv, layoutParams);
            linearLayout.addView(et, layoutParams);
        }

        linearLayout.setMinimumHeight(pixels);
        loadValues();

    }

    public void loadValues(){
        try {
            EditText et0 =(EditText)getView().findViewById(0);
            String editTextString = et0.getText().toString();
            switch(inputTo) {

                case "loctite_lot_number":
                    et0.setText(seaCure_job.getLoctite_lot_number());
                    break;
                case "seaCure_ORing":
                    et0.setText(String.valueOf(seaCure_job.getWo_oRing()));
                    break;
                case "seaCure_UpperRing":
                    et0.setText(String.valueOf(seaCure_job.getWo_upperBearingRing()));
                    break;
                case "seaCure_MiddleRing":
                    et0.setText(String.valueOf(seaCure_job.getWo_middleBearingRing()));
                    break;
                case "seaCure_StubORing":
                    et0.setText(String.valueOf(seaCure_job.getWo_STUB_acme_O_ring()));
                    break;
                case "seaCure_LowerRing":
                    et0.setText(String.valueOf(seaCure_job.getWo_lowerBearingRing()));
                    break;
                case "seaCure_torque_break_out":
                    et0.setText(String.valueOf(seaCure_job.getTorque_SCR_TRB()));
                    break;
                case "seaCure_main":
                    et0.setText(seaCure_job.getClientOperator());
                    EditText et1 =(EditText)getView().findViewById(1);
                    et1.setText(String.valueOf(seaCure_job.getPo_reference()));
                    EditText et2 =(EditText)getView().findViewById(2);
                    et2.setText(seaCure_job.getRegion());
                    break;
                case "seaCure_torque_make_up":
                    et0.setText(seaCure_job.getTorque_SCR_TRB_end());
                    break;
                case "seaCure_dot_out":
                    EditText pbr = (EditText) getView().findViewById(1);
                    EditText inb = (EditText) getView().findViewById(2);
                    EditText ibh = (EditText) getView().findViewById(3);

                    if (seaCure_job.getSn_out_DOT_SCR_TRB() == 0) {
                        et0.setText(String.valueOf(dotSerail.getDOT_SCR_TRB()));
                    }
                    else{
                        et0.setText(String.valueOf(seaCure_job.getSn_out_DOT_SCR_TRB()));
                    }
                    if (seaCure_job.getSn_out_DOT_SCR_PBR() == 0) {

                        pbr.setText(String.valueOf(dotSerail.getDOT_SCR_PBR()));
                    }
                    else{
                        pbr.setText(String.valueOf(seaCure_job.getSn_out_DOT_SCR_PBR()));
                    }
                    if (seaCure_job.getSn_out_DOT_SCR_INB() == 0) {
                        inb.setText(String.valueOf(dotSerail.getDOT_SCR_INB()));
                    }
                    else{
                        inb.setText(String.valueOf(seaCure_job.getSn_out_DOT_SCR_INB()));
                    }

                    if (seaCure_job.getSn_out_DOT_SCR_IBH() == 0) {
                        ibh.setText(String.valueOf(dotSerail.getDOT_SCR_IBH()));
                    }
                    else{
                        ibh.setText(String.valueOf(seaCure_job.getSn_out_DOT_SCR_IBH()));
                    }
            }
        }
        catch (Exception e){
            //TODO errorlogger build
            Log.d("LoadValues", e.toString());
        }
    }


    //TODO extend the validation with input types (like string, int, etc...)
    public boolean isValid(){
        //close the keypad if active
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        for (int i=0; i< inputList.size(); i++){
            EditText c = (EditText)getView().findViewById(i);
            String inputToValidate = c.getText().toString();
            if (inputToValidate.trim().length() == 0){
                return false;
            }
        }

        return true;
    }

    public void saveValues(){
        try {
            EditText editText =(EditText)getView().findViewById(0);
            String editTextString = editText.getText().toString();
            switch(inputTo) {

                case "loctite_lot_number":
                    seaCure_job.setLoctite_lot_number(editTextString);
                    break;
                case "seaCure_ORing":
                    seaCure_job.setORingReplaced(true);
                    seaCure_job.setWo_oRing(Integer.valueOf(editTextString));
                    break;
                case "seaCure_UpperRing":
                    seaCure_job.setUpperBearingRingReplaced(true);
                    seaCure_job.setWo_upperBearingRing(Integer.valueOf(editTextString));
                    break;
                case "seaCure_MiddleRing":
                    seaCure_job.setMiddelBearingRingReplaced(true);
                    seaCure_job.setWo_middleBearingRing(Integer.valueOf(editTextString));
                    break;
                case "seaCure_StubORing":
                    seaCure_job.setStubAcmeRingReplaced(true);
                    seaCure_job.setWo_STUB_acme_O_ring(Integer.valueOf(editTextString));
                    break;
                case "seaCure_LowerRing":
                    seaCure_job.setLowerBearingRingReplaced(true);
                    seaCure_job.setWo_lowerBearingRing(Integer.valueOf(editTextString));
                    break;
                case "seaCure_torque_break_out":
                    seaCure_job.setTorque_SCR_TRB(Integer.valueOf(editTextString));
                    break;
                case "seaCure_main":
                    seaCure_job.setClientOperator(editTextString);

                    EditText po =(EditText)getView().findViewById(1);
                    seaCure_job.setPo_reference(Integer.valueOf(po.getText().toString()));

                    EditText reg =(EditText)getView().findViewById(2);
                    seaCure_job.setRegion(reg.getText().toString());
                    break;
                case "seaCure_torque_make_up":
                    seaCure_job.setTorque_SCR_TRB_end(Integer.valueOf(editTextString));
                    break;
                case "seaCure_dot_out":
                    seaCure_job.setSn_out_DOT_SCR_TRB(Integer.valueOf(editTextString));

                    EditText pbr =(EditText)getView().findViewById(1);
                    seaCure_job.setSn_out_DOT_SCR_PBR(Integer.valueOf(pbr.getText().toString()));

                    EditText inb =(EditText)getView().findViewById(2);
                    seaCure_job.setSn_out_DOT_SCR_INB(Integer.valueOf(inb.getText().toString()));

                    EditText ibh =(EditText)getView().findViewById(3);
                    seaCure_job.setSn_out_DOT_SCR_IBH(Integer.valueOf(ibh.getText().toString()));

            }

          //  Log.d("seaCureJOB: ", seaCure_job.toString()) ;
        }
        catch (Exception e){
            //TODO errorlogger build
            //Log.d("SaveValuesEx", e.toString());
        }
    }



    public void setProcedureSlide(ProcedureInput procedureInput) {
        this.procedureInput = procedureInput;
    }



}