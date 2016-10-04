package com.example.svenscan.svenscan;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;
import com.example.svenscan.svenscan.repositories.FirebaseMediaRepository;
import com.example.svenscan.svenscan.repositories.FirebaseWordRepository;
import com.example.svenscan.svenscan.repositories.IFavoriteRepository;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;
import com.karumi.dexter.Dexter;
import com.example.svenscan.svenscan.utils.ocr.IOCR;
import com.example.svenscan.svenscan.utils.ocr.OCRDecoder;

import java.util.HashSet;

public class SvenScanApplication extends Application {
    private IWordRepository wordRepository;
    private IFavoriteRepository favoriteWordRepository; // todo: bör vara interface?
    private IOCR ocr;
    private IMediaRepository mediaRepository;

    public void onCreate() {
        super.onCreate();
        wordRepository = new FirebaseWordRepository();
        favoriteWordRepository = new FavoriteWordRepository();
        mediaRepository = new FirebaseMediaRepository();
        ocr = new OCRDecoder(this);

        System.out.println("Files dir = " + getApplicationContext().getFilesDir());
        mediaRepository.initialize(getApplicationContext().getFilesDir()); // TODO: 2016-10-04 move to after permissions

        recreateFavoriteWords();
        Dexter.initialize(this);
    }

    public IWordRepository getWordRepository() {
        return wordRepository;
    }

    public IOCR getOCR () {
        return ocr;
    }

    public IFavoriteRepository getFavoriteWordRepository() {
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
