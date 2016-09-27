package com.example.svenscan.svenscan;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PlayPronunciation extends AppCompatActivity {

    private SoundManager soundManager;
    final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SvenScan";
    private List<String> myList;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_pronunciation);
        soundManager = new SoundManager(this);

        ListView listView = (ListView)findViewById(R.id.listOfFiles);
        myList = new ArrayList<String>();

        file = new File( PATH );
        if(file.length() != 0) {
            File list[] = file.listFiles();
            for (int i = 0; i < list.length; i++) {
                myList.add(list[i].getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, myList);
            listView.setAdapter(adapter);
        }
        Button swosh = (Button)findViewById(R.id.swosh);
        swosh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundManager.setSound(R.raw.swosh1);
                soundManager.start();
            }
        });

        Button giggity = (Button)findViewById(R.id.giggity);
        giggity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundManager.setSound(R.raw.giggity);
                soundManager.start();
            }
        });

    }
}