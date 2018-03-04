package project.deepwateroiltools_001.Fragments.HomeScreen;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import project.deepwateroiltools.HTTP.Common;
import project.deepwateroiltools_001.R;
import project.iTextPDF.CreatePDF;

/**
 * Created by janos on 06/02/2018.
 */

public class FragmentExport extends Fragment implements View.OnClickListener {
    View view;
    Button btn_export;
    CreatePDF createPDF;
    private final int MY_PERMISSIONS_REQUEST_STORAGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_export, container, false);
        Log.d("WelcomScreenFragemtn","LOADED");
        btn_export = (Button) view.findViewById(R.id.btn_export);
        btn_export.setOnClickListener(this);


        createPDF = new CreatePDF(getContext(), "text", "test");

        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Intent callIntent = new Intent(Intent.WR);
                    //callIntent.setData(Uri.parse(Common.SUPPORT_PHONE_NUMBER));
                    //startActivity(callIntent);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    @Override
    public void onClick(View v) {

        if (v == btn_export){
            if ( (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)  || (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);

            } else {
             //   Intent callIntent = new Intent(Intent.ACTION_CALL);
              //  callIntent.setData(Uri.parse(Common.SUPPORT_PHONE_NUMBER));
                //startActivity(callIntent);
                createPDF.createandDisplayPdf();
            }

            Toast.makeText(getActivity(), "Export btn Click", Toast.LENGTH_LONG).show();
        }
    }
}