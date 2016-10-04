package com.example.svenscan.svenscan.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.svenscan.svenscan.activities.ScanActivity;


public class Camera {
    private final static int CAMERA_RQ = 2000;
    private Activity activity;
    private ICameraCaptureHandler handler;

    public interface ICameraCaptureHandler {
        void onCameraCapture(String imagePath);
    }

    public Camera(Activity activity, ICameraCaptureHandler handler) {
        this.activity = activity;
        this.handler = handler;
    }

    public void show() {
        Intent intent = new Intent(activity, ScanActivity.class);
        activity.startActivityForResult(intent, CAMERA_RQ);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_RQ) {
            if (resultCode == Activity.RESULT_OK) {
                String imagePath = data.getData().getPath();
                handler.onCameraCapture(imagePath);
            } else if(data != null) {
                Exception e = new Exception("Welp, this didn't work");
                e.printStackTrace();
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}