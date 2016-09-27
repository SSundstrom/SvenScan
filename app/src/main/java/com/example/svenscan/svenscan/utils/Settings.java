package com.example.svenscan.svenscan.utils;

public class Settings {

    private static boolean edgeFilter = false;

    public static void toggleEdgeFilter() {
        edgeFilter = !edgeFilter;
    }

    public static boolean getEdgeFilter() {
        return edgeFilter;
    }
}
