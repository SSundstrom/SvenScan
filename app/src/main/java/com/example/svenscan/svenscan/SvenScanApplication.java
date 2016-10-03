package com.example.svenscan.svenscan;

import android.app.Application;

import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;
import com.example.svenscan.svenscan.repositories.FirebaseWordRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;
import com.karumi.dexter.Dexter;

public class SvenScanApplication extends Application {
    private IWordRepository wordRepository;
    private FavoriteWordRepository favoriteWordRepository; // todo: bör vara interface?

    public void onCreate() {
        super.onCreate();
        wordRepository = new FirebaseWordRepository();
        favoriteWordRepository = new FavoriteWordRepository();
        Dexter.initialize(this);

    }

    public IWordRepository getWordRepository() {
        return wordRepository;
    }

    public FavoriteWordRepository getFavoriteWordRepository() {
        return favoriteWordRepository;
    }
}
