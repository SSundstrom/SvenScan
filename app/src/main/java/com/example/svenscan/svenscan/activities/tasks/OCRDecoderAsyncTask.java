package com.example.svenscan.svenscan.activities.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.repositories.FirebaseWordRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;
import com.example.svenscan.svenscan.utils.ocr.ImageProcessor;
import com.example.svenscan.svenscan.utils.ocr.OCRDecoder;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.WriteFile;

public class OCRDecoderAsyncTask extends AsyncTask<Pix, Pix, String> {
    private View rootView;
    private OCRDecoder ocr;
    private IWordRepository wordManager;

    public OCRDecoderAsyncTask(View rootView, OCRDecoder ocr, IWordRepository wordManager) {
        this.rootView = rootView;
        this.ocr = ocr;
        this.wordManager = wordManager;
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
        Button heart = (Button)rootView.findViewById(R.id.favorite);

        heart.setBackgroundResource(wordManager.get(ocrResult) != null && wordManager.get(ocrResult).isFavorite() ? R.drawable.fav_red : R.drawable.fav_gray);

        heart.setClickable(true);
    }
}