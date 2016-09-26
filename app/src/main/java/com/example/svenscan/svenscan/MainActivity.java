package com.example.svenscan.svenscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.svenscan.svenscan.favorite.FavoriteListActivity;
import com.example.svenscan.svenscan.favorite.FavoriteWords;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;


public class MainActivity extends AppCompatActivity implements Camera.ICameraCaptureHandler {
    private OCRDecoder ocr;
    private Camera camera;

    private FavoriteWords favoriteWords = new FavoriteWords();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = new Camera(this, this);
        ocr = new OCRDecoder(getApplication());
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
        new OCRDecoderAsyncTask(rootView, ocr).execute(picture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.onActivityResult(requestCode, resultCode, data);
    }

    public void playSound(View view){
        Intent tmp = new Intent(this, PlayPronunciation.class);
        startActivity(tmp);
    }
    public void recordPronunciation(View view){
        Intent tmp = new Intent(this, RecordPronunciation.class);
        startActivity(tmp);
    }

    public void favoriteWord(View view){
        //TODO should be Label later on and not EditText
        String word = ocr.getText();
        favoriteWords.addFavorite(word);
    }

    public void showFavoriteWords(View view){
        Intent intent = new Intent(this, FavoriteListActivity.class);
        intent.putExtra("favoriteWords", favoriteWords.getFavorites());
        startActivity(intent);
    }
}













