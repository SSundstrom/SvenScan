package com.example.svenscan.svenscan.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Word {

    private String soundPath;
    private String imagePath;
    private String word;

    public Word() {
        // Default constructor is required by Firebase
    }

    public Word(String word, String imagePath, String soundPath) {
        this.word = word;
        this.imagePath = imagePath;
        this.soundPath = soundPath;
    }

    public String getWord() {
        return word;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Word = " + word + " | soundId = " + soundPath;
    }

}