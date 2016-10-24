package com.example.svenscan.svenscan;

import com.example.svenscan.svenscan.models.Points;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.failNotEquals;

public class Us8PointsTest {

    private Points points;

    @Before
    public void setup(){
        points = new Points(50);
    }

    @Test
    public void testEarnPoints() {
        points.earnPoints(1);
        assertTrue(points.getPoints() == 51);
    }

    @Test
    public void testLevelProgress(){
        int currentLevelProgress = points.getLevelProgress();
        points.earnPoints(10);
        assertFalse("Should not be equal 51 != 61", currentLevelProgress == points.getLevelProgress());
        assertEquals("Should have same value (61). ", 61, points.getLevelProgress());
    }



}
