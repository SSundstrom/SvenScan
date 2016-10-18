package com.example.svenscan.svenscan.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.svenscan.svenscan.models.Points;

public class ProgressManager implements IProgressManager {

    private SharedPreferences sharedPreferences;
    private Points points;


    public ProgressManager(SharedPreferences sharedPreferences){

        this.sharedPreferences = sharedPreferences;
        points = new Points(sharedPreferences.getInt("points", 0));


    }

    private void wordScanned() {
        points.earnPoints();

    }

    @Override
    public void earnPoints() {
        wordScanned();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("points", points.getPoints());
        editor.commit();
    }

    @Override
    public int getLevel() {
        return points.getLevel();
    }

    @Override
    public int getPoints() {
        return points.getPoints();
    }

    @Override
    public int getLevelProgress() {
        return points.getLevelProgress();
    }


}
