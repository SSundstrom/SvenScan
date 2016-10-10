package com.example.svenscan.svenscan.models;

public class Points {

    private int points;
    private static Points instance = new Points();

    private Points(){
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

    public static Points getInstance(){
        if(instance == null){
            instance = new Points();
        }
        return instance;
    }

}
