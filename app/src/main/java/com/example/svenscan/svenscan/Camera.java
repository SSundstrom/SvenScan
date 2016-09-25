package com.example.svenscan.svenscan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;

import java.io.File;

public class Camera {
    private final static int CAMERA_RQ = 2000;
    private Activity activity;
    private PermissionManager permission;

    public Camera(Activity activity) {
        this.activity = activity;
        this.permission = new PermissionManager(activity);
    }

    public void start() {
        permission.require(Manifest.permission.CAMERA, new PermissionManager.ISuccessHandler() {
            @Override
            public void call() {
                permission.require(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionManager.ISuccessHandler() {
                    @Override
                    public void call() {
                        File saveFolder = new File(activity.getApplicationContext().getFilesDir().getAbsolutePath(), "MaterialCamera Sample");

                        if (!saveFolder.exists() && !saveFolder.mkdirs())
                            throw new RuntimeException("Unable to create save directory, make sure WRITE_EXTERNAL_STORAGE permission is granted.");

                        new MaterialCamera(activity)
                                .stillShot()
                                .saveDir(saveFolder)
                                .start(CAMERA_RQ);
                    }
                });
            }
        });
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Received recording or error from MaterialCamera
        if (requestCode == CAMERA_RQ) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(activity, "Saved to: " + data.getDataString(), Toast.LENGTH_LONG).show();
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