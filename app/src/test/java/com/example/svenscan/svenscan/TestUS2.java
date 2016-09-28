package com.example.svenscan.svenscan;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.svenscan.svenscan.activities.PlayPronunciationActivity;
import com.example.svenscan.svenscan.utils.SoundManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUS2 {

    private SoundManager soundManager;

    @Before
    public void setup(){
        PlayPronunciationActivity activity = mock(PlayPronunciationActivity.class);
        soundManager = new SoundManager(activity);
    }

    @Test
    public void testSetSound(){
        assertFalse(soundManager.hasSound());
        soundManager.setSound(R.raw.swosh1);
        assertTrue(soundManager.hasSound()
        );
    }

    @Test
    public void testStart(){

    }

}
