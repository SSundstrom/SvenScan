package com.example.svenscan.svenscan;

import android.app.Activity;

import com.example.svenscan.svenscan.models.Points;
import com.example.svenscan.svenscan.utils.IProgressManager;
import com.example.svenscan.svenscan.utils.ProgressManager;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class us8PointsTest {


    @Test
    public void testCheckIfLevelUp() {

        Points points = new Points(130);
        points.earnPoints();
        assertTrue(points.getPoints() == 140);


    }



}
