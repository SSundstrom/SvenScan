package com.example.svenscan.svenscan;


import android.util.SparseArray;


public class Wordmanager {

    private SparseArray<Word> wordMap;

    public Wordmanager() {
        wordMap = new SparseArray<>();
    }

    public void addWord(int id, Word word) {
        if (wordMap.valueAt(id) != null) return;
        wordMap.put(id, word);
    }

    public Word getWordFromID (int id) {
        return wordMap.get(id);
    }
}
