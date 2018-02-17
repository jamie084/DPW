package project.deepwateroiltools_001.Fragments.SeaCure;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import project.deepwateroiltools_001.R;
import project.dto.service.ProcedureInput;
import project.dto.service.ProcedureSlide;

/**
 * Created by janos on 06/02/2018.
 */

public class Fragment_procedure_inp extends Fragment implements View.OnClickListener {
    View view;
    Button btn_left;
    ProcedureInput procedureInput;
    TextView lbl_procInput1, lbl_procInput2, lbl_procInput3, lbl_procInput4;
    EditText inpField_1, inpField_2, inpField_3, inpField_4;
    List<String> labelList;
    List<String> inputType;
    List<ProcedureSlide> procedureSlideList;
    TextView lbl_procInp;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_procedure_input, container, false);

        btn_left  = (Button) view.findViewById(R.id.btn_left);
        btn_left.setOnClickListener(this);

        lbl_procInp = (TextView) view.findViewById(R.id.lbl_procInputTitle);
        lbl_procInp.setText(procedureInput.getTitle());

        lbl_procInput1 = (TextView) view.findViewById(R.id.lbl_procInput1);
        lbl_procInput1.setVisibility(View.INVISIBLE);
        lbl_procInput2 = (TextView) view.findViewById(R.id.lbl_procInput2);
        lbl_procInput2.setVisibility(View.INVISIBLE);
        lbl_procInput3 = (TextView) view.findViewById(R.id.lbl_procInput3);
        lbl_procInput3.setVisibility(View.INVISIBLE);
        lbl_procInput4 = (TextView) view.findViewById(R.id.lbl_procInput4);
        lbl_procInput4.setVisibility(View.INVISIBLE);

        inpField_1 = (EditText) view.findViewById(R.id.inpField_1);
        inpField_1.setVisibility(View.INVISIBLE);
        inpField_2 = (EditText) view.findViewById(R.id.inpField_2);
        inpField_2.setVisibility(View.INVISIBLE);
        inpField_3 = (EditText) view.findViewById(R.id.inpField_3);
        inpField_3.setVisibility(View.INVISIBLE);
        inpField_4 = (EditText) view.findViewById(R.id.inpField_4);
        inpField_4.setVisibility(View.INVISIBLE);

        this.labelList = procedureInput.getLabelList();


        if (labelList.size() >= 1){
            lbl_procInput1.setVisibility(View.VISIBLE);
            inpField_1.setVisibility(View.VISIBLE);
            lbl_procInput1.setText(labelList.get(0));
            lbl_procInput1.setGravity(Gravity.CENTER);
        }
        if (labelList.size() >=2 ){
            lbl_procInput2.setVisibility(View.VISIBLE);
            inpField_2.setVisibility(View.VISIBLE);
            lbl_procInput2.setText(labelList.get(1));
            lbl_procInput2.setGravity(Gravity.CENTER);
        }
        if (labelList.size() >=3){
            lbl_procInput3.setVisibility(View.VISIBLE);
            inpField_3.setVisibility(View.VISIBLE);
            lbl_procInput3.setText(labelList.get(2));
            lbl_procInput3.setGravity(Gravity.CENTER);
        }
        if (labelList.size() >=4){
            lbl_procInput4.setVisibility(View.VISIBLE);
            inpField_4.setVisibility(View.VISIBLE);
            lbl_procInput4.setText(labelList.get(3));
            lbl_procInput4.setGravity(Gravity.CENTER);
        }



        return view;
    }

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public List<String> getInputType() {
        return inputType;
    }

    public void setInputType(List<String> inputType) {
        this.inputType = inputType;
    }



    public ProcedureInput getProcedureImg() {
        return procedureInput;
    }

    public void setProcedureSlide(ProcedureInput procedureInput) {
        this.procedureInput = procedureInput;
    }

    public List<ProcedureSlide> getProcedureSlideList() {
        return procedureSlideList;
    }

    public void setProcedureSlideList(List<ProcedureSlide> procedureSlideList) {
        this.procedureSlideList = procedureSlideList;
    }

    @Override
    public void onClick(View v) {

        if (v == btn_left){
//            Context context = imageView.getContext();
//            int id = context.getResources().getIdentifier(procedureImg.getImgLocalName(), "drawable", context.getPackageName());
//            imageView.setImageResource(id);
//            Toast.makeText(getActivity(), procedureImg.getImgURL(), Toast.LENGTH_LONG).show();
//            procedureImg.setImgLocalName("false_img");
        }
    }
}