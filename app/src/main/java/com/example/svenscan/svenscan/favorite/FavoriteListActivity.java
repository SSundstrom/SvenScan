package com.example.svenscan.svenscan.favorite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.svenscan.svenscan.R;

public class FavoriteListActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        initList();
    }
    private void initList(){
        ListView favorites = (ListView) findViewById(R.id.favorites);
        //TODO: create new adapter class
        //favorites.setAdapter();
    }
}
