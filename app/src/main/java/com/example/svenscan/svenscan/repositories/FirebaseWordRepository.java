package com.example.svenscan.svenscan.repositories;

import android.util.Log;

import com.example.svenscan.svenscan.models.Word;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class FirebaseWordRepository implements ValueEventListener, IWordRepository {
    private Observable observable = new Observable();
    private DatabaseReference database;
    private HashMap<String, Word> wordMap;

    public FirebaseWordRepository() {
        wordMap = new HashMap<>();

        FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        database = firebase.getReference("words");

        // Listen for database changes
        database.addValueEventListener(this);
    }

    @Override
    public void addWord(String id, Word word) {
        if (wordMap.containsKey(id)) return;

        wordMap.put(id.toUpperCase(), word);

        // Add to Firebase
        database.child(id).setValue(word);
    }

    @Override
    public boolean containsWord(String word) {
        return wordMap.containsKey(word);
    }

    public Word getWordFromID(String id) {
        return wordMap.get(id);
    }

    @Override
    public Map<String, Word> getAllWords() {
        return wordMap;
    }

    /**
     * Triggered by Firebase when the list of words successfully change on the server
     * @param listSnapshot A Firebase reference to the list of words
     */
    @Override
    public void onDataChange(DataSnapshot listSnapshot) {

        for (DataSnapshot wordSnapshot : listSnapshot.getChildren()) {
            Word word = wordSnapshot.getValue(Word.class);
            word.setWordID(wordSnapshot.getKey());
            wordMap.put(wordSnapshot.getKey(), word);
        }

        debugPrintAllWords();

        observable.notifyObservers();
    }

    /**
     * Triggered by Firebase when the list of words fail to change on the server
     * @param error An error
     */
    @Override
    public void onCancelled(DatabaseError error) {
        // Failed to read value
        Log.w("FirebaseWordRepository", "Failed to read value.", error.toException());
    }

    private void debugPrintAllWords() {
        Iterator<Map.Entry<String, Word>> mapIterator = wordMap.entrySet().iterator();

        while (mapIterator.hasNext()) {
            Word word = mapIterator.next().getValue();
            Log.d("FirebaseWordRepository", "Added " + word);

        }
    }

    @Override
    public void addObserver(Observer observer) {
        observable.addObserver(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observable.deleteObserver(observer);
    }
}
