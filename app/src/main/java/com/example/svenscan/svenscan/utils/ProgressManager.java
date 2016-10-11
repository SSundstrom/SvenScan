package com.example.svenscan.svenscan.utils;

public class ProgressManager {

    private int points;
    private int level;

    public ProgressManager(){
        this.points = 0;
        this.level = 1;
    }

    public void wordScanned(){
        earnPoints();
        checkIfLevelUp();
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
        return "Nivå:" + level;
    }

    public String toString(){
        return "Poäng:" + this.points;
    }


}
