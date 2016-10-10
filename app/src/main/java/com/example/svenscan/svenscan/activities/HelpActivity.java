package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class HelpActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(AppIntroFragment.newInstance("1. Skriv lapp", "", R.drawable.help_1 , Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance("2. Sätt upp lapp", "", R.drawable.help_2 , Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance("3. Scanna lapp", "", R.drawable.help_3 , Color.parseColor("#2196F3")));
        addSlide(AppIntroFragment.newInstance("4. Resultat!", "", R.drawable.help_4 , Color.parseColor("#2196F3")));

        showSkipButton(true);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        endHelp();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        endHelp();
    }

    public void endHelp(){
        SharedPreferences settings = getSharedPreferences("firstTimeUser", 0);
        boolean firstTimeUser = settings.getBoolean("firstTimeUser", true);

        if(firstTimeUser) {
            Intent i = new Intent(this, ScanActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            IMediaRepository mediaRepository = ((SvenScanApplication) getApplication()).getMediaRepository();
            mediaRepository.initialize(getFilesDir());
            startActivity(i);

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstTimeUser", false);
            editor.commit();
        }
        else{
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        }
    }
}
