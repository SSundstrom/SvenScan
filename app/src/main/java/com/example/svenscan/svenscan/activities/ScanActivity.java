package com.example.svenscan.svenscan.activities;

import com.desmond.squarecamera.CameraActivity;
import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.fragments.ScanFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class ScanActivity extends CameraActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.squarecamera__CameraFullScreenTheme);
        setContentView(R.layout.activity_scan);

        getActionBar().hide();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ScanFragment.newInstance(), ScanFragment.TAG)
                .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent i1 = new Intent(this, AddNewWordActivity.class);
                startActivity(i1);
                return true;

            case R.id.action_help:
                Intent i2 = new Intent(this, HelpActivity.class);
                startActivity(i2);
                return true;

            case R.id.action_game:
                Intent i3 = new Intent(this, GameActivity.class);
                startActivity(i3);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
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