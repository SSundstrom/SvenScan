package com.example.svenscan.svenscan.repositories;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FavoriteWordRepository {

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
    
    public void removeFavorite(String word){
        if(!favorites.isEmpty()) {
            if(favorites.contains(word)){
                favorites.remove(word);
            }
        }
    }

    public void toggleFavorite(String word){
        word = word.toUpperCase();
        if(isFavoriteWord(word)){
            removeFavorite(word);
        }
        else{
            addFavorite(word);
        }
    }

    public boolean isFavoriteWord(String word){
        if (word == null) return false;

        word = word.toUpperCase();
        return favorites.contains(word);
    }

    public List<String> getFavorites(){
        return this.favorites;
    }
}
