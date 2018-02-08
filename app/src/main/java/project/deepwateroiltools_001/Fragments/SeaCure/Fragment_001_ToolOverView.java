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
import android.widget.Toast;

import project.deepwateroiltools_001.R;
import project.dto.service.ProcedureImg;
import project.dto.service.ProcedureSlide;

/**
 * Created by janos on 06/02/2018.
 */

public class Fragment_001_ToolOverView extends Fragment implements View.OnClickListener {
    View view;
    Button btn_left;
    ProcedureImg procedureImg;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_seacure_001_tool_overview, container, false);

        btn_left  = (Button) view.findViewById(R.id.btn_left);
        btn_left.setOnClickListener(this);

        imageView = (ImageView) view.findViewById(R.id.imgView_procedure);

        procedureImg = new ProcedureImg() ;
        procedureImg.setProcId(1);
        procedureImg.setTitle("Test One");
        procedureImg.setImgLocalName("drawer4");


        return view;



    }

    @Override
    public void onClick(View v) {

        if (v == btn_left){
            Context context = imageView.getContext();
            int id = context.getResources().getIdentifier(procedureImg.getImgLocalName(), "drawable", context.getPackageName());
            imageView.setImageResource(id);
            Toast.makeText(getActivity(), "left btn Click", Toast.LENGTH_LONG).show();
            procedureImg.setImgLocalName("false_img");
        }
    }
}