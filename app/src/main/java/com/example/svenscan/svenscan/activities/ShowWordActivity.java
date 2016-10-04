package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.HashSet;
import java.util.Set;

public class ShowWordActivity extends AppCompatActivity {
    private SoundManager soundManager;
    private IWordRepository wordManager;
    private FavoriteWordRepository favoriteWords;
    private Word currentWord;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SvenScanApplication app = (SvenScanApplication) getApplication();
        wordManager = app.getWordRepository();
        favoriteWords = app.getFavoriteWordRepository();

        soundManager = new SoundManager(this);
        setContentView(R.layout.activity_show_word);

        //TODO: get right intent.. How I do not know yet
        Intent intent = getIntent();
        String word = (String) intent.getSerializableExtra("id");
        setCurrentWord(word);
        initButton();

    }

    public boolean containsWord(String word){
        return wordManager.containsWord(word);
    }

    public void playWord(View view) {
        if (currentWord != null) {
            soundManager.start(currentWord.getSoundID());
        }
    }

    public void favoriteWord(View view) {

        String word = currentWord.getWord();
        View heart = findViewById(R.id.favorite);
        favoriteWords.toggleFavorite(word);

        if (favoriteWords.isFavoriteWord(word)) {
            heart.setBackgroundResource(R.drawable.fav_red);
        } else {
            heart.setBackgroundResource(R.drawable.fav_gray);
        }

        updateFavoriteWordsInMemory();
    }

    public void updateFavoriteWordsInMemory(){
        Set<String> set = new HashSet<String>();

        if(favoriteWords.getFavorites() != null){
            set.addAll(favoriteWords.getFavorites());
        }

        SharedPreferences settings = getSharedPreferences("favoriteWords", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet("favoriteWords", set);
        editor.commit();
    }
    public void setCurrentWord(String word){
        if(wordManager.containsWord(word)){
            currentWord = wordManager.getWordFromID(word);
        }
    }

    public void initButton(){
        Button heart = (Button)findViewById(R.id.favorite);

        if (currentWord != null && favoriteWords.isFavoriteWord(currentWord.getWord())) {
            heart.setBackgroundResource(R.drawable.fav_red);
        } else {
            heart.setBackgroundResource(R.drawable.fav_gray);
        }

        heart.setClickable(true);

//      soundManager.start(currentWord.getSoundID());  // TODO: 2016-10-03 get real sound path

    }
}




