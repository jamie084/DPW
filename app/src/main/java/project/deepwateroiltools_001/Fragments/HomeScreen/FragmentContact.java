package project.deepwateroiltools_001.Fragments.HomeScreen;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;


import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;


import project.deepwateroiltools.HTTP.Common;
import project.helpers.PermissionManager;
import project.deepwateroiltools.HTTP.ProcessListener;
import project.deepwateroiltools_001.HomeScreen;
import project.deepwateroiltools_001.R;
import project.dto.user.User;
import project.email.EmailClient;

/**
 * Created by janos on 06/02/2018.
 */

public class FragmentContact extends Fragment implements View.OnClickListener {
    View view;
    Button btn_contact;
    Button btn_call;
    User user;
    EditText inp_contact;
    TextView lbl_info;
    PermissionManager permissionManager;
    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact, container, false);

        btn_contact  = (Button) view.findViewById(R.id.btn_contact);
        btn_contact.setOnClickListener(this);

        btn_call = (Button) view.findViewById(R.id.btn_call);
        btn_call.setOnClickListener(this);
        btn_call.setText(Common.SUPPORT_PHONE_NUMBER);

        inp_contact = (EditText) view.findViewById(R.id.inp_contact);

        lbl_info = (TextView) view.findViewById(R.id.lbl_info);

        final HomeScreen homeScreenActivity = (HomeScreen)getActivity();
        user = homeScreenActivity.getUser();

        permissionManager = new PermissionManager(getActivity());

        return view;

    }



    @Override
    public void onClick(View v) {

        if (v == btn_contact){

            ArrayList<String> toList = new ArrayList<String>();
            //TODO add this line at live version
            //toList.add(user.getUser());
            EmailClient email = new EmailClient(getActivity(), "Support request", toList, inp_contact.getText().toString(), true);
            email.setProcessListener(new ProcessListener() {
                @Override
                public void ProcessingIsDone(String result) {
                        lbl_info.setText(result);
                }
            });
            try {
                email.execute();
            }
            catch (Exception e){
                Log.d("Email exception", e.toString());
            }
        }
        else if (v == btn_call){
            if (permissionManager.CallPhone()){
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(Common.SUPPORT_PHONE_NUMBER));
                startActivity(callIntent);
            }
            else{
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(Common.SUPPORT_PHONE_NUMBER));
                    startActivity(callIntent);

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
}