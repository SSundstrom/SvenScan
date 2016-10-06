package com.example.svenscan.svenscan.fragments;

import com.desmond.squarecamera.CameraFragment;
import com.example.svenscan.svenscan.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ScanFragment extends CameraFragment {

    public static final String TAG = ScanFragment.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scan, container, false);
    }

    public static Fragment newInstance() {
        return new ScanFragment();
    }
}
