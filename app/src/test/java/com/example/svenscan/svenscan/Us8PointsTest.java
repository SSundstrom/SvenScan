package com.example.svenscan.svenscan;

import com.example.svenscan.svenscan.models.Points;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

public class Us8PointsTest {


    @Test
    public void testCheckIfLevelUp() {

        Points points = new Points(130);
        points.earnPoints(10);
        assertTrue(points.getPoints() == 140);


    }



}
