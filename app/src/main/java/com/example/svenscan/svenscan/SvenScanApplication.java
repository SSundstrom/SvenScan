package com.example.svenscan.svenscan;

import android.app.Application;

import com.example.svenscan.svenscan.repositories.FirebaseWordRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;

public class SvenScanApplication extends Application {
    private IWordRepository wordRepository;

    public void onCreate() {
        super.onCreate();
        wordRepository = new FirebaseWordRepository();
    }

    public IWordRepository getWordRepository() {
        return wordRepository;
    }
}
