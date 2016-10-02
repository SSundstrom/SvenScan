package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.adapters.ArrayListAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Intent intent = getIntent();
        ArrayList<String> hm = (ArrayList<String>) intent.getSerializableExtra("favoriteWords");
        initList(hm);

    }
    private void initList(ArrayList<String> list){
        ListView favorites = (ListView) findViewById(R.id.favorites);
        favorites.setAdapter(new ArrayListAdapter((ArrayList<String>) list));
    }
}
