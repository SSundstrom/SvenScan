package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;
import com.example.svenscan.svenscan.activities.tasks.OCRDecoderAsyncTask;
import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.repositories.WordRepository;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.svenscan.svenscan.utils.ocr.OCRDecoder;
import com.example.svenscan.svenscan.utils.Camera;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;


public class MainActivity extends AppCompatActivity implements Camera.ICameraCaptureHandler {
    private OCRDecoder ocr;
    private Camera camera;
    private WordRepository wordManager;
    private FavoriteWordRepository favoriteWords = new FavoriteWordRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = new Camera(this, this);
        ocr = new OCRDecoder(getApplication());
        wordManager = new WordRepository();
    }

    public void chooseImage(View view) {
        camera.show();
    }

    @Override
    public void onCameraCapture(Bitmap map) {
        setContentView(R.layout.activity_show_word);
        ImageView mainView = (ImageView)findViewById((R.id.imageView4));
        map = Bitmap.createScaledBitmap(map, 500, 500, false);
        mainView.setImageBitmap(map);
        View rootView = findViewById(android.R.id.content);
        Pix picture = ReadFile.readBitmap(map);
        new OCRDecoderAsyncTask(rootView, ocr, wordManager).execute(picture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.onActivityResult(requestCode, resultCode, data);
    }

    public void playSound(View view){
        Intent tmp = new Intent(this, PlayPronunciationActivity.class);
        startActivity(tmp);
    }
    public void recordPronunciation(View view){
        Intent tmp = new Intent(this, RecordPronunciationActivity.class);
        startActivity(tmp);
    }

    public void favoriteWord(View view){
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

    public void showFavoriteWords(View view){
        Intent intent = new Intent(this, FavoriteListActivity.class);
        intent.putExtra("favoriteWords", favoriteWords.getFavorites());
        startActivity(intent);
    }
}













