package project.deepwateroiltools_001.Fragments.SeaCure;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.deepwateroiltools_001.R;
import project.dto.service.ProcedureChecklist;
import project.dto.service.ProcedureSlide;

/**
 * Created by janos on 24/02/2018.
 */

public class Fragment_procedure_checklist extends Fragment implements  View.OnClickListener {
    View view;
    ProcedureChecklist procedureSlide;
    TextView lbl_procImg;
    List<String> listItems;
    LinearLayout linearLayout;


    public ProcedureSlide getProcedureSlide() {
        return procedureSlide;
    }

    public void setProcedureSlide(ProcedureChecklist procedureSlide) {
        this.procedureSlide = procedureSlide;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_procedure_checklist, container, false);

        listItems = procedureSlide.getListItems();

        lbl_procImg = (TextView) view.findViewById(R.id.lbl_procImg);
        lbl_procImg.setText(procedureSlide.getTitle());

        Log.d("fragmant created", "gen");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutChecklist);

        for (int i=0; i< listItems.size(); i++) {

            CheckBox ch = new CheckBox(getContext());
            ch.setText(listItems.get(i));
            ch.setId(i);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(30, 20, 30, 0);
            linearLayout.addView(ch, layoutParams);
        }

   }

   public void verifyCheckboxes(){
       boolean checkedState[] = new boolean[listItems.size()];

       for(int i=0; i <= listItems.size()-1; i++)
       {
           CheckBox c = (CheckBox)getView().findViewById(i);
           checkedState[i] = c.isChecked();
       }
   }

    @Override
    public void onClick(View v) {

    }
}