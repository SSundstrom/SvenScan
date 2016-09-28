package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.activities.tasks.OCRDecoderAsyncTask;
import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;
import com.example.svenscan.svenscan.utils.SoundManager;
import com.example.svenscan.svenscan.utils.ocr.OCRDecoder;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;

public class ShowScannedWordActivity extends AppCompatActivity implements OCRDecoderAsyncTask.ITaskCompleteHandler{
    private OCRDecoder ocr;
    private SoundManager soundManager;
    private IWordRepository wordManager;
    private FavoriteWordRepository favoriteWords = new FavoriteWordRepository();
    private Word currentWord;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ocr = new OCRDecoder(getApplication()); // todo: denna bör nog köras i async-tasken... den är långsam

        SvenScanApplication app = (SvenScanApplication) getApplication();
        wordManager = app.getWordRepository();

        soundManager = new SoundManager(this);
        setContentView(R.layout.activity_show_word);
        Intent intent = getIntent();

        Bitmap map = BitmapFactory.decodeFile(intent.getStringExtra("picture"));
        ImageView mainView = (ImageView)findViewById((R.id.imageView4));
        map = Bitmap.createScaledBitmap(map, 500, 500, false);
        mainView.setImageBitmap(map);
        View rootView = findViewById(android.R.id.content);
        Pix picture = ReadFile.readBitmap(map);
        new OCRDecoderAsyncTask(rootView, ocr, this).execute(picture);

    }

    public void playWord(View view) {
        if (currentWord != null) {
            soundManager.start(currentWord.getSoundID());
        }
    }

    public void favoriteWord(View view) {
        //TODO should be Label later on and not EditText
        if (ocr.getText() == null || !wordManager.containsWord(ocr.getText())) {
            return;
        }
        String word = ocr.getText();
        View heart = findViewById(R.id.favorite);
        if (wordManager.toggleFavorite(word)) {
            heart.setBackgroundResource(R.drawable.fav_red);
            favoriteWords.addFavorite(wordManager.getWordFromID(word));
        } else {
            heart.setBackgroundResource(R.drawable.fav_gray);
            favoriteWords.removeFavorite(wordManager.getWordFromID(word));
        }
    }

    @Override
    public void onOCRComplete(String ocrResult) {
        Button heart = (Button)findViewById(R.id.favorite);
        heart.setBackgroundResource(wordManager.getWordFromID(ocrResult) != null && wordManager.getWordFromID(ocrResult).isFavorite() ? R.drawable.fav_red : R.drawable.fav_gray);
        heart.setClickable(true);
        if (wordManager.containsWord(ocrResult)) {
            currentWord = wordManager.getWordFromID(ocrResult);
            soundManager.start(currentWord.getSoundID());

        }
    }
}




