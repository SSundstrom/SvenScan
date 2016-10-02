package com.example.svenscan.svenscan.repositories;

import com.example.svenscan.svenscan.models.Word;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FavoriteWordRepository {

    private List<String> favorites = new ArrayList<String>();

    public void addFavorite(String word){
        if(!favorites.contains(word.toUpperCase())){
            favorites.add(word.toUpperCase());
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
        if(isFavoriteWord(word)){
            removeFavorite(word);
        }
        else{
            addFavorite(word);
        }
    }

    public boolean isFavoriteWord(String word){
        return favorites.contains(word.toUpperCase());
    }

    public List<String> getFavorites(){
        return this.favorites;
    }
}
