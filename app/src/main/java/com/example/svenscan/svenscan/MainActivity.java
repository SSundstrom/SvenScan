package com.example.svenscan.svenscan;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.commonsware.cwac.cam2.CameraActivity;
import com.commonsware.cwac.cam2.ZoomStyle;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;

import java.io.File;

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
    public void onCameraCapture(Uri uri) {
        ImageView mainView = (ImageView)findViewById((R.id.imageView));
        mainView.setImageURI(uri);
        View rootView = findViewById(android.R.id.content);
        Pix picture = ReadFile.readFile(new File(uri.getPath()));
        new OCRDecoderAsyncTask(rootView, ocr).execute(picture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.onActivityResult(requestCode, resultCode, data);
    }
    public void toCamera(View view) {
        Intent intent = new Intent(this, Camera2Activity.class);
        startActivity(intent);
    }
}
