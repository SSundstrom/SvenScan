package com.example.svenscan.svenscan.activities;

import com.desmond.squarecamera.CameraActivity;
import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.fragments.ScanFragment;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ScanActivity extends CameraActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ScanFragment.newInstance(), ScanFragment.TAG)
                .commit();
        }
    }

    @Override
    public void returnPhotoUri(Uri uri) {
        Intent intent = new Intent(this,ShowWordActivity.class);
        intent.putExtra("picture", uri.getPath());
        startActivity(intent);

    }

    public void toFavoriteWords(View view){
        Intent intent = new Intent(this, FavoriteListActivity.class);
        startActivity(intent);
    }
}
