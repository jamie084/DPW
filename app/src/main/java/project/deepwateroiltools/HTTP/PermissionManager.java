package project.deepwateroiltools.HTTP;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

/**
 * Created by janos on 04/03/2018.
 */

public class PermissionManager {
    private final int MY_PERMISSIONS_REQUEST_STORAGE = 1;
    Activity activity;

    public PermissionManager(Activity activity){
        this.activity = activity;
    }
    public boolean WriteAndReadExternal(){
        if ( (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)  || (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_STORAGE);
            activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_STORAGE);
            return false;

        } else {
            //   Intent callIntent = new Intent(Intent.ACTION_CALL);
            //  callIntent.setData(Uri.parse(Common.SUPPORT_PHONE_NUMBER));
            //startActivity(callIntent);
            return true; //createPDF.createandDisplayPdf();
        }
    }
}
