package com.example.svenscan.svenscan.utils;

import android.app.Activity;

public interface IProgressManager {
    void wordScanned(Activity app);
    void setPoints(int points);
    int checkLevel();
    int getLevel();
    int getPoints();
    int getLevelProgress();
    String showLevel();
    String toString();
}
