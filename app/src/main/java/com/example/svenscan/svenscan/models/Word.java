package com.example.svenscan.svenscan.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Word {

    private int soundID;
    private String imagePath;
    private String word;

    public Word() {
        // Default constructor is required by Firebase
    }

    public Word(String word, String imagePath, int soundPath) {
        this.word = word;
        this.imagePath = imagePath;
        this.soundID = soundPath;
    }

    public String getWord() {
        return word;
    }

    public int getSoundID() {
        return soundID;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Word = " + word + " | soundId = " + soundID;
    }

}