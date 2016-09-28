package com.example.svenscan.svenscan.utils.ocr;

import com.googlecode.leptonica.android.Enhance;
import com.googlecode.leptonica.android.GrayQuant;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.Scale;

public class ImageProcessor {

    public Pix getOptimizedPicture(Pix picture) {
        System.out.println("start enhance");
        picture = Scale.scaleToSize(picture, 500, 500, Scale.ScaleType.FIT);
        picture = Enhance.unsharpMasking(picture);
        picture = GrayQuant.pixThresholdToBinary(picture, 95);
        System.out.println("end enhance");
        return picture;
    }


}
