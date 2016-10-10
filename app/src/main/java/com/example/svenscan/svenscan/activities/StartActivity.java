package com.example.svenscan.svenscan.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.example.svenscan.svenscan.utils.CameraAndStoragePermission;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

public class StartActivity extends AppCompatActivity {
    private CameraAndStoragePermission permission = new CameraAndStoragePermission(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        askForPermissions();
    }

    public void onAllPermissionsApproved() {
        SharedPreferences settings = getSharedPreferences("firstTimeUser", 0);
        boolean firstTimeUser = settings.getBoolean("firstTimeUser", true);

        if(firstTimeUser) {
            Intent i = new Intent(this, HelpActivity.class);
            startActivity(i);
        }
        else {
            Intent i = new Intent(this, ScanActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            IMediaRepository mediaRepository = ((SvenScanApplication)getApplication()).getMediaRepository();
            mediaRepository.initialize(getFilesDir());
            startActivity(i);
        }
    }

    public void onPermissionDenied() {
        // handle denial. At some point. :-)
    }

    private void askForPermissions() {
        Dexter.checkPermissions(permission, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
}