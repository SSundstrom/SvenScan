package com.example.svenscan.svenscan.activities;

import com.desmond.squarecamera.SquareCameraPreview;
import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.fragments.ScanFragment;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ScanActivity extends com.desmond.squarecamera.CameraActivity {

    public ScanActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, ScanFragment.newInstance(), ScanFragment.TAG)
                    .commit();
        }

    }
}
