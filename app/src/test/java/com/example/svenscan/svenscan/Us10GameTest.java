package com.example.svenscan.svenscan;

import android.support.v7.app.AppCompatActivity;

import com.example.svenscan.svenscan.models.Game;
import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

public class Us10GameTest extends AppCompatActivity {


    private Game game;
    private Map<String, Word> allWords;

    private Word word;
    @Before
    public void setUp(){
        allWords = new HashMap<>();
        allWords.put("kök", word);
        allWords.put("stol", word);
        allWords.put("bord", word);
        allWords.put("säng", word);
        game = new Game(allWords);
    }


    @Test
    public void testCorrectAnswer(){
        game.newQuestion();
        assertTrue(game.getCorrectWord().equals(game.getChoices().get(0)));
        String wrongWord = game.getChoices().get(1);
        assertFalse(game.getCorrectWord().equals(wrongWord));


    }
}
