package com.example.svenscan.svenscan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playSound(View view){
        Intent tmp = new Intent(this, PlayPronunciation.class);
        startActivity(tmp);
    }
    public void recordPronunciation(View view){
        Intent tmp = new Intent(this, RecordPronunciation.class);
        startActivity(tmp);
    }

}
