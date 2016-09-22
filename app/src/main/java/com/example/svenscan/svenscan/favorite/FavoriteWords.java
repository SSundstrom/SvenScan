package com.example.svenscan.svenscan.favorite;

import com.example.svenscan.svenscan.Word;

import java.util.HashMap;
import java.util.Map;

public class FavoriteWords {

    private Map<String, Word> favorites = new HashMap<>();

    public void addFavorite(String s){
        if(!favorites.containsKey(s)){
            favorites.put(s, new Word());
        }

        //TODO remove
        System.out.println("Words in the list: " + favorites.keySet());
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

    public Map<String, Word> getFavorites(){
        return this.favorites;
    }
}
