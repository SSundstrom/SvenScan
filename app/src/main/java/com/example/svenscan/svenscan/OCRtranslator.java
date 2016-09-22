package com.example.svenscan.svenscan;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.leptonica.android.Enhance;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class OCRtranslator {

    TessBaseAPI tess;
    String text;


    public OCRtranslator(Application app){

        System.out.println("startOCR");

        try {
            saveOCRDataFileToStorage(app);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        String langPath = app.getApplicationContext().getFilesDir() + "/";

        tess = new TessBaseAPI();
        tess.init(langPath, "swe");
        tess.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");  // Should make OCR only take numbers

    }

    public String getStringFromImage(Bitmap bitmap) {

        tess.setImage(bitmap);
        text = tess.getUTF8Text();
        tess.clear();
        return text;
    }

    public String getStringFromImageWithEnchance(Bitmap bitmap) {
        Pix picture = ReadFile.readBitmap(bitmap);
        picture = Enhance.unsharpMasking(picture);
        tess.setImage(picture);
        text = tess.getUTF8Text();
        tess.clear();
        return text;
    }

    public String getTextIfAvailable() {
        return text;
    }

    public void saveOCRDataFileToStorage(Application app) throws IOException {
        InputStream in = null;
        BufferedOutputStream fout = null;
        try {
            in = app.getResources().openRawResource(R.raw.swe);
            String path = app.getFilesDir() + "/tessdata/";
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
