package com.example.svenscan.svenscan;

import com.example.svenscan.svenscan.favorite.FavoriteWords;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class US3 {

    FavoriteWords favoriteWords;
    List<String> favorites;

    @Before
    public void before(){
        favoriteWords = new FavoriteWords();
        favorites = favoriteWords.getFavorites();
    }

    @Test
    public void testAddFavorite(){
        favoriteWords.addFavorite("stol");
        favoriteWords.addFavorite("bord");

        
    }

    @Test
    public void testRemoveFavorite(){

    }

    @Test
    public void testViewAll(){

    }

    @Test
    public void testViewOneWord(){

    }





}























