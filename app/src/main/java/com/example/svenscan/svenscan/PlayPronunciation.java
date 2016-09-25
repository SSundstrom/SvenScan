package com.example.svenscan.svenscan;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PlayPronunciation extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private MediaPlayer swoshPlayer;
    private MediaPlayer giggityPlayer;
    final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SvenScan";
    private List<String> myList;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_pronunciation);
        final Button swosh = (Button)findViewById(R.id.swosh);
        mediaPlayer = new MediaPlayer();
        swoshPlayer = MediaPlayer.create(this ,R.raw.swosh1);
        giggityPlayer = MediaPlayer.create(this, R.raw.giggity);
        Button giggity = (Button)findViewById(R.id.giggity);

        ListView listView = (ListView)findViewById(R.id.listOfFiles);
        myList = new ArrayList<String>();

        file = new File( PATH );
        File list[] = file.listFiles();

        for( int i=0; i< list.length; i++)
        {
            myList.add( list[i].getName() );
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, myList);
        listView.setAdapter(adapter);

        swosh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swoshPlayer.start();
            }
        });

        giggity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giggityPlayer.start();
            }
        });
    }

}
