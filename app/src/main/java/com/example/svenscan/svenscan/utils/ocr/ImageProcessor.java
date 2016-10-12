package com.example.svenscan.svenscan.utils.ocr;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.googlecode.leptonica.android.Enhance;
import com.googlecode.leptonica.android.GrayQuant;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.Scale;
import com.googlecode.leptonica.android.WriteFile;

import java.io.File;
import java.io.IOException;

public class ImageProcessor {

    public Pix getOptimizedPicture(Pix picture) {
        System.out.println("start enhance");
        picture = Scale.scaleToSize(picture, 500, 500, Scale.ScaleType.FIT);
        picture = Enhance.unsharpMasking(picture);
        picture = GrayQuant.pixThresholdToBinary(picture, 95);
        System.out.println("end enhance");
        return picture;
    }

    public File scaleToReasonableSize(Uri uri, ContentResolver contentResolver, File target) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
        Pix picture = ReadFile.readBitmap(bitmap);// TODO: 2016-10-12 it becomes gray...
        picture = Scale.scaleToSize(picture, 500, 500, Scale.ScaleType.FIT);
        WriteFile.writeImpliedFormat(picture, target);
        return target;
    }
}
