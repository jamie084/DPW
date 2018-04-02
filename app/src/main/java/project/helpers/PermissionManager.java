package project.helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.PermissionChecker;

/**
 * Created by janos on 04/03/2018.
 *
 * Class designed to handle the permission request and ask the user for permissions
 */

public class PermissionManager extends Activity {
    private final int MY_PERMISSIONS_REQUEST = 1;
    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    Activity activity;

    public PermissionManager(Activity activity){
        this.activity = activity;
    }

    //Permission to read & write to external storage (ie SD card)
    public boolean WriteAndReadExternal(){
        if ( (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)  || (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
            activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
            return false;

        } else {
            return true;
        }
    }

    //Permission request to let the application start calls
    public boolean CallPhone(){
        if (activity.checkSelfPermission(Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {

            return false;
    }
    else{
            return true;
        }
    }

    //Permission request to let the application start calls
    public boolean Camera(){
        if (activity.checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            return false;
        }
        else{
            return true;
        }
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


}
