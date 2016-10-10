package com.example.svenscan.svenscan.models;

public class Points {

    private int points;

    public Points(){
        this.points = 0;
    }

    public void setPoints(int points){
        this.points = points;
    }

    public int getPoints(){
        return this.points;
    }

    public void earnPoints(){
        this.points = points + 10;
    }

    public String toString(){
        return "Points:" + this.points;
    }

}
