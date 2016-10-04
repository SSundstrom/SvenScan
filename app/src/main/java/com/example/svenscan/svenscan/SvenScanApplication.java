package com.example.svenscan.svenscan;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;
import com.example.svenscan.svenscan.repositories.FirebaseMediaRepository;
import com.example.svenscan.svenscan.repositories.FirebaseWordRepository;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;
import com.karumi.dexter.Dexter;
import com.example.svenscan.svenscan.utils.ocr.IOCR;
import com.example.svenscan.svenscan.utils.ocr.OCRDecoder;

import java.util.HashSet;

public class SvenScanApplication extends Application {
    private IWordRepository wordRepository;
    private FavoriteWordRepository favoriteWordRepository; // todo: bör vara interface?
    private IOCR ocr;
    private IMediaRepository mediaRepository;

    public void onCreate() {
        super.onCreate();
        wordRepository = new FirebaseWordRepository();
        favoriteWordRepository = new FavoriteWordRepository();
        System.out.println("Files dir = " + getApplicationContext().getFilesDir());
        mediaRepository = new FirebaseMediaRepository(getApplicationContext().getFilesDir());
        ocr = new OCRDecoder(this);

        recreateFavoriteWords();
        Dexter.initialize(this);
    }

    public IWordRepository getWordRepository() {
        return wordRepository;
    }

    public IOCR getOCR () {
        return ocr;
    }

    public FavoriteWordRepository getFavoriteWordRepository() {
        return favoriteWordRepository;
    }

    public IMediaRepository getMediaRepository() {
        return mediaRepository;
    }

    public void recreateFavoriteWords(){
        HashSet<String> set = new HashSet<String>();
        SharedPreferences settings = getSharedPreferences("favoriteWords", 0);
        set = (HashSet<String>) settings.getStringSet("favoriteWords", set);
        favoriteWordRepository.addSetToFavorites(set);
    }


}
