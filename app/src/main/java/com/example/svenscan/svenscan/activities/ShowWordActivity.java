package com.example.svenscan.svenscan.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.activities.tasks.OCRDecoderAsyncTask;
import com.example.svenscan.svenscan.utils.ProgressManager;
import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.IFavoriteRepository;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;
import com.example.svenscan.svenscan.utils.SoundManager;
import com.example.svenscan.svenscan.utils.ocr.IOCR;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;

import java.io.File;
import com.example.svenscan.svenscan.R;

public class ShowWordActivity extends AppCompatActivity implements OCRDecoderAsyncTask.ITaskCompleteHandler{
    private IOCR ocr;
    private SoundManager soundManager;
    private IWordRepository wordManager;
    private IFavoriteRepository favoriteWords;
    private IMediaRepository mediaRepository;
    private Word currentWord;
    private boolean cameFromFav;
    private ProgressManager progressManager;


    @Override
    public void onBackPressed() {
        if (cameFromFav){
            super.onBackPressed();
        } else {
            Intent intent = new Intent(this, ScanActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_word);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SvenScanApplication app = (SvenScanApplication)getApplication();
        soundManager = new SoundManager(this);
        wordManager = app.getWordRepository();
        favoriteWords = app.getFavoriteWordRepository();
        mediaRepository = app.getMediaRepository();
        progressManager = app.getPoints();

        ocr = app.getOCR();

        if(getIntent().hasExtra(getString(R.string.intent_extra_word))) {
            currentWordFromOtherSource();
            if (getIntent().hasExtra(getString(R.string.special_parent)))
                cameFromFav = true;
        } else {
            currentWordFromOCR();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void favoriteWord(View view) {
        if (currentWord == null) {
            return;
        }

        String word = currentWord.getWordID();

        favoriteWords.toggleFavorite(word, this);

        setFavoriteColor();
    }

    @Override
    public void onOCRComplete(String ocrResult) {
        if (wordManager.containsWord(ocrResult)) {
            currentWord = wordManager.getWordFromID(ocrResult);
            setOcrText(ocrResult);
            handleCurrentWord();
            progressManager.wordScanned(this);

        }
        else {
            wordNotFound(ocrResult);
        }
    }
    public void wordNotFound(String s){
        setContentView(R.layout.no_word_match_view);
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setBackgroundResource(R.drawable.redo_button);
        ImageView errorImage = (ImageView) findViewById((R.id.noWordErrorImage));
        errorImage.setBackgroundResource(R.drawable.exclamation_mark);
        TextView errorText = (TextView) findViewById(R.id.errorText);
        errorText.setText("Tyvärr hittas inte det scannade ordet, försök igen! \n"  + s);
    }

    public void backToCamera(View view){
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    private void currentWordFromOtherSource() {
        String wordID = getIntent().getStringExtra(getString(R.string.intent_extra_word)).toUpperCase();

        currentWord = wordManager.getWordFromID(wordID);

        handleCurrentWord();
    }


    private void currentWordFromOCR() {

        String imagePath = getIntent().getStringExtra("picture");
        Bitmap map = BitmapFactory.decodeFile(imagePath);


        map = Bitmap.createScaledBitmap(map, 500, 500, false);
        View rootView = findViewById(android.R.id.content);
        Pix picture = ReadFile.readBitmap(map);


        File image = new File(getIntent().getStringExtra("picture"));   // TODO: 2016-10-04 this should maybe be somewhere else
        Log.d("image deleted : ", image.delete() ? "success" : "fail"); // TODO: 2016-10-04 this as well

        new OCRDecoderAsyncTask(rootView, ocr, this).execute(picture);

    }

    private void handleCurrentWord() {
        setFavoriteColor();
        setMainPicture();
        setWordText(currentWord.getWord());
        playWord(null);
    }

    private void setFavoriteColor() {

        ImageButton heart = (ImageButton)findViewById(R.id.favorite);

        if (currentWord != null && favoriteWords.isFavoriteWord(currentWord.getWordID())) {
            heart.setBackgroundResource(R.drawable.fav_icon_24dp);
        } else {
            heart.setBackgroundResource(R.drawable.ic_not_fav_24dp);
        }

        heart.setClickable(true);
    }

    private void setMainPicture() {
        showLoadingAnimation(true);
        ImageView mainView = (ImageView) findViewById((R.id.imageView4));
        mediaRepository.getImageUri(currentWord.getImagePath(), (uri) -> {
            mainView.setImageURI(uri);
            showLoadingAnimation(false);
        });
    }

    private void showLoadingAnimation(boolean value) {

        findViewById(R.id.show_word_image_loading).setVisibility(value ? View.VISIBLE : View.INVISIBLE);

    }

    public void playWord(@Nullable View view) {
        if (currentWord != null) {
            mediaRepository.getSoundUri(currentWord.getSoundPath(), (uri) -> soundManager.start(uri, view));
            startSoundAnimation();
        }
    }
    private void startSoundAnimation() {
        System.out.println("Start animation");
        View playWord = findViewById(R.id.playWord);
        playWord.setBackgroundResource(R.drawable.sound_playing);
        AnimationDrawable animation = (AnimationDrawable)playWord.getBackground();
        animation.start();
    }

    private void setWordText(String word) {
        TextView textBox = ((TextView)findViewById(R.id.wordText));
        setText(textBox, word);

    }
    private void setOcrText(String ocr){
        TextView ocrText = (TextView)findViewById(R.id.ocrText);
        ocrText.setVisibility(View.VISIBLE);
        setText(ocrText, ocr);
    }
    private void setText(TextView textBox, String s){
        if(s != null) {
            textBox.setText(s);
        } else {
            textBox.setText("(null)");
        }
    }
}




