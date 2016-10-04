package com.example.svenscan.svenscan.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example .svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.activities.tasks.OCRDecoderAsyncTask;
import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;
import com.example.svenscan.svenscan.repositories.IFavoriteRepository;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;
import com.example.svenscan.svenscan.utils.SoundManager;
import com.example.svenscan.svenscan.utils.ocr.IOCR;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;

public class ShowScannedWordActivity extends AppCompatActivity implements OCRDecoderAsyncTask.ITaskCompleteHandler{
    private IOCR ocr;
    private SoundManager soundManager;
    private IWordRepository wordManager;
    private IFavoriteRepository favoriteWords;
    private IMediaRepository mediaRepository;
    private Word currentWord;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_word);
        SvenScanApplication app = (SvenScanApplication)getApplication();
        soundManager = new SoundManager(this);
        wordManager = app.getWordRepository();
        favoriteWords = app.getFavoriteWordRepository();
        mediaRepository = app.getMediaRepository();

        ocr = app.getOCR();

        if(getIntent().hasExtra("fav")) {

            currentWordFromFavorites();

        } else {

            currentWordFromOCR();

        }

    }

    public void playWord(@Nullable View view) {
        if (currentWord != null) {
            mediaRepository.getSoundUri(currentWord.getSoundPath(), (soundUri) -> soundManager.start(soundUri));
        }
    }

    public void favoriteWord(View view) {
        if (currentWord == null) {
            return;
        }

        String word = currentWord.getWordID();

        View heart = findViewById(R.id.favorite);
        favoriteWords.toggleFavorite(word, this);

        if (favoriteWords.isFavoriteWord(word)) {
            heart.setBackgroundResource(R.drawable.fav_red);
        } else {
            heart.setBackgroundResource(R.drawable.fav_gray);
        }
    }

    @Override
    public void onOCRComplete(String ocrResult) {

        if (wordManager.containsWord(ocrResult)) {
            currentWord = wordManager.getWordFromID(ocrResult);
//            soundManager.start(currentWord.getSoundID());  // TODO: 2016-10-03 get real sound path
            handleCurrentWord();

        }

        String pretext = wordManager.containsWord(ocrResult.toUpperCase()) ? "" : "Word is not recognized: \n";
        setText(pretext + ocrResult);
    }

    private void currentWordFromFavorites() {
        String wordID = getIntent().getStringExtra("fav").toUpperCase();

        currentWord = wordManager.getWordFromID(wordID);

        Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.no_pic);

        setLeftPicture(map);

        handleCurrentWord();
    }

    private void currentWordFromOCR() {

        Bitmap map = BitmapFactory.decodeFile(getIntent().getStringExtra("picture"));
        setLeftPicture(map);

        map = Bitmap.createScaledBitmap(map, 500, 500, false);
        View rootView = findViewById(android.R.id.content);
        Pix picture = ReadFile.readBitmap(map);

        new OCRDecoderAsyncTask(rootView, ocr, this).execute(picture);

    }

    private void handleCurrentWord() {

        Button heart = (Button)findViewById(R.id.favorite);

        if (currentWord != null && favoriteWords.isFavoriteWord(currentWord.getWordID())) {
            heart.setBackgroundResource(R.drawable.fav_red);
        } else {
            heart.setBackgroundResource(R.drawable.fav_gray);
        }

        heart.setClickable(true);
        setText(currentWord.getWord());
        playWord(null);
    }

    private void setLeftPicture(Bitmap map) {

        ImageView mainView = (ImageView) findViewById((R.id.imageView4));
        mainView.setImageBitmap(map);

    }

    private void setText(String word) {
        TextView textBox = ((TextView)findViewById(R.id.wordText));

        if(word != null) {
            textBox.setText(word);
        } else {
            textBox.setText("(null)");
        }
    }
}




