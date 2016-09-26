package com.example.svenscan.svenscan.favorite;

import com.example.svenscan.svenscan.Word;

import java.util.HashMap;

public class FavoriteWords {

    private HashMap<String, Object> favorites = new HashMap<>();

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

    public HashMap<String, Object> getFavorites(){
        return this.favorites;
    }
}
