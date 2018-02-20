package project.deepwateroiltools_001.Fragments.SeaCure;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import project.deepwateroiltools_001.R;
import project.dto.service.ProcedureInput;

/**
 * Created by janos on 06/02/2018.
 */

public class Fragment_procedure_inp extends Fragment  {
    View view;
   // Button btn_left;
    ProcedureInput procedureInput;
    TextView lbl_operator, lbl_PO_number, lbl_torque_SCR_TRB;
    EditText inp_operator, inp_PO_number, inp_torque_SCR_TRB;
    List<String> labelList;
    List<String> inputType;
    TextView lbl_procInp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //validated = false;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_procedure_input, container, false);


        lbl_procInp = (TextView) view.findViewById(R.id.lbl_procInputTitle);
        lbl_procInp.setText(procedureInput.getTitle());

        lbl_operator = (TextView) view.findViewById(R.id.lbl_operator);

        lbl_PO_number = (TextView) view.findViewById(R.id.lbl_PO_number);

        lbl_torque_SCR_TRB = (TextView) view.findViewById(R.id.lbl_torque_SCR_TRB);


        inp_operator = (EditText) view.findViewById(R.id.inp_operator);

        inp_PO_number = (EditText) view.findViewById(R.id.inp_PO_number);

        inp_torque_SCR_TRB = (EditText) view.findViewById(R.id.inp_torque_SCR_TRB);

        return view;
    }

    public boolean isValid(){
        //close the keypad if active
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

            if (inp_operator.getText().toString().trim().length() == 0){
                return false;
            }


            if (inp_PO_number.getText().toString().trim().length() == 0){
                return false;
            }


            if (inp_torque_SCR_TRB.getText().toString().trim().length() == 0){
                return false;
            }

        return true;
    }



    public void setProcedureSlide(ProcedureInput procedureInput) {
        this.procedureInput = procedureInput;
    }



}