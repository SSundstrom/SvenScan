package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.adapters.ArrayListAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Intent intent = getIntent();
        ArrayList<String> favoriteList = (ArrayList<String>) intent.getSerializableExtra("favoriteWords");
        initList(favoriteList);

    }
    private void initList(ArrayList<String> list){
        ListView favorites = (ListView) findViewById(R.id.favorites);
        favorites.setAdapter(new ArrayListAdapter(list,(SvenScanApplication) getApplication()));
        //TODO: implement so that you can click on every object instead of "more" button
        /*
        favorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.print("Clicked" + position +"");
            }
        });
        */
    }
    public void showWord(View view){
        Intent intent = new Intent(this, ShowFavoriteWordActivity.class);
        intent.putExtra("word", (String) view.getTag());
        startActivity(intent);
    }
}
