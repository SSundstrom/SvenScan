package com.example.svenscan.svenscan.favorite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.svenscan.svenscan.R;

import java.util.HashMap;

public class FavoriteListActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

    }
    private void initList(HashMap<String, Object> hashMap){
        ListView favorites = (ListView) findViewById(R.id.favorites);
        favorites.setAdapter(new HashMapAdapter(hashMap));
    }
}
