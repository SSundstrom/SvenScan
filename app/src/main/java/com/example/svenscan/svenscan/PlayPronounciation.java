package com.example.svenscan.svenscan;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class PlayPronounciation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_pronunciation);
        Button swosh = (Button)findViewById(R.id.swosh);

        final SoundManager soundManager = new SoundManager(this);
        swosh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundManager.setSound(R.raw.swosh1);
                soundManager.start();
            }
        });

        Button giggity = (Button)findViewById(R.id.giggity);
        giggity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundManager.setSound(R.raw.giggity);
                soundManager.start();
            }
        });

    }

}
