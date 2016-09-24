package com.example.svenscan.svenscan;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class PlayPronunciation extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private MediaPlayer swoshPlayer;
    private MediaPlayer giggityPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_pronunciation);
        final Button swosh = (Button)findViewById(R.id.swosh);
        mediaPlayer = new MediaPlayer();
        swoshPlayer = MediaPlayer.create(this ,R.raw.swosh1);
        giggityPlayer = MediaPlayer.create(this, R.raw.giggity);
        Button giggity = (Button)findViewById(R.id.giggity);
        swosh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swoshPlayer.start();
            }
        });

        giggity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giggityPlayer.start();
            }
        });
    }

}
