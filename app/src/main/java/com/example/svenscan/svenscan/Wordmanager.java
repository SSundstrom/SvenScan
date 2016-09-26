package com.example.svenscan.svenscan;

import java.util.HashMap;


public class Wordmanager {

    private HashMap<String, Word> wordMap;

    public Wordmanager() {
        wordMap = new HashMap<>();
    }

    public void addWord(String id, Word word) {
        if (wordMap.containsKey(id)) return;
        wordMap.put(id.toUpperCase(), word);
    }

    public Word getWordFromID(String id) {
        return wordMap.get(id.toUpperCase());
    }
}
