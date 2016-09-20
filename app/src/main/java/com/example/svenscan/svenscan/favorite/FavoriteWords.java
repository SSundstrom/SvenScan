package com.example.svenscan.svenscan.favorite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FavoriteWords {

    private List<String> favorites = new ArrayList<String>();

    public void add(String s){
        favorites.add(s);
    }
    
    //TODO: fix if word doesn't exist, maybe send back boolean?
    public void remove(String s){
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
}
