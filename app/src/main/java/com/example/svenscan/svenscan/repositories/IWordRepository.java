package com.example.svenscan.svenscan.repositories;

import com.example.svenscan.svenscan.interfaces.IObservable;
import com.example.svenscan.svenscan.models.Word;

import java.util.Map;

public interface IWordRepository extends IObservable {
    void addWord(String id, Word word);
    boolean containsWord(String word);
    Word getWordFromID(String id);
    Map<String, Word> getAllWords();
}