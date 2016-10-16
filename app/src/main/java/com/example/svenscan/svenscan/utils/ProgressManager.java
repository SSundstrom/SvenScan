package com.example.svenscan.svenscan.utils;

import android.app.Activity;
import android.content.SharedPreferences;

public class ProgressManager implements IProgressManager {

    private int points;
    private int level;

    public ProgressManager(){
        this.points = 0;
        this.level = 1;
        checkLevel();

    }

    public void wordScanned(Activity app) {
        earnPoints();
        SharedPreferences settings = app.getSharedPreferences("points", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("points", points);
        editor.commit();
        checkIfLevelUp();
    }


    public void setPoints(int points){
        this.points = points;
    }

    public int getPoints(){
        return this.points;
    }

    @Override
    public int getLevelProgress() {
        return points % 100;
    }

    private void earnPoints(){
        this.points = points + 10;
    }


    private void checkIfLevelUp(){
        if(points % 100 == 0){
            levelUp();
        }
    }

    public int checkLevel(){
        int tempPoints = points;
        while(tempPoints >= 100){
            tempPoints = tempPoints - 100;
            level = level + 1;
        }
        return level;
    }

    @Override
    public int getLevel() {
        return level;
    }

    private void levelUp(){
        level = level + 1;
    }

    public String showLevel(){
        return "Nivå:" + " " + level;
    }

    public String toString(){
        return "Poäng: " + this.points;
    }

}
