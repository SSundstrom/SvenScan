package com.example.svenscan.svenscan.utils;

public class Settings {

    private static boolean edgeFilter = false;
    private static boolean binarize = true;

    public static void toggleEdgeFilter() {
        edgeFilter = !edgeFilter;
    }

    public static boolean getEdgeFilter() {
        return edgeFilter;
    }

    public static void toggleBinarizeFilter() {
        binarize = !binarize;
    }

    public static boolean isBinarize() {
        return binarize;
    }
}
