package com.example.svenscan.svenscan.utils;
import android.app.Activity;
import android.media.MediaPlayer;

public class SoundManager  {

    private boolean hasSound;

    private MediaPlayer mediaPlayer;
    private Activity activity;

    public SoundManager(Activity activity){
        hasSound = false;
        this.activity = activity;
    }

    public void setSound(int soundID){
        mediaPlayer = MediaPlayer.create(activity, soundID);
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
