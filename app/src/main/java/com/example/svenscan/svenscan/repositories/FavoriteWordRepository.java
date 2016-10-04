package com.example.svenscan.svenscan.repositories;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoriteWordRepository implements IFavoriteRepository {

    private List<String> favorites;

    public FavoriteWordRepository() {
        favorites = new ArrayList<>();
    }

    public void addFavorite(String word){
        if(!favorites.contains(word)){
            favorites.add(word);
        }
    }

    public void addSetToFavorites(HashSet<String> set){
        favorites.addAll(set);
    }
    
    private void removeFavorite(String word){
        if(!favorites.isEmpty()) {
            if(favorites.contains(word)){
                favorites.remove(word);
            }
        }
    }

    public void toggleFavorite(String word, Activity app){
        if(isFavoriteWord(word)){
            removeFavorite(word);
        }
        else{
            addFavorite(word);
        }
        updateFavoriteWordsInMemory(app);
    }

    public boolean isFavoriteWord(String word){
        if (word == null) return false;

        word = word.toUpperCase();
        return favorites.contains(word);
    }

    public List<String> getFavorites(){
        return this.favorites;
    }


    private void updateFavoriteWordsInMemory(Activity app){ // TODO: 2016-10-04 Should not be in this class.. Should prob happen in FavoriteRepository.
        Set<String> set = new HashSet<String>();

        if(getFavorites() != null){
            set.addAll(getFavorites());
        }

        SharedPreferences settings = app.getSharedPreferences("favoriteWords", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet("favoriteWords", set);
        editor.commit();
    }
}
