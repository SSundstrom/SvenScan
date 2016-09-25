package com.example.svenscan.svenscan;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class OCRDecoderAsyncTask extends AsyncTask<Bitmap, Bitmap, String> {
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
    protected void onProgressUpdate(Bitmap... params) {
        ImageView mainView = (ImageView)rootView.findViewById((R.id.imageViewOptimzed));
        mainView.setImageBitmap(params[0]);
    }

    @Override
    protected String doInBackground(Bitmap... params) {
        Bitmap optimizedPicture = ocr.getOptimizedPicture(params[0]);
        publishProgress(optimizedPicture);
        return ocr.getStringFromBitmap(optimizedPicture);
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