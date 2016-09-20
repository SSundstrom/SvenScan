package com.example.svenscan.svenscan;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.media.ImageReader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.googlecode.tesseract.android.TessBaseAPI;

public class MainActivity extends AppCompatActivity {

    private OCRtranslator ocr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ocr = new OCRtranslator();
    }

    public void seeWord(View view) {
        if(ocr.getTextIfAvailable() != null) {
            //Show the text
        } else {
            //Say that there was no word found
        }
    }

    public void chooseImage(View view) {
        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        ocr.setImage(picture);
        ImageView mainView = (ImageView)findViewById((R.id.imageView));
        mainView.setImageBitmap(picture);
    }

}
