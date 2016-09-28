package com.example.svenscan.svenscan.repositories;

import com.example.svenscan.svenscan.models.Word;

public interface IWordRepository {
    void add(String id, Word word);
    boolean contains(String word);
    Word get(String id);

    // todo: this should be moved to favorites
    boolean toggleFavorite(String word);
}
