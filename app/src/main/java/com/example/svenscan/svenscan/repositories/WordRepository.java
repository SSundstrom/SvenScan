package com.example.svenscan.svenscan.repositories;

import android.util.Log;

import com.example.svenscan.svenscan.models.Word;

import com.example.svenscan.svenscan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WordRepository {
    private DatabaseReference database;
    private HashMap<String, Word> wordMap;
    private static int index;

    public WordRepository() {
        wordMap = new HashMap<>();

        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        database = firebase.getReference("words");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot listSnapshot) {
                wordMap = (HashMap<String, Word>) listSnapshot.getValue();
                Log.d("WordRepository", "size: " + wordMap.size());

                Iterator<Map.Entry<String, Word>> mapIterator = wordMap.entrySet().iterator();
                while (mapIterator.hasNext()) {
                    String word = mapIterator.next().getKey();
                    Log.d("WordRepository", "Added " + word);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("WordRepository", "Failed to read value.", error.toException());
            }
        });
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
}
