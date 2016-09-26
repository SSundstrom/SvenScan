package com.example.svenscan.svenscan;

import android.app.Application;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.WriteFile;

class OCRDecoderAsyncTask extends AsyncTask<Pix, Pix, String> {
    private View rootView;
    private OCRDecoder ocr;

    OCRDecoderAsyncTask(View rootView, OCRDecoder ocr) {
        this.rootView = rootView;
        this.ocr = ocr;
    }

    @Override
    protected void onPreExecute() {
        TextView textBox = ((TextView)rootView.findViewById(R.id.textView));
        textBox.setText("Processing OCR...");
    }

    @Override
    protected void onProgressUpdate(Pix... params) {
        ImageView mainView = (ImageView)rootView.findViewById((R.id.imageViewOptimzed));
        mainView.setImageBitmap(WriteFile.writeBitmap(params[0]));
    }

    @Override
    protected String doInBackground(Pix... params) {

        ImageProcessor leptonica = new ImageProcessor();
        Pix optimizedPicture = leptonica.getOptimizedPicture(params[0]);
        publishProgress(optimizedPicture);
        return ocr.getStringFromPix(optimizedPicture);
    }

    @Override
    protected void onPostExecute(String ocrResult) {
        TextView textBox = ((TextView)rootView.findViewById(R.id.textView));
        if(ocrResult != null) {
            textBox.setText(ocrResult);
        } else {
            textBox.setText("(null)");
        }
    }
}