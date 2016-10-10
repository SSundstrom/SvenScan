package com.example.svenscan.svenscan.utils;

import android.media.MediaRecorder;
import android.net.Uri;
import android.view.View;

import com.example.svenscan.svenscan.R;

import java.io.File;

public class RecordingManager implements IRecordingManager{

    private static final String FILE_FORMAT = ".3gp";

    private MediaRecorder mediaRecorder;
    private boolean hasRecording;
    private File soundDir;
    private File soundFile;
    private boolean isRecording;

    public RecordingManager(File soundDir) {
        mediaRecorder = new MediaRecorder();
        this.soundDir = soundDir;
    }

    private void startRecording(String fileName) {
        if (soundFile != null && soundFile.delete()) {
            System.out.println("last recorded soundfile deleted");
        }
        hasRecording = false;

        fileName = fileName + FILE_FORMAT;
        soundFile = new File(soundDir +"/"+ fileName);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setMaxDuration(5000);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(soundFile.getAbsolutePath());

        try {
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
        isRecording = true;
    }

    private void endRecording() {
        mediaRecorder.stop();
        mediaRecorder.reset();
        isRecording = false;
        hasRecording = true;
    }

    @Override
    public void toggleRecording(String fileName, IOnRecordingComplete handler) {
        if (fileName == null) {
            System.out.println("No file name");
            return;
        }
        if (!isRecording) {
            startRecording(fileName);
        } else {
            endRecording();
            handler.recordingDone(Uri.fromFile(soundFile), fileName + FILE_FORMAT);
        }
    }

    @Override
    public void toggleRecording(String fileName, IOnRecordingComplete handler, View view) {
        toggleRecording(fileName, handler);
        view.setBackgroundResource(isRecording ? R.drawable.ic_recording_now : R.drawable.ic_record);
    }

    @Override
    public Uri getUri() {
        return Uri.fromFile(soundFile);
    }

    @Override
    public boolean hasRecording() {
        return hasRecording;
    }
}
