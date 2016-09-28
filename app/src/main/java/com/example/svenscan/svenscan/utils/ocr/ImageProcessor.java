package com.example.svenscan.svenscan.utils.ocr;

import com.example.svenscan.svenscan.utils.Settings;
import com.googlecode.leptonica.android.Binarize;
import com.googlecode.leptonica.android.Edge;
import com.googlecode.leptonica.android.Enhance;
import com.googlecode.leptonica.android.GrayQuant;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.Scale;

public class ImageProcessor {

    public Pix getOptimizedPicture(Pix picture) {
        System.out.println("start enhance");
        picture = Scale.scaleToSize(picture, 500, 500, Scale.ScaleType.FIT);
        picture = Enhance.unsharpMasking(picture);
        if (Settings.getEdgeFilter()) {
            picture = Edge.pixSobelEdgeFilter(picture, Edge.L_ALL_EDGES);
        }
        if (Settings.isBinarize()) {
            picture = GrayQuant.pixThresholdToBinary(picture, 95);
        }
        System.out.println("end enhance");

        return picture;
    }


}
