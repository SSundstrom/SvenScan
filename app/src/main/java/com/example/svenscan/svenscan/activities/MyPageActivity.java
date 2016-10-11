package com.example.svenscan.svenscan.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;

public class MyPageActivity extends AppCompatActivity {

    private SvenScanApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        app = (SvenScanApplication)getApplication();

        showPoints();
        showLevel();

    }

    public void showPoints(){
        TextView myPoints = (TextView) findViewById(R.id.points_view2);
        myPoints.setText(app.getPoints().toString());
    }

    public void showLevel(){
        TextView myLevel = (TextView) findViewById(R.id.level_view);
        myLevel.setText(app.getPoints().showLevel());
    }




}
