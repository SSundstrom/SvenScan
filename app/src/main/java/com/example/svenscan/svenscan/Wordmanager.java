package com.example.svenscan.svenscan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Wordmanager {

    private HashMap<String, Word> wordMap;
    private static int index;

    public Wordmanager() {
        wordMap = new HashMap<>();
    }

    public void addWord(String id, Word word) {
        if (wordMap.containsKey(id)) return;
        wordMap.put(id.toUpperCase(), word);
        index++;
    }

    public boolean toggleFavorite(String word) {

        word = word.toUpperCase();
        if (!wordMap.containsKey(word)) {
            addWord(word, new Word(word, "", this.hashCode()+index));
        }
        wordMap.get(word).setFavorite(!wordMap.get(word).isFavorite());

        return wordMap.get(word).isFavorite();
    }

    public Word getWordFromID(String id) {
        return wordMap.get(id.toUpperCase());
    }
}