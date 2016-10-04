package com.example.svenscan.svenscan.utils;

import com.example.svenscan.svenscan.activities.StartActivity;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class CameraAndStoragePermission implements MultiplePermissionsListener {

    private final StartActivity activity;

    public CameraAndStoragePermission(StartActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if (report.getDeniedPermissionResponses().size() == 0) {
            activity.onAllPermissionsApproved();
        } else {
            activity.onPermissionDenied();
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

    }
}