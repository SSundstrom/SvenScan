package com.example.svenscan.svenscan;

import android.graphics.Bitmap;

import com.googlecode.leptonica.android.Enhance;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.tesseract.android.TessBaseAPI;


public class OCRtranslator {

    TessBaseAPI tess;
    String text;


    public OCRtranslator(String path){
        tess = new TessBaseAPI();
        tess.init(path, "swe");
        tess.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");  // Should make OCR only take numbers
    }

    public void setImage(Bitmap bitmap) {
        Pix picture = ReadFile.readBitmap(bitmap);
        picture = Enhance.unsharpMasking(picture);
        tess.setImage(picture);
        text = tess.getUTF8Text();
        tess.clear();
    }

    public String getTextIfAvailable() {
        return text;
    }
}
