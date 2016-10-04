package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;
import com.example.svenscan.svenscan.R;

import com.example.svenscan.svenscan.utils.Camera;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Camera.ICameraCaptureHandler {
    private Camera camera;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        camera = new Camera(this, this);
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);


    }

    public void chooseImage(View view) {
        camera.show();
    }

    @Override
    public void onCameraCapture(String imagePath) {
        Intent intent = new Intent(this, ShowScannedWordActivity.class);
        intent.putExtra("picture", imagePath);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.onActivityResult(requestCode, resultCode, data);
    }
}

