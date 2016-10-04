package com.example.svenscan.svenscan.utils;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;

import com.example.svenscan.svenscan.R;

import java.io.IOException;

public class SoundManager  {

    private boolean hasSound;

    private MediaPlayer mediaPlayer;
    private Activity activity;

    public SoundManager(Activity activity){
        hasSound = false;
        this.activity = activity;
    }

    private void setSound(Uri soundUri){

        mediaPlayer = MediaPlayer.create(activity, soundUri);
        hasSound = true;
    }

    private void start(){
        if (hasSound) {
            mediaPlayer.start();
        }
    }

    public void start(Uri soundUri){
        setSound(soundUri);
        start();
    }

    public boolean hasSound(){
        return hasSound;
    }
}
