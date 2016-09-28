package com.example.svenscan.svenscan.repositories;

import java.util.ArrayList;
import java.util.List;

public class FavoriteWordRepository {

    private List<String> favorites = new ArrayList<String>();

    public void addFavorite(String word){
        if(favorites.contains(word.toUpperCase())){
            favorites.add(word.toUpperCase());
        }
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
            favorites.remove(word);
        }
        else{
            favorites.add(word);
        }
    }

    public boolean isFavoriteWord(String word){
        return favorites.contains(word);
    }

    public void viewAll(){

    }

    public void viewOneWord(String s){

    }

    public List<String> getFavorites(){
        return this.favorites;
    }
}
