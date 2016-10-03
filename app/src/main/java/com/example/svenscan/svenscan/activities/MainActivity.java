package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.svenscan.svenscan.Manifest;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;
import com.example.svenscan.svenscan.R;

import com.example.svenscan.svenscan.utils.Camera;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.EmptyMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements Camera.ICameraCaptureHandler {
    private Camera camera;
    FavoriteWordRepository favoriteWords;

    private MultiplePermissionsListener allPermissionsListener;
    private PermissionListener cameraPermissionListener;
    private PermissionListener audioPermissionListener;
    private PermissionListener storagePremission;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        camera = new Camera(this, this);
        setContentView(R.layout.activity_main);

        SvenScanApplication app = (SvenScanApplication) getApplication();
        favoriteWords = app.getFavoriteWordRepository();

        recreateFavoriteWords();

        createPermissionListeners();


    }

    public void showPermissionGranted(String permission) {
        TextView feedbackView = getFeedbackViewForPermission(permission);
        feedbackView.setText(R.string.permission_granted_feedback);
        feedbackView.setTextColor(ContextCompat.getColor(this, R.color.permission_granted));
    }


    @OnClick(R.id.button) public void onAllPermissionsButtonClicked() {
        if (Dexter.isRequestOngoing()) {
            return;
        }
        Dexter.checkPermissions(allPermissionsListener, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO);
    }

    private void createPermissionListeners() {

        MultiplePermissionsListener feedbackViewMultiplePermissionListener = new EmptyMultiplePermissionsListener();
        allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with(rootView,
                                "Permission denied")
                                .withOpenSettingsButton("Denied")
                                .build());

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

        //TODO: Where should this code be?
    /*
    public void favoriteWord(View view){
        if (ocr.getText() == null || !wordManager.containsWord(ocr.getText())) {
            return;
        }
        String word = ocr.getText();
        View heart = findViewById(R.id.favorite);

        if (favoriteWords.isFavoriteWord(word)) {
            heart.setBackgroundResource(R.drawable.fav_gray);
            favoriteWords.removeFavorite(word);
        } else {
            heart.setBackgroundResource(R.drawable.fav_red);
            favoriteWords.addFavorite(word);

        }*/
    }

    public void showFavoriteWords(View view){
        Intent intent = new Intent(this, FavoriteListActivity.class);
        intent.putExtra("favoriteWords", (ArrayList<String>)favoriteWords.getFavorites());
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

    @Override
    protected void onStop(){
        super.onStop();
        saveFavoriteWords();
    }

    public void recreateFavoriteWords(){
        HashSet<String> set = new HashSet<String>();
        SharedPreferences settings = getSharedPreferences("favoriteWords", 0);
        set = (HashSet<String>) settings.getStringSet("favoriteWords", set);
        favoriteWords.addSetToFavorites(set);
    }

    public void saveFavoriteWords(){
        Set<String> set = new HashSet<String>();

        if(favoriteWords.getFavorites() != null){
            set.addAll(favoriteWords.getFavorites());
        }

        SharedPreferences settings = getSharedPreferences("favoriteWords", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet("favoriteWords", set);
        editor.commit();
    }


}

