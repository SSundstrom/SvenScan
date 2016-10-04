package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.adapters.ArrayListAdapter;
import com.example.svenscan.svenscan.repositories.IFavoriteRepository;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        SvenScanApplication app = (SvenScanApplication) getApplication();
        IFavoriteRepository favoriteWordRepository = app.getFavoriteWordRepository();
        initList(favoriteWordRepository.getFavorites());  // TODO: ouch on typecast.. Should be able to handle all lists?


    }
    private void initList(List<String> list){
        ListView favorites = (ListView) findViewById(R.id.favorites);
        favorites.setAdapter(new ArrayListAdapter((ArrayList<String>)list ,(SvenScanApplication) getApplication()));
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
        Intent intent = new Intent(this, ShowScannedWordActivity.class);
        System.out.println(view.getTag());
        intent.putExtra("fav", (String)view.getTag());
        startActivity(intent);
    }
}
