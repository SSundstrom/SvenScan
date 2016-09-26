package com.example.svenscan.svenscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.svenscan.svenscan.favorite.FavoriteListActivity;
import com.example.svenscan.svenscan.favorite.FavoriteWords;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private FavoriteWords favoriteWords = new FavoriteWords();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void favoriteWord(View view){
        //TODO should be Label later on and not EditText
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String word = editText.getText().toString();
        favoriteWords.addFavorite(word);

    }

    public void showFavoriteWords(View view){
        Intent intent = new Intent(this, FavoriteListActivity.class);
        intent.putExtra("favoriteWords", favoriteWords.getFavorites());
        startActivity(intent);
    }
}













