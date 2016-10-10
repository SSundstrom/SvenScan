package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.adapters.ListAdapter;
import com.example.svenscan.svenscan.repositories.IFavoriteRepository;

import java.util.List;

public class FavoriteListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorites);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SvenScanApplication app = (SvenScanApplication) getApplication();
        IFavoriteRepository favoriteWordRepository = app.getFavoriteWordRepository();
        initList(favoriteWordRepository.getFavorites());  // TODO: ouch on typecast.. Should be able to handle all lists?
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initList(List<String> list){
        ListView favorites = (ListView) findViewById(R.id.favorites);
        favorites.setAdapter(new ListAdapter(list ,(SvenScanApplication) getApplication()));
    }

    public void showWord(View view){
        view.setSelected(true);
        Intent intent = new Intent(this, ShowWordActivity.class);
        intent.putExtra("fav", (String)view.getTag());
        startActivity(intent);
    }
}
