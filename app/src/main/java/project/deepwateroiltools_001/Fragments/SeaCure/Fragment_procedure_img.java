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

import java.util.List;

import project.deepwateroiltools_001.R;
import project.dto.service.ProcedureImg;
import project.dto.service.ProcedureSlide;

/**
 * Created by janos on 06/02/2018.
 */

public class Fragment_procedure_img extends Fragment implements View.OnClickListener {
    View view;
    Button btn_left;
    ProcedureImg procedureImg;
    ImageView imageView;
    List<ProcedureSlide> procedureSlideList;

    public ProcedureImg getProcedureImg() {
        return procedureImg;
    }

    public void setProcedureImg(ProcedureImg procedureImg) {
        this.procedureImg = procedureImg;
    }

    public List<ProcedureSlide> getProcedureSlideList() {
        return procedureSlideList;
    }

    public void setProcedureSlideList(List<ProcedureSlide> procedureSlideList) {
        this.procedureSlideList = procedureSlideList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_procedure_img, container, false);

        btn_left  = (Button) view.findViewById(R.id.btn_left);
        btn_left.setOnClickListener(this);

        imageView = (ImageView) view.findViewById(R.id.imgView_procedure);


      //  List<ProcedureSlide> procedureSlideList = ((SeaCure) getActivity()).getProcedureList();
//        try {
//            procedureImg = (ProcedureImg)procedureSlideList.get(0);
//        }
//        catch (Exception e){
//            Log.d("exception", e.toString());
//        }
        Log.d("fragmant created", procedureImg.getImgLocalName());
        Context context = imageView.getContext();
        int id = context.getResources().getIdentifier(procedureImg.getImgLocalName(), "drawable", context.getPackageName());
        imageView.setImageResource(id);
        return view;



    }

    @Override
    public void onClick(View v) {

        if (v == btn_left){
            Context context = imageView.getContext();
            int id = context.getResources().getIdentifier(procedureImg.getImgLocalName(), "drawable", context.getPackageName());
            imageView.setImageResource(id);
            Toast.makeText(getActivity(), procedureImg.getImgURL(), Toast.LENGTH_LONG).show();
            procedureImg.setImgLocalName("false_img");
        }
    }
}