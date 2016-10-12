package com.example.svenscan.svenscan.utils;

import android.app.Activity;
import android.content.SharedPreferences;

public class ProgressManager {

    private int points;
    private int level;

    public ProgressManager(){
        this.points = 0;
        this.level = 1;
    }

    public void wordScanned(Activity app){
        earnPoints();
        checkIfLevelUp();
        SharedPreferences settings = app.getSharedPreferences("points", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("points", points);
        editor.commit();
    }

    public void setPoints(int points){
        this.points = points;
    }

    public int getPoints(){
        return this.points;
    }

    private void earnPoints(){
        this.points = points + 10;
    }


    private void checkIfLevelUp(){
        if(points % 100 == 0){
            levelUp();
        }
    }

    private void levelUp(){
        level = level + 1;
    }

    public String showLevel(){
        return "Nivå:" + " " + level;
    }

    public String toString(){
        return "Poäng:" + " " + this.points;
    }


}
