package com.example.svenscan.svenscan.repositories;

import com.example.svenscan.svenscan.models.Word;

import com.example.svenscan.svenscan.R;

import java.util.HashMap;

public class WordRepository {

    private HashMap<String, Word> wordMap;
    private static int index;

    public WordRepository() {
        wordMap = new HashMap<>();
        createWords();
    }

    public void addWord(String id, Word word) {
        if (wordMap.containsKey(id)) return;
        wordMap.put(id.toUpperCase(), word);
        index++;
    }

    public boolean containsWord(String word) {
        return wordMap.containsKey(word);
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

    private void createWords() {
        wordMap.put("BORD", new Word("BORD", "", R.raw.giggity));
        wordMap.put("HEJ", new Word("HEJ", "", R.raw.giggity));
        wordMap.put("STOL", new Word("STOL", "", R.raw.giggity));
        wordMap.put("NAKEN", new Word("NAKEN", "", R.raw.giggity));
        wordMap.put("JA", new Word("JA", "", R.raw.giggity));
        wordMap.put("OCKSÅ", new Word("OCKSÅ", "", R.raw.giggity));
    }
}
