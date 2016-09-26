package com.example.svenscan.svenscan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;
import com.example.svenscan.svenscan.services.permission.PermissionManager;

import java.io.File;

public class Camera {
    private final static int CAMERA_RQ = 2000;
    private final static String[] PERMISSIONS = {
        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Activity activity;
    private PermissionManager permission;
    private ICameraCaptureHandler handler;

    public interface ICameraCaptureHandler {
        void onCameraCapture(Uri uri);
    }

    public Camera(Activity activity, ICameraCaptureHandler handler) {
        this.activity = activity;
        this.permission = new PermissionManager(activity);
        this.handler = handler;
    }

    public void show() {
        permission.require(PERMISSIONS, () -> {
            File saveFolder = new File(activity.getApplicationContext().getFilesDir().getAbsolutePath(), "cameraTemp");

            if (!saveFolder.exists() && !saveFolder.mkdirs())
                throw new RuntimeException("Unable to create save directory, make sure WRITE_EXTERNAL_STORAGE permission is granted.");

            new MaterialCamera(activity)
                    .stillShot()
                    .saveDir(saveFolder)
                    .start(CAMERA_RQ);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Received recording or error from MaterialCamera
        if (requestCode == CAMERA_RQ) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = Uri.parse(data.getDataString());
                handler.onCameraCapture(uri);
            } else if(data != null) {
                Exception e = (Exception) data.getSerializableExtra(MaterialCamera.ERROR_EXTRA);
                e.printStackTrace();
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            permission.onActivityResult(requestCode, resultCode, data);
        }
    }

}