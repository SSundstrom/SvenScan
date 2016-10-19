package com.example.svenscan.svenscan.utils;


public interface IProgressManager {
    void earnPoints(String source);
    int getLevel();
    int getPoints();
    int getLevelProgress();
}
