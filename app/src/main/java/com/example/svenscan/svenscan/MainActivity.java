package com.example.svenscan.svenscan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;


public class MainActivity extends AppCompatActivity implements Camera.ICameraCaptureHandler {
    private OCRDecoder ocr;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = new Camera(this, this);
        ocr = new OCRDecoder(getApplication());
    }

    public void chooseImage(View view) {
        camera.show();
    }

    @Override
    public void onCameraCapture(Bitmap map) {
        ImageView mainView = (ImageView)findViewById((R.id.imageView));
        map = Bitmap.createScaledBitmap(map, 500, 500, false);
        mainView.setImageBitmap(map);
        View rootView = findViewById(android.R.id.content);
        Pix picture = ReadFile.readBitmap(map);
        new OCRDecoderAsyncTask(rootView, ocr).execute(picture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.onActivityResult(requestCode, resultCode, data);
    }
}
