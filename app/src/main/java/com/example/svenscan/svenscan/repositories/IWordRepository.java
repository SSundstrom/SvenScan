package com.example.svenscan.svenscan.repositories;

import com.example.svenscan.svenscan.interfaces.IObservable;
import com.example.svenscan.svenscan.models.Word;

import java.util.Map;

public interface IWordRepository extends IObservable {
    void add(String id, Word word);
    boolean contains(String word);
    Word get(String id);
    Map<String, Word> getAll();

    // todo: this should be moved to favorites
    boolean toggleFavorite(String word);
}