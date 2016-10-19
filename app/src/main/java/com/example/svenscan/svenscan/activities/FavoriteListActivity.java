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

    List<String> favorites;
    View selectedView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorites);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SvenScanApplication app = (SvenScanApplication) getApplication();
        IFavoriteRepository favoriteWordRepository = app.getFavoriteWordRepository();
        favorites = favoriteWordRepository.getFavorites();
        initList();

        setTitle(R.string.favorite_activity);
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

    private void initList(){
        ListView list = (ListView) findViewById(R.id.favorites);
        ListAdapter adapter =  new ListAdapter(favorites,(SvenScanApplication) getApplication());
        list.setAdapter(adapter);
    }
    @Override
    protected void onResume(){
        super.onResume();
        initList();
        if(selectedView!=null){
            selectedView.setSelected(false);
            selectedView = null;
        }
    }

    public void showWord(View view){
        selectedView = view;
        view.setSelected(true);
        Intent intent = new Intent(this, ShowWordActivity.class);
        intent.putExtra(getString(R.string.special_parent), getString(R.string.favorite_activity));
        intent.putExtra(getString(R.string.intent_extra_word), (String)view.getTag());
        startActivity(intent);
    }
}
