package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;

public class AddNewWordActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    private IMediaRepository mediaRepository;
    private IWordRepository wordRepository;
    private String url;
    private String name;
    private String wordID;
    private String imagePath;
    private String soundPath;
    private int fieldsFilled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);
        SvenScanApplication app = (SvenScanApplication)getApplication();
        mediaRepository = app.getMediaRepository();
        wordRepository = app.getWordRepository();
        fieldsFilled = 0;
    }

    public void addNewWord(View view) {
        Word word = new Word(soundPath, imagePath, name, wordID);
        mediaRepository.addImage(new Uri.Builder().path(imagePath).build());
        mediaRepository.addSound(new Uri.Builder().path(soundPath).build());
        wordRepository.addWord(wordID, word);
    }

    private void getURL() {
        url = ((EditText)(findViewById(R.id.wordName))).getText().toString();

    }

    public void findImagePath(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), getIntFromID(R.integer.PICK_IMAGE));

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
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        v.clearFocus();
        if (actionId == getIntFromID(R.integer.name)) {
            getName();
            getWordID();
        }
        return false;
    }

    private int getIntFromID(@IntegerRes int id) {
         return getResources().getInteger(id);
    }
}
