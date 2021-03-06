package com.example.svenscan.svenscan.activities.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.utils.ocr.IOCR;
import com.example.svenscan.svenscan.utils.ocr.ImageProcessor;
import com.example.svenscan.svenscan.utils.ocr.OCRDecoder;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.WriteFile;

public class OCRDecoderAsyncTask extends AsyncTask<Pix, Pix, String> {
    private View rootView;
    private IOCR ocr;
    private ITaskCompleteHandler resultHandler;


    public interface ITaskCompleteHandler {
        void onOCRComplete(String ocrResult);
    }

    public OCRDecoderAsyncTask(View rootView, IOCR ocr, ITaskCompleteHandler resultHandler) {
        this.rootView = rootView;
        this.ocr = ocr;
        this.resultHandler = resultHandler;
    }

    @Override
    protected void onPreExecute() {
        TextView textBox = ((TextView)rootView.findViewById(R.id.wordText));
        textBox.setText("Laddar");
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
        resultHandler.onOCRComplete(ocrResult);
    }
}