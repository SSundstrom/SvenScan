package com.example.svenscan.svenscan.models;

import java.io.Serializable;

public class Word implements Serializable{

    private int soundID;
    private String imagePath;
    private String word;

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

}