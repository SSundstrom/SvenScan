package com.example.svenscan.svenscan.models;

public class Points {

    private int points;
    private int level;

    public Points(int points){
        this.points = points;
        checkIfLevelUp();
    }


    public int getLevel() {
        return level;
    }

    private void levelUp(){
        level = level + 1;
    }

    public int getPoints(){
        return this.points;
    }

    public int getLevelProgress() {
        return points % 100;
    }

    public void earnPoints(){
        this.points = points + 10;
        checkIfLevelUp();
    }


    private void checkIfLevelUp(){
        if(points % 100 == 0){
            levelUp();
        }
    }

}
