package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;
import com.example.svenscan.svenscan.activities.tasks.OCRDecoderAsyncTask;
import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.repositories.FirebaseWordRepository;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.svenscan.svenscan.repositories.IWordRepository;
import com.example.svenscan.svenscan.utils.ocr.OCRDecoder;
import com.example.svenscan.svenscan.utils.Camera;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;


public class MainActivity extends AppCompatActivity implements Camera.ICameraCaptureHandler {
    private OCRDecoder ocr;
    private Camera camera;
    private IWordRepository wordManager;
    private FavoriteWordRepository favoriteWords = new FavoriteWordRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = new Camera(this, this);

        SvenScanApplication app = (SvenScanApplication) getApplication();
        wordManager = app.getWordRepository();
    }

    public void chooseImage(View view) {
        camera.show();
    }

    @Override
    public void onCameraCapture(Bitmap map) {
        ImageView mainView = (ImageView)findViewById((R.id.imageView));
        map = Bitmap.createScaledBitmap(map, 500, 500, false);
        mainView.setImageBitmap(map);
        View rootView = findViewById(android.R.id.content);
        Pix picture = ReadFile.readBitmap(map);

        if (ocr == null)
            ocr = new OCRDecoder(getApplication());

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
        if (ocr.getText() == null || !wordManager.contains(ocr.getText())) {
            return;
        }
        String word = ocr.getText();
        View heart = findViewById(R.id.favorite);
        if (wordManager.toggleFavorite(word)) {
            heart.setBackgroundResource(R.drawable.fav_red);
            favoriteWords.addFavorite(wordManager.get(word));
        } else {
            heart.setBackgroundResource(R.drawable.fav_gray);
            favoriteWords.removeFavorite(wordManager.get(word));
        }
    }

    public void showAllWords(View view) {
        Intent intent = new Intent(this, DebugWordListActivity.class);
        startActivity(intent);
    }

    public void showFavoriteWords(View view){
        Intent intent = new Intent(this, FavoriteListActivity.class);
        intent.putExtra("favoriteWords", favoriteWords.getFavorites());
        startActivity(intent);
    }
}













