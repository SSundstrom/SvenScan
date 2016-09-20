package com.example.svenscan.svenscan.favorite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FavoriteWords {

    private List<String> favorites = new ArrayList<String>();

    public void addFavorite(String s){
        if(!isFavoriteWord(s)){
            favorites.add(s);
        }
    }
    
    public void removeFavorite(String s){
        if(isFavoriteWord(s)){
            favorites.remove(s);
        }
    }
    public boolean isFavoriteWord(String s){
        Iterator<String> iter = favorites.listIterator();

        if(!favorites.isEmpty()) {
            while(iter.hasNext()) {
                if (iter.next().equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void viewAll(){

    }
    public void viewOneWord(String s){

    }

    public List<String> getFavorites(){
        return this.favorites;
    }
}
