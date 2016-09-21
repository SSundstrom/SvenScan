package com.example.svenscan.svenscan;

import android.graphics.Bitmap;
import com.googlecode.tesseract.android.TessBaseAPI;

public class OCRtranslator {

    TessBaseAPI tess;
    String text;


    public OCRtranslator(String path){
        tess = new TessBaseAPI();

        tess.init(path, "swe");
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
