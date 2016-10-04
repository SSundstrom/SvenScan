package com.example.svenscan.svenscan.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Word {

    private String soundPath;
    private String imagePath;
    private String word;
    private String wordID;


    public Word() {
        // Default constructor is required by Firebase
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

    public void setWordID(String wordID) {
        this.wordID = wordID;
    }

    public String getWordID() {
        return wordID;
    }

    @Override
    public String toString() {
        return "Word = " + word + " | soundId = " + soundPath;
    }

}