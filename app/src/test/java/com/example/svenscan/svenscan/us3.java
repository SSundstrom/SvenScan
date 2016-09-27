package com.example.svenscan.svenscan;

import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Map;

public class us3 {

    FavoriteWordRepository favoriteWords;
    Map<String, Object> favorites;

    @Before
    public void before(){
        favoriteWords = new FavoriteWordRepository();
        favorites = favoriteWords.getFavorites();
    }

    @Test
    public void testAddFavorite(){
        Word word = new Word("stol", " ", 67);
        favoriteWords.addFavorite(word);

        assertTrue(favoriteWords.isFavoriteWord(word));
    }

    @Test
    public void testRemoveFavorite(){
        Word word = new Word("stol", " ", 67);
        favoriteWords.removeFavorite(word);
        favoriteWords.addFavorite(word);
        favoriteWords.removeFavorite(word);

        assertTrue(favorites.isEmpty());
    }

    @Test
    public void testViewAll(){

    }

    @Test
    public void testViewOneWord(){

    }





}





