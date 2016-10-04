package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.adapters.ArrayListAdapter;
import com.example.svenscan.svenscan.repositories.FavoriteWordRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        SvenScanApplication app = (SvenScanApplication) getApplication();
        FavoriteWordRepository favoriteWords = app.getFavoriteWordRepository();

        initList(favoriteWords.getFavorites());

    }
    private void initList(List<String> list){
        ListView favorites = (ListView) findViewById(R.id.favorites);
        favorites.setAdapter(new ArrayListAdapter((ArrayList<String>) list));
    }
}
