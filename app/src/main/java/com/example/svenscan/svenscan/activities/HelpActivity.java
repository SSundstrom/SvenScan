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
import android.view.MenuItem;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class HelpActivity extends AppIntro2 {
    private SharedPreferences settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("", "", R.drawable.help_1 , getResources().getColor(R.color.help1)));
        addSlide(AppIntroFragment.newInstance("", "", R.drawable.help_2 , getResources().getColor(R.color.help2)));
        addSlide(AppIntroFragment.newInstance("", "", R.drawable.help_3 , getResources().getColor(R.color.help3)));
        addSlide(AppIntroFragment.newInstance("", "", R.drawable.help_4 , getResources().getColor(R.color.help4)));

        showSkipButton(true);
        setProgressButtonEnabled(true);
        setFlowAnimation();

        settings = getSharedPreferences("firstTimeUser", 0);

        if (!isFirstTimeUser()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
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

    private boolean isFirstTimeUser() {
        return settings.getBoolean("firstTimeUser", true);
    }

    public void endHelp(){
        if(isFirstTimeUser()) {
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
