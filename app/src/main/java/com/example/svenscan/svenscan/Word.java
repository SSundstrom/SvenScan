package com.example.svenscan.svenscan;

import java.io.Serializable;

public class Word implements Serializable{

    private String soundPath;
    private String imagePath;
    private String word;

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
}
