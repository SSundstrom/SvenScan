package com.example.svenscan.svenscan;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class PermissionManager {
    private int lastRequestId = 1000;
    private Activity activity;
    private Map<Integer, ISuccessHandler> handlers = new HashMap<>();

    public interface ISuccessHandler {
        void call();
    }

    public PermissionManager(Activity activity) {
        this.activity = activity;
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

        if (resultCode == RESULT_OK) {
            handler.call();
        } else if (data != null) {
            handleError(data);
        } else {
            handleDenied();
        }
    }

    private void handleError(Intent data) {
        Exception e = (Exception) data.getSerializableExtra(MaterialCamera.ERROR_EXTRA);
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
    }

}
