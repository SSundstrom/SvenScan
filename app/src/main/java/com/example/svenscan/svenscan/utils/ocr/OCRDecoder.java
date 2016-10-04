package com.example.svenscan.svenscan.utils.ocr;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.example.svenscan.svenscan.R;
import com.googlecode.leptonica.android.Binarize;
import com.googlecode.leptonica.android.Enhance;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.Rotate;
import com.googlecode.leptonica.android.Scale;
import com.googlecode.leptonica.android.WriteFile;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class OCRDecoder implements IOCR {

    private TessBaseAPI tess;
    private String text;
    private String langPath;


    public OCRDecoder(Application app){

        System.out.println("startOCR");

        initiateOCR(app);
        //tess.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");  // Should make OCR only take numbers
    }

    public String getStringFromPix(Pix picture) {
        tess.clear();
        tess.setImage(picture);
        text = tess.getUTF8Text();
        tess.clear();
        text = text.trim(); // TODO: 2016-10-03 Trim is not working
        return text;
    }

    private void initiateOCR(Application app) {
        try {
            saveOCRDataFileToStorage(app);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        langPath = app.getApplicationContext().getFilesDir() + "/";

        tess = new TessBaseAPI();
        tess.init(langPath, "swe");
    }

    private void saveOCRDataFileToStorage(Application app) throws IOException {
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

    public String getText() {
        return text.trim().toUpperCase();
    }
}
