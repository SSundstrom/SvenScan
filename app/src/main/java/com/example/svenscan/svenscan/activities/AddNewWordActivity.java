package com.example.svenscan.svenscan.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.desmond.squarecamera.CameraActivity;
import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;
import com.example.svenscan.svenscan.utils.RecordingManager;
import com.example.svenscan.svenscan.utils.SoundManager;
import com.example.svenscan.svenscan.utils.ocr.ImageProcessor;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;

public class AddNewWordActivity extends AppCompatActivity implements KeyEvent.Callback {
    private static final int REQUEST_CAMERA = 282;
    private IMediaRepository mediaRepository;
    private IWordRepository wordRepository;
    private Uri imageUri;
    private Uri soundUri;
    private String name;
    private String wordID;
    private String imageFileName;
    private String soundFileName;
    private RecordingManager recordingManager;
    private boolean mediaUploaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        askForPermissions();

        setContentView(R.layout.activity_add_new_word);
        SvenScanApplication app = (SvenScanApplication)getApplication();
        mediaRepository = app.getMediaRepository();
        wordRepository = app.getWordRepository();
        recordingManager = new RecordingManager(mediaRepository.getSoundDir());
        findViewById(R.id.okButton).setVisibility(View.VISIBLE);
        findViewById(R.id.add_word_loading_icon).setVisibility(View.INVISIBLE);

        setListeners();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setListeners() {
        EditText nameField = (EditText)findViewById(R.id.addWordTextField);
        nameField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (validateWord(v.getText().toString().toUpperCase())) {
                    getName();
                    getWordID();
                    hideSoftKeyboard();
                }
                return true;
            }
            return false;
        });
    }

    private boolean validateWord(String wordID) {
        TextInputLayout layout = (TextInputLayout)findViewById(R.id.addWordTextFieldLayout);
        boolean wordIsValid = true;
        if (wordRepository.containsWord(wordID)) {
            layout.setError(getString(R.string.add_new_word_exist));
            layout.setErrorEnabled(true);
            wordIsValid = false;
        } else if (wordID.length() == 0) {
            layout.setError(getString(R.string.add_new_word_no_word));
            layout.setErrorEnabled(true);
            wordIsValid = false;
        } else {
            layout.setErrorEnabled(false);
        }
        return wordIsValid;
    }


    private void hideSoftKeyboard() { // TODO: 2016-10-08 there is probably a better way to do this
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    public void showCamera(View view) {
        Intent startCustomCameraIntent = new Intent(this, CameraActivity.class);
        startActivityForResult(startCustomCameraIntent, REQUEST_CAMERA);
    }


    public void addNewWord(View view) {
        getWordID();
        Word word = new Word(soundFileName, imageFileName, name, wordID);

        showUploading();
        scaleImage();
        mediaRepository.addImage(imageUri, (success) -> onUploadDone(success, R.id.imageUploaded));
        mediaRepository.addSound(soundUri, (success) -> onUploadDone(success, R.id.soundUploaded));

        wordRepository.addWord(wordID, word);
        findViewById(R.id.okButton).setEnabled(false);
    }

    private void scaleImage() {
        ImageProcessor leptonica = new ImageProcessor();

        File targetImage = new File(mediaRepository.getImageDir(), imageFileName);

        try {
            targetImage = leptonica.scaleToReasonableSize(imageUri, getContentResolver(), targetImage);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        imageUri = Uri.fromFile(targetImage);
    }

    private void onUploadDone(boolean success, @IdRes int view) {
        if (success) {
            setViewToDone(view);
            showNewWord();
        } else {
            setViewToFail(view);
        }
    }

    private void showNewWord() {
        if (mediaUploaded) {
            Intent showWordIntent = new Intent(this, ShowWordActivity.class);
            showWordIntent.putExtra(getString(R.string.intent_extra_word), wordID);
            startActivity(showWordIntent);
        } else {
            mediaUploaded = true;
        }
    }

    private void showUploading() {
        findViewById(R.id.soundUploaded).setBackgroundResource(R.drawable.ic_cloud_upload);
        findViewById(R.id.imageUploaded).setBackgroundResource(R.drawable.ic_cloud_upload);
        findViewById(R.id.okButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.add_word_loading_icon).setVisibility(View.VISIBLE);
    }

    private void setViewToDone(@IdRes int id) {
        findViewById(id).setBackgroundResource(R.drawable.ic_cloud_done);
    }
    private void setViewToFail(@IdRes int id) {
        findViewById(id).setBackgroundResource(R.drawable.ic_cloud_failed_24dp);
    }

    public void playRecordedSound(View view) {
        if (recordingManager.hasRecording()) {
            SoundManager soundManager = new SoundManager(this);
            soundManager.start(recordingManager.getUri(), view);
        }
    }

    public void recordSound(View view) {
        if (name == null) return;
        recordingManager.toggleRecording(name, (uri, fileName) -> {
            soundUri = uri;
            soundFileName = fileName;
            checkOkButton();
        }, view);
    }

    private void checkOkButton() {
        View okButton = findViewById(R.id.okButton);

        if (isEverythingFilled()) {
            okButton.setEnabled(true);
            okButton.setBackgroundResource(R.color.successGreen);
        } else {
            okButton.setEnabled(false);
            okButton.setBackgroundResource(R.color.darkerGray);
        }
    }


    public void findImagePath(View view) {
        if (name == null) return;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), getIntFromID(R.integer.PICK_IMAGE));

    }

    private void getName() {
        name = ((EditText)(findViewById(R.id.addWordTextField))).getText().toString();
        name = name.trim();
        name = CorrectCaseLettering(name);
    }

    private void getWordID() {
        wordID = name; // TODO: WordID is atm just the name in uppercase
        wordID = wordID.toUpperCase();
    }

    private String CorrectCaseLettering(String input) { // TODO: 2016-10-08 Should be done but maybe not like this
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

    /**
     * Called when an image has been selected from the gallery
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == getIntFromID(R.integer.PICK_IMAGE) || requestCode == REQUEST_CAMERA) && resultCode == RESULT_OK) {
            imageUri = data.getData();

            // todo: this should probably be the word as well as some random/unique ID
            imageFileName = imageUri.getLastPathSegment();
            setPreviewImage();
            checkOkButton();
        }
    }

    private void setPreviewImage() {
        ImageView image = (ImageView)findViewById(R.id.add_new_word_image_preview);
        image.setImageURI(imageUri);
    }

    private int getIntFromID(@IntegerRes int id) {
         return getResources().getInteger(id);
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
