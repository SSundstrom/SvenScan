package com.example.svenscan.svenscan;

import android.media.MediaRecorder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecordPronunciation extends AppCompatActivity {
    final String filePath = MediaStore.Audio.Media.DATA;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_pronunciation);
        final MediaRecorder mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setMaxDuration(5000);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(filePath);
        try {
            mediaRecorder.prepare();
        } catch (Exception e){
            e.printStackTrace();
        }
        final Button recordButton = (Button)findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!isRecording) {
                    mediaRecorder.start();
                    isRecording = true;
                } else {
                    mediaRecorder.stop();
                    isRecording = false;
                }

            }
        });
    }


}
