package project.deepwateroiltools_001.Fragments.SeaCure;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import project.deepwateroiltools_001.R;
import project.dto.service.ProcedureImg;
import project.dto.service.ProcedureSlide;

/**
 * Created by janos on 06/02/2018.
 */

public class Fragment_procedure_img extends Fragment {
    View view;
    ProcedureImg procedureImg;
    ImageView imageView;
    TextView lbl_procImg;

    public void setProcedureSlide(ProcedureImg procedureImg) {
        this.procedureImg = procedureImg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_procedure_img, container, false);
        lbl_procImg = (TextView) view.findViewById(R.id.lbl_procImg);
        lbl_procImg.setText(procedureImg.getTitle());


        imageView = (ImageView) view.findViewById(R.id.imgView_procedure);


        Log.d("fragmant created", procedureImg.getImgLocalName());
        Context context = imageView.getContext();
        int id = context.getResources().getIdentifier(procedureImg.getImgLocalName(), "drawable", context.getPackageName());
        imageView.setImageResource(id);
        return view;



    }
}