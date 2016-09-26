package com.example.svenscan.svenscan.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.svenscan.svenscan.MainActivity;
import com.example.svenscan.svenscan.R;

import java.util.HashMap;
import java.util.Objects;

public class FavoriteListActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Intent intent = getIntent();
        HashMap<String,Object> hm = (HashMap<String,Object>) intent.getSerializableExtra("favoriteWords");
        initList(hm);

    }
    private void initList(HashMap<String, Object> hashMap){
        ListView favorites = (ListView) findViewById(R.id.favorites);
        favorites.setAdapter(new HashMapAdapter(hashMap));
    }
}
