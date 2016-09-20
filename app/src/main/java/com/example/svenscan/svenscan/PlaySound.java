package com.example.svenscan.svenscan;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlaySound extends Activity implements MediaPlayer.OnPreparedListener{

    @Override
    public void onPrepared(MediaPlayer mp) {

    }
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);

        Button one = (Button)this.findViewById(R.id.audio);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.swosh1);
        one.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                mp.start();
            }
        });

}

}
