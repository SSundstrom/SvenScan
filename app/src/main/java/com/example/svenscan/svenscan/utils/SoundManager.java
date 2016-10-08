package com.example.svenscan.svenscan.utils;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;

import com.example.svenscan.svenscan.R;

import java.io.IOException;

public class SoundManager  {

    private boolean hasSound;
    private boolean playing;
    private MediaPlayer mediaPlayer;
    private Activity activity;

    public SoundManager(Activity activity){
        hasSound = false;
        this.activity = activity;
    }

    private void setSound(Uri soundUri){
        mediaPlayer = MediaPlayer.create(activity, soundUri);
        mediaPlayer.setOnCompletionListener((v) -> {
            playing = false;
            mediaPlayer.reset();
        });
        hasSound = true;

    }

    private void start(){
        if (hasSound) {
            mediaPlayer.start();
            playing = true;
        }
    }

    public void start(Uri soundUri){
        if (!playing) {
            setSound(soundUri);
            mediaPlayer.setOnPreparedListener((v) -> mediaPlayer.start());
            mediaPlayer.prepareAsync();
        }
    }

    public void start(Uri soundUri, MediaPlayer.OnCompletionListener onCompletionListener) {
        if (!playing) {
            setSound(soundUri);
            mediaPlayer.setOnCompletionListener(onCompletionListener);
            mediaPlayer.setOnPreparedListener((v) -> mediaPlayer.start());

            try {
                mediaPlayer.start();
            } catch (IllegalStateException e) {
                Log.d("Mediaplayer", e.getMessage());
            }
        }
    }

}
