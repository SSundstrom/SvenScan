package com.example.svenscan.svenscan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;
import com.desmond.squarecamera.CameraActivity;
import com.commonsware.cwac.cam2.Facing;
import com.commonsware.cwac.cam2.OrientationLockMode;
import com.commonsware.cwac.cam2.ZoomStyle;
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
        void onCameraCapture(Bitmap map);
    }

    public Camera(Activity activity, ICameraCaptureHandler handler) {
        this.activity = activity;
        this.permission = new PermissionManager(activity);
        this.handler = handler;
    }

    public void show() {
        permission.require(PERMISSIONS, () -> {
/**
            File saveFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "SvenScan");

            if (!saveFolder.exists() && !saveFolder.mkdirs())
                throw new RuntimeException("Unable to create save directory, make sure WRITE_EXTERNAL_STORAGE permission is granted.");
            File image = new File(saveFolder.getPath(), "tempImage.jpeg");

            if (image.exists() && !image.delete()) {
                throw new RuntimeException("Unable to delete tmp imagefile");
            }
            Uri uri = Uri.fromFile(image);
*/
            Intent intent2 = new Intent(activity, CameraActivity.class);
            activity.startActivityForResult(intent2, CAMERA_RQ);

        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_RQ) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap map = BitmapFactory.decodeFile(data.getData().getPath());
                handler.onCameraCapture(map);
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