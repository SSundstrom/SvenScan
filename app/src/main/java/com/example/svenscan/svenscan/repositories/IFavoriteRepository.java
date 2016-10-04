package com.example.svenscan.svenscan.repositories;

import android.app.Activity;

import java.util.HashSet;
import java.util.List;

public interface IFavoriteRepository {

    /**
     * Will change the favorite status of the first argument
     * @param word to be removed or added to the favorites
     * @param app used for storing the favorites data
     */
    void toggleFavorite(String word, Activity app);

    boolean isFavoriteWord(String word);

    List<String> getFavorites();

    /**
     * When the application is started this is used to load the stored favorites
     * @param set stored favorites
     */
    void addSetToFavorites(HashSet<String> set);
}
