package com.example.svenscan.svenscan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private OCRtranslator ocr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ocr = new OCRtranslator();
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
        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.test1);
        ocr.setImage(picture);
        ImageView mainView = (ImageView)findViewById((R.id.imageView));
        mainView.setImageBitmap(picture);
    }

}
