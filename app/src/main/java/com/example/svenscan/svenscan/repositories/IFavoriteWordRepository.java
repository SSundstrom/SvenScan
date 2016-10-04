package com.example.svenscan.svenscan.repositories;

import android.app.Activity;

import java.util.HashSet;
import java.util.List;

public interface IFavoriteWordRepository {

    void toggleFavorite(String word, Activity app);

    boolean isFavoriteWord(String word);

    List<String> getFavorites();

    void addSetToFavorites(HashSet<String> set);
}
