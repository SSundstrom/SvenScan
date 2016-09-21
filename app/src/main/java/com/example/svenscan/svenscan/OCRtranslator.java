package com.example.svenscan.svenscan;

import android.graphics.Bitmap;
import com.googlecode.tesseract.android.TessBaseAPI;

public class OCRtranslator {

    TessBaseAPI tess;
    String text;


    public OCRtranslator(){
        tess = new TessBaseAPI();
        //tess.init()
    }

    public void setImage(Bitmap bitmap) {
        tess.setImage(bitmap);
        text = tess.getUTF8Text();
        tess.end();
    }

    public String getTextIfAvailable() {
        return text;
    }
}
