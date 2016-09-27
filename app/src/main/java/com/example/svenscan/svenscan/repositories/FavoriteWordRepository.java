package com.example.svenscan.svenscan.repositories;

import com.example.svenscan.svenscan.models.Word;

import java.util.HashMap;

public class FavoriteWordRepository {

    private HashMap<String, Object> favorites = new HashMap<>();

    public void addFavorite(String s){
        if(!favorites.containsKey(s)){
            favorites.put(s, new Word(s, "", 1));
        }
    }
    
    public void removeFavorite(String s){
        if(favorites.containsKey(s)){
            favorites.remove(s);
        }
    }

    public boolean isFavoriteWord(String word){
        return favorites.containsKey(word);
    }

    public void viewAll(){

    }
    public void viewOneWord(String s){

    }

    public HashMap<String, Object> getFavorites(){
        return this.favorites;
    }
}
