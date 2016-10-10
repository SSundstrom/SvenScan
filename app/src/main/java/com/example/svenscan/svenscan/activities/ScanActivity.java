package com.example.svenscan.svenscan.activities;

import com.desmond.squarecamera.CameraActivity;
import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.fragments.ScanFragment;
import com.example.svenscan.svenscan.models.Points;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ScanActivity extends CameraActivity {

    private Points points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.squarecamera__CameraFullScreenTheme);
        setContentView(R.layout.activity_scan);
        getActionBar().hide();

        points = new Points();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView pointsView = (TextView) findViewById(R.id.points_view);
        pointsView.setText(points.toString());
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
                Intent i = new Intent(this, AddNewWordActivity.class);
                startActivity(i);
                return true;

            case R.id.action_help:
                // Intent i = new Intent(this, HelpActivity.class);
                // startActivity(i);
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
