package com.example.svenscan.svenscan;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;
import com.example.svenscan.svenscan.repositories.FirebaseWordRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;

import java.util.HashSet;

public class SvenScanApplication extends Application {
    private IWordRepository wordRepository;
    private FavoriteWordRepository favoriteWordRepository; // todo: bör vara interface?

    public void onCreate() {
        super.onCreate();
        wordRepository = new FirebaseWordRepository();
        favoriteWordRepository = new FavoriteWordRepository();

        recreateFavoriteWords();
    }

    public IWordRepository getWordRepository() {
        return wordRepository;
    }

    public FavoriteWordRepository getFavoriteWordRepository() {
        return favoriteWordRepository;
    }

    public void recreateFavoriteWords(){
        HashSet<String> set = new HashSet<String>();
        SharedPreferences settings = getSharedPreferences("favoriteWords", 0);
        set = (HashSet<String>) settings.getStringSet("favoriteWords", set);
        favoriteWordRepository.addSetToFavorites(set);
    }


}
