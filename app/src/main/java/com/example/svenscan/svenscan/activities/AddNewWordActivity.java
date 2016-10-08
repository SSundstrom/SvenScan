package com.example.svenscan.svenscan.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;
import com.example.svenscan.svenscan.utils.SoundManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

public class AddNewWordActivity extends AppCompatActivity implements KeyEvent.Callback {

    private IMediaRepository mediaRepository;
    private IWordRepository wordRepository;
    private Uri imageUri;
    private Uri soundUri;
    private String name;
    private String wordID;
    private String imageFileName;
    private String soundFileName;
    private boolean isRecording;
    private MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        askForPermissions();

        setContentView(R.layout.activity_add_new_word);
        SvenScanApplication app = (SvenScanApplication)getApplication();
        mediaRepository = app.getMediaRepository();
        wordRepository = app.getWordRepository();
        mediaRecorder = new MediaRecorder();

    }

    public void addNewWord(View view) {
        getWordID();
        System.out.println(imageUri);
        System.out.println(soundUri);
        System.out.println(wordID);
        Word word = new Word(soundFileName, imageFileName, name, wordID);
        System.out.println(word);
        mediaRepository.addImage(imageUri);
        mediaRepository.addSound(soundUri);
        wordRepository.addWord(wordID, word);
    }

    private void getImagePath() {
        imageFileName = imageUri.getLastPathSegment();

    }

    public void playRecordedSound(View view) {
        SoundManager soundManager = new SoundManager(this);
        soundManager.start(soundUri, (v) -> view.setBackgroundResource(R.drawable.ic_volume_max));
        ImageButton playSoundButton = (ImageButton)findViewById(R.id.playRecordedSound);
        playSoundButton.setBackgroundResource(R.drawable.sound_playing);
        AnimationDrawable frameAnimation = (AnimationDrawable)playSoundButton.getBackground();
        frameAnimation.start();

    }

    public void recordSound(View view) {
        if (!view.isClickable()) return;  // TODO: 2016-10-07 fix visual feedback

        if (!isRecording) {
            startRecording();
        } else {
            endRecording();
        }

        isRecording = !isRecording;

    }

    private void endRecording() {
        mediaRecorder.stop();
        mediaRecorder.reset();
        setRecordingButtonImage(R.drawable.ic_record);
        findViewById(R.id.playRecordedSound).setClickable(true);
        checkOkButton();
    }

    private void checkOkButton() {
        View okButton = findViewById(R.id.okButton);

        if (isEverythingFilled()) {
            okButton.setClickable(true);
            okButton.setBackgroundResource(R.color.success_green);
        } else {
            okButton.setClickable(false);
            okButton.setBackgroundResource(R.color.darkGray);
        }
    }

    private void startRecording() {
        findViewById(R.id.playRecordedSound).setClickable(false);
        soundFileName = name + ".3gp";

        soundUri = new Uri.Builder().path(mediaRepository.getSoundDir() +"/" + soundFileName).build(); // TODO: 2016-10-07 Should prob remove files that arnt used
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setMaxDuration(5000);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(soundUri.getPath());

        try {
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaRecorder.start();
        setRecordingButtonImage(R.drawable.ic_recording_now);
    }

    private void setRecordingButtonImage(@DrawableRes int id) {
        ImageButton recordButton = (ImageButton)findViewById(R.id.recordButton);
        recordButton.setBackgroundResource(id);
    }

    public void findImagePath(View view) {
        if (view.isClickable()) {
            Intent intent = new Intent();

            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), getIntFromID(R.integer.PICK_IMAGE));
        }

    }

    private void getName() {
        name = ((EditText)(findViewById(R.id.wordName))).getText().toString();
        name = makeStuffToCorrectCaseLettering(name);
    }

    private void getWordID() {
        wordID = ((EditText)(findViewById(R.id.wordName))).getText().toString(); // TODO: WordID is atm just the name in uppercase
        wordID = wordID.toUpperCase();
    }

    private String makeStuffToCorrectCaseLettering(String input) {
        String output;
        if (input.length() > 2) {
            input = input.toLowerCase();
            output = input.substring(0, 1).toUpperCase() + input.substring(1);
        } else if (input.length() == 0) {
            output = input;
        } else {
            output = input.toUpperCase();
        }
        return output;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == getIntFromID(R.integer.PICK_IMAGE) && resultCode == RESULT_OK) {
            imageUri = data.getData();
            getImagePath();
            checkOkButton();
        }
    }

    private int getIntFromID(@IntegerRes int id) {
         return getResources().getInteger(id);
    }

    private void setMediaClickable(Boolean status) {
        findViewById(R.id.recordButton).setClickable(status);
        findViewById(R.id.findImageButton).setClickable(status);
    }

    private void showWordToShortNotification(boolean value) {
        if (value) findViewById(R.id.add_new_word_error_text).setVisibility(View.VISIBLE);
        else findViewById(R.id.add_new_word_error_text).setVisibility(View.INVISIBLE);
    }

    private boolean isEverythingFilled() {
        return soundFileName != null &&
                imageFileName != null;
    }


    private void askForPermissions() {
        MultiplePermissionsListener permissions =
                DialogOnAnyDeniedMultiplePermissionsListener.Builder
                        .withContext(this)
                        .withTitle("Microphone and storage permission")
                        .withMessage("Both microphone and storage permissions are needed to submit words")
                        .withButtonText(android.R.string.ok)
                        .build();
        Dexter.checkPermissions(permissions, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
}
