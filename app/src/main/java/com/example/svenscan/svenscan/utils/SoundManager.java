package com.example.svenscan.svenscan.utils;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;

import com.example.svenscan.svenscan.R;

import java.io.IOException;

public class SoundManager  {

    private boolean playing;
    private MediaPlayer mediaPlayer;
    private Activity activity;

    public SoundManager(Activity activity){
        this.activity = activity;
    }

    private void setSound(Uri soundUri){
        mediaPlayer = MediaPlayer.create(activity, soundUri);
        mediaPlayer.setOnCompletionListener((v) -> {
            playing = false;
            mediaPlayer.reset();
        });

    }

    public void start(Uri soundUri){
        if (!playing) {
            setSound(soundUri);
            mediaPlayer.setOnPreparedListener((v) -> mediaPlayer.start());
            mediaPlayer.prepareAsync();
        }
    }

    public void start(Uri soundUri, View view) {
        if (!playing) {
            setSound(soundUri);
            mediaPlayer.setOnCompletionListener((v) -> setAnimation(view, false));
            mediaPlayer.setOnPreparedListener((v) -> mediaPlayer.start());
            try {
                mediaPlayer.start();
                setAnimation(view, true);
            } catch (IllegalStateException e) {
                Log.d("Mediaplayer", e.getMessage());
            }
        }
    }

    private void setAnimation(View view, boolean value) {
        if (value) {
            view.setBackgroundResource(R.drawable.sound_playing);
            AnimationDrawable frameAnimation = (AnimationDrawable)view.getBackground();
            frameAnimation.start();
        } else {
            view.setBackgroundResource(R.drawable.ic_volume_max);
        }

    }

}
