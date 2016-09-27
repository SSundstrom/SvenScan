package com.example.svenscan.svenscan.repositories;

import com.example.svenscan.svenscan.models.Word;

import java.util.HashMap;

public class FavoriteWordRepository {

    private HashMap<String, Object> favorites = new HashMap<>();

    public void addFavorite(Word word){
        if(!favorites.containsKey(word.getWord())){
            favorites.put(word.getWord(), word);
        }
    }
    
    public void removeFavorite(Word word){
        if(favorites.containsKey(word.getWord())){
            favorites.remove(word.getWord());
        }
    }

    public boolean isFavoriteWord(Word word){
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
