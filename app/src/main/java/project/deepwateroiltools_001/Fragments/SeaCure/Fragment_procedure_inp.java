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
import project.dto.SeaCure_job;
import project.dto.service.ProcedureInput;

/**
 * Created by janos on 06/02/2018.
 */

public class Fragment_procedure_inp extends Fragment  {
    View view;
   // Button btn_left;
    ProcedureInput procedureInput;
    TextView lbl_procInp;
    String inputTo;
    List<String> inputList;
    List<String> inputTypes;
    LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_procedure_input, container, false);


        lbl_procInp = (TextView) view.findViewById(R.id.lbl_procInputTitle);
        lbl_procInp.setText(procedureInput.getTitle());

        inputList = procedureInput.getInputList();
        inputTo = procedureInput.getInputTo();
        inputTypes = procedureInput.getInputTypes();

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
        SeaCure_job seaCure_job = ((SeaCure) this.getActivity()).getSeaCure_job();
        try {
            EditText editText =(EditText)getView().findViewById(0);
            String editTextString = editText.getText().toString();
            switch(inputTo) {

                case "seaCure_ORing":
                    seaCure_job.setWo_oRing(Integer.valueOf(editTextString));
                    break;
                case "seaCure_UpperRing":
                    seaCure_job.setWo_upperBearingRing(Integer.valueOf(editTextString));
                    break;
                case "seaCure_MiddleRing":
                    seaCure_job.setWo_middleBearingRing(Integer.valueOf(editTextString));
                    break;
                case "seaCure_StubORing":
                    seaCure_job.setWo_STUB_acme_O_ring(Integer.valueOf(editTextString));
                    break;
                case "seaCure_LowerRing":
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
            }

            Log.d("seaCureJOB: ", seaCure_job.toString()) ;
        }
        catch (Exception e){
            //TODO errorlogger build
            Log.d("SaveValuesEx", e.toString());
        }
    }



    public void setProcedureSlide(ProcedureInput procedureInput) {
        this.procedureInput = procedureInput;
    }



}