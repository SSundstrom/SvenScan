package com.example.svenscan.svenscan.utils.ocr;

import com.googlecode.leptonica.android.Pix;

public interface IOCR {
    String getStringFromPix(Pix picture);
    String getText();
}
