package com.example.svenscan.svenscan.utils.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.svenscan.svenscan.Manifest;
import com.example.svenscan.svenscan.activities.MainActivity;
import com.example.svenscan.svenscan.activities.ScanActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.EmptyMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Easy-to-use wrapper class for requesting permissions
 * during use, as required by Android 6.0s.
 */
public class PermissionManager implements MultiplePermissionsListener{



    private MainActivity activity;

    private MultiplePermissionsListener permissions = new EmptyMultiplePermissionsListener();
    private PermissionToken token;

    public PermissionManager(MainActivity activity){
        this.activity = activity;
    }


        @Override
        public void onPermissionsChecked(MultiplePermissionsReport report) {

            for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
                activity.showPermissionGranted(response.getPermissionName());
            }

           /* for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
                activity.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
            }*/


        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {


            activity.showPermissionRationale(token);

        }
    }





  /*  private int lastRequestId = 1000;
    private Activity activity;
    private Map<Integer, ISuccessHandler> handlers = new HashMap<>();

    public PermissionManager(Activity activity) {
        this.activity = activity;
    }

    public void require(String[] permissions, ISuccessHandler onSuccess) {
        ISuccessHandler aggregator = new PermissionAggregator(permissions.length, onSuccess);

        for (String permission : permissions) {
            require(permission, aggregator);
        }
    }

    public void require(String permission, ISuccessHandler onSuccess) {
        if (isApproved(permission)) {
            onSuccess.call();
        } else {
            requestPermission(permission, onSuccess);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ISuccessHandler handler = handlers.get(requestCode);

        if (handler == null)
            return;

        if (resultCode == Activity.RESULT_OK) {
            handler.call();
        } else if (data != null) {
            handleError(data);
        } else {
            handleDenied();
        }
    }

    private void handleError(Intent data) {
        Exception e = new Exception("yolo");
        e.printStackTrace();
        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void handleDenied() {
        Toast.makeText(activity, "Did you decline the permission? todo: ask for permission again", Toast.LENGTH_LONG).show();
    }

    private void requestPermission(String permission, ISuccessHandler onSuccess) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // The user previously denied this permission. We ask again.
            require(permission, onSuccess);
        } else {
            int requestId = getNextRequestId();
            handlers.put(requestId , onSuccess);
            ActivityCompat.requestPermissions(activity, new String[] { permission }, requestId );
        }
    }

    private int getNextRequestId() {
        return lastRequestId++;
    }

    private boolean isApproved(String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }*/
