package com.example.svenscan.svenscan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class Camera {

    private Activity main;
    private final static int CAMERA_RQ = 6969;
    private final static int CAMERA_PREMISSION = 378;
    private final static int STORAGE_PREMISSION = 432;
    private int permissionsGranted;

    public Camera(Activity main) {
        this.main = main;
        permissionsGranted = 0;
        requestCamera();
        requestExternalStorage();
    }

    public void requestCamera() {
        // Here, thisActivity is the current activity
        // Should we show an explanation?
        if (ContextCompat.checkSelfPermission(main,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(main,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(main,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_PREMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    public void requestExternalStorage() {
        // Here, thisActivity is the current activity
        // Should we show an explanation?
        System.out.println("External requested");
        if (ContextCompat.checkSelfPermission(main,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(main,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(main,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PREMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }


    public void startCamera(){
        File saveFolder = new File(main.getApplicationContext().getFilesDir().getAbsolutePath(), "MaterialCamera Sample");
        if (!saveFolder.mkdirs())
            throw new RuntimeException("Unable to create save directory, make sure WRITE_EXTERNAL_STORAGE permission is granted.");

        new MaterialCamera(main)
                /** all the previous methods can be called, but video ones would be ignored */
                .stillShot() // launches the Camera in stillshot mode
                .saveDir(saveFolder)
                .start(CAMERA_RQ);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Received recording or error from MaterialCamera
        if (requestCode == CAMERA_RQ) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(main, "Saved to: " + data.getDataString(), Toast.LENGTH_LONG).show();
            } else if(data != null) {
                Exception e = (Exception) data.getSerializableExtra(MaterialCamera.ERROR_EXTRA);
                e.printStackTrace();
                Toast.makeText(main, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
