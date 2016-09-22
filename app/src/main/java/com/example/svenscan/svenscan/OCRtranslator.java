package com.example.svenscan.svenscan;

import android.graphics.Bitmap;

import com.googlecode.leptonica.android.Enhance;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.tesseract.android.TessBaseAPI;


public class OCRtranslator {

    TessBaseAPI tess;
    String text;


    public OCRtranslator(){
        tess = new TessBaseAPI();
//        tess.init("/", null);
//        tess.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");  Should make OCR only take numbers
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

    /**
    private void initializeTessApi() {

        File data = new File(app.getDataDir(), TESSDATA);
        File traineddataFile = new File(data, LANG + ".traineddata");
        if (!traineddataFile.exists()) {
            try {
                data.mkdirs();
                copyAndClose(open(TESSDATA + "/" + LANG + ".traineddata"), new FileOutputStream(traineddataFile));
                info("Copied", LANG, "traineddata");
            } catch (IOException e) {
                error(e, "Was unable to copy", LANG, "traineddata ");
            }
        }
        tess.init(application().dataDir().getAbsolutePath(), LANG);
    }
     */
}
