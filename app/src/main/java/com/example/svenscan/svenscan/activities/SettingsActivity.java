package com.example.svenscan.svenscan.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.utils.Settings;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ((CheckBox)findViewById(R.id.edgefilter)).setChecked(Settings.getEdgeFilter());

    }

    public void toggleEdgeFilter(View view) {
        Settings.toggleEdgeFilter();
    }
}
