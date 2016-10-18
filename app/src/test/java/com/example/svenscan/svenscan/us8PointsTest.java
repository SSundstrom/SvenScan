package com.example.svenscan.svenscan;

import android.app.Activity;

import com.example.svenscan.svenscan.utils.IProgressManager;
import com.example.svenscan.svenscan.utils.ProgressManager;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class us8PointsTest {


    @Test
    public void testCheckIfLevelUp(){

        IProgressManager progressManager = new ProgressManager();
        progressManager.setPoints(100);
        assertTrue(progressManager.checkLevel() == 2);
        progressManager.setPoints(150);
        assertTrue(progressManager.checkLevel() != 3);
    }


    @Test
    public void testWordScanned(){
        IProgressManager progressManager = new ProgressManager();
        Activity activity = new Activity();
        progressManager.setPoints(120);
        progressManager.wordScanned(activity);
        assertTrue(progressManager.getPoints() == 130);
    }
}
