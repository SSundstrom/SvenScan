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
import java.io.FileOutputStream;
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
        bitmap = getResizedBitmap(bitmap, 500, 500);
        saveBitmapToFile(bitmap, target);
        return target;
    }

    private void saveBitmapToFile(Bitmap bmp, File file) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getResizedBitmap(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }
}
