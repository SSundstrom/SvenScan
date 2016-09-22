package com.example.svenscan.svenscan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialcamera.MaterialCamera;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private OCRtranslator ocr;
    private final static int CAMERA_RQ = 6969;
    private final static int CAMERA_PREMISSION = 378;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Here, thisActivity is the current activity
        // Should we show an explanation?
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_PREMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            startCamera();
        }

        startOCR();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PREMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startCamera();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    public void startCamera(){
        File saveFolder = new File(this.getApplicationContext().getFilesDir(), "MaterialCamera Sample");
        if (!saveFolder.mkdirs())
            throw new RuntimeException("Unable to create save directory, make sure WRITE_EXTERNAL_STORAGE permission is granted.");

        new MaterialCamera(this)
                /** all the previous methods can be called, but video ones would be ignored */
                .stillShot() // launches the Camera in stillshot mode
                .saveDir(saveFolder)
                .start(CAMERA_RQ);
    }

    private void startOCR() {
        System.out.println("startOCR");

        try {
            saveOCRDataFileToStorage();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }


        String langPath = getApplicationContext().getFilesDir() + "/";
        System.out.println(langPath);

        System.out.println((new File(langPath + "tessdata/")).listFiles().length);

        ocr = new OCRtranslator(langPath);
    }

    public void seeWord(View view) {
        String text = ocr.getTextIfAvailable();
        TextView textBox = ((TextView)findViewById(R.id.textView));
        if(text != null) {
            textBox.setText(text);
        } else {
            textBox.setText("Text = null");
        }
    }

    public void chooseImage(View view) {
        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        ocr.setImage(picture);
        ImageView mainView = (ImageView)findViewById((R.id.imageView));
        mainView.setImageBitmap(picture);
    }


    public void saveOCRDataFileToStorage() throws IOException {
        InputStream in = null;
        BufferedOutputStream fout = null;
        try {
            in = getResources().openRawResource(R.raw.swe);
            String path = this.getApplicationContext().getFilesDir() + "/tessdata/";
            File file = new File(path);
            file.mkdirs();

            path += "swe.traineddata";

            fout = new BufferedOutputStream(new FileOutputStream(path, true));

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }
}
