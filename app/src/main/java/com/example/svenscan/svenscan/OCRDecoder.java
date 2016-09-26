package com.example.svenscan.svenscan;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.leptonica.android.Enhance;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.WriteFile;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class OCRDecoder {

    TessBaseAPI tess;
    String text;


    public OCRDecoder(Application app){

        System.out.println("startOCR");

        try {
            saveOCRDataFileToStorage(app);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        String langPath = app.getApplicationContext().getFilesDir() + "/";

        tess = new TessBaseAPI();
        tess.init(langPath, "swe");
        //tess.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");  // Should make OCR only take numbers
    }

    public Bitmap getOptimizedPicture(Bitmap bitmap) {
        Pix picture = ReadFile.readBitmap(bitmap);
        Bitmap enhancedBitmap = WriteFile.writeBitmap(Enhance.unsharpMasking(picture));
        int ratio = enhancedBitmap.getHeight() / enhancedBitmap.getWidth();

        enhancedBitmap = Bitmap.createScaledBitmap(enhancedBitmap, 512, 512 * ratio, false);

        return enhancedBitmap;
    }

    public String getStringFromBitmap(Bitmap bitmap) {
        tess.setImage(bitmap);
        text = tess.getUTF8Text();
        tess.clear();

        return text;
    }

    public void saveOCRDataFileToStorage(Application app) throws IOException {
        InputStream in = null;
        BufferedOutputStream fout = null;
        try {
            in = app.getResources().openRawResource(R.raw.swe);
            String path = app.getFilesDir() + "/tessdata/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            path += "swe.traineddata";
            File file = new File(path);
            if (!file.exists()) {
                fout = new BufferedOutputStream(new FileOutputStream(path, true));

                final byte data[] = new byte[1024];
                int count;
                while ((count = in.read(data, 0, 1024)) != -1) {
                    fout.write(data, 0, count);
                }
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
