package com.example.svenscan.svenscan.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.IWordRepository;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class DebugWordListActivity extends AppCompatActivity implements Observer {
    private IWordRepository wordRepository;
    private ArrayAdapter<String> adapter;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_word_list);

        SvenScanApplication app = (SvenScanApplication) getApplication();
        wordRepository = app.getWordRepository();

        ListView listView  = (ListView) findViewById(R.id.wordList);

        String[] items = getWordsFromMap(wordRepository.getAll());

       adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        wordRepository.addObserver(this);
    }

    private String[] getWordsFromMap(Map<String, Word> map) {
        String[] words = new String[map.keySet().size()];
        return map.keySet().toArray(words);
    }

    @Override
    public void update(Observable o, Object arg) {
        ListView listView  = (ListView) findViewById(R.id.wordList);

        String[] items = getWordsFromMap(wordRepository.getAll());

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);

        listView.setAdapter(adapter);
    }
}
