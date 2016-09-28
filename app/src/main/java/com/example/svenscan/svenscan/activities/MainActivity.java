package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;
import com.example.svenscan.svenscan.R;

import com.example.svenscan.svenscan.utils.Camera;

public class MainActivity extends AppCompatActivity implements Camera.ICameraCaptureHandler {
    private Camera camera;
    FavoriteWordRepository favoriteWords;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        camera = new Camera(this, this);
        setContentView(R.layout.activity_main);

        SvenScanApplication app = (SvenScanApplication) getApplication();
        favoriteWords = app.getFavoriteWordRepository();
    }

    public void chooseImage(View view) {
        camera.show();
    }

    public void playSound(View view){
        Intent tmp = new Intent(this, PlayPronunciationActivity.class);
        startActivity(tmp);
    }

    public void recordPronunciation(View view){
        Intent tmp = new Intent(this, RecordPronunciationActivity.class);
        startActivity(tmp);
    }

    public void showAllWords(View view){
        Intent tmp = new Intent(this, DebugWordListActivity.class);
        startActivity(tmp);
    }

    public void showFavoriteWords(View view){
        Intent intent = new Intent(this, FavoriteListActivity.class);
        intent.putExtra("favoriteWords", favoriteWords.getFavorites());
        startActivity(intent);
    }


    @Override
    public void onCameraCapture(String imagePath) {
        Intent intent = new Intent(this, ShowScannedWordActivity.class);
        intent.putExtra("picture", imagePath);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        camera.onActivityResult(requestCode, resultCode, data);
    }


}