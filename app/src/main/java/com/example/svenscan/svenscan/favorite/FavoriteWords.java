package com.example.svenscan.svenscan.favorite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FavoriteWords {

    private List<String> favorites = new ArrayList<String>();

    public void addFavorite(String s){
        favorites.add(s);
    }
    
    //TODO: fix if word doesn't exist, maybe send back boolean?
    public void removeFavorite(String s){
        Iterator<String> iter = favorites.listIterator();

        if(!favorites.isEmpty()) {
            while(iter.hasNext()) {
                if (iter.next().equals(s)) {
                    favorites.remove(s);
                    break;
                }
            }
        }
    }
    public void viewAll(){

    }
    public void viewOneWord(String s){

    }

    public List<String> getFavorites(){
        return this.favorites;
    }
}
