package com.example.svenscan.svenscan.utils;
import android.app.Activity;
import android.media.MediaPlayer;

import com.example.svenscan.svenscan.R;

public class SoundManager  {

    private boolean hasSound;

    private MediaPlayer mediaPlayer;
    private Activity activity;

    public SoundManager(Activity activity){
        hasSound = false;
        this.activity = activity;
    }

    public void setSound(int soundID){
//        mediaPlayer = MediaPlayer.create(activity, soundID);
        mediaPlayer = MediaPlayer.create(activity, R.raw.giggity); // TODO: 2016-10-04 shits broken.. We need to make it work with external audio
        hasSound = true;
    }

    public void start(){
        if (hasSound) {
            mediaPlayer.start();
        }
    }

    public void start(int soundID){
        setSound(soundID);
        start();
    }

    public boolean hasSound(){
        return hasSound;
    }
}
