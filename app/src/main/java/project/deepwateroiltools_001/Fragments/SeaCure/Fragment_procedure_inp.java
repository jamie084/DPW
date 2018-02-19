package project.deepwateroiltools_001.Fragments.SeaCure;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import java.util.List;

import project.Drawer.SeaCureMenuDrawer;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentAdminArea;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentContact;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentHistory;
import project.deepwateroiltools_001.Fragments.HomeScreen.FragmentSettings;
import project.deepwateroiltools_001.R;
import project.deepwateroiltools_001.SeaCure;
import project.dto.service.ProcedureInput;
import project.dto.service.ProcedureSlide;

import static android.content.Context.INPUT_METHOD_SERVICE;

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
    TextView lbl_procInp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //validated = false;
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

    public boolean isValid(){
        //close the keypad if active
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        this.labelList = procedureInput.getLabelList();

        if (this.labelList.size() >= 1){
            if (inpField_1.getText().toString().trim().length() == 0){
                return false;
            }
        }
        if (this.labelList.size() >=2 ){
            if (inpField_2.getText().toString().trim().length() == 0){
                return false;
            }
        }
        if (this.labelList.size() >=3){
            if (inpField_3.getText().toString().trim().length() == 0){
                return false;
            }
        }
        if (this.labelList.size() >=4){
            if (inpField_4.getText().toString().trim().length() == 0){
                return false;
        }
        }
        return true;
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