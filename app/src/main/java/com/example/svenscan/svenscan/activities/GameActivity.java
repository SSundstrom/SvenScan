package com.example.svenscan.svenscan.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.IFavoriteRepository;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;
import com.example.svenscan.svenscan.utils.SoundManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    final int NBR_OF_QUESTIONS = 5;
    final int NBR_OF_CHOICES = 4;

    int questionNbr = 1;
    int correctAnswers = 0;

    List<String> allWordsList;
    List<String> allCorrectWordsList;

    List<String> answers;

    List<String> choices;

    List<Button> buttons;
    Button selectedButton;

    IWordRepository wordManager;
    IFavoriteRepository favoriteWords;
    IMediaRepository mediaRepository;

    String correctWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SvenScanApplication app = (SvenScanApplication)getApplication();
        wordManager = app.getWordRepository();
        favoriteWords = app.getFavoriteWordRepository();
        mediaRepository = app.getMediaRepository();

        answers = new ArrayList<>();

        initAllWordsList();
        initButtons();
        newQuestion();
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

    private void initAllWordsList(){
        Map<String, Word> allWords = wordManager.getAllWords();
        allWordsList = new ArrayList<>(allWords.keySet());
        allCorrectWordsList = new ArrayList<>(allWords.keySet());
    }
    private void initButtons(){
        buttons = new ArrayList<>();

        buttons.add((Button)findViewById(R.id.button_1));
        buttons.add((Button)findViewById(R.id.button_2));
        buttons.add((Button)findViewById(R.id.button_3));
        buttons.add((Button)findViewById(R.id.button_4));
    }

    private void newQuestion(){
        if(questionNbr<=NBR_OF_QUESTIONS){
            choices = new ArrayList<>();
            enableNextButton(false);
            setRandomCorrectWord();
            setChoices();
            setView();
            questionNbr++;
        }else{
            //Visa poäng osv
            //WOHO
        }
    }

    private void enableNextButton(boolean b){
        findViewById(R.id.nextButton).setEnabled(b);
    }

    private void setRandomCorrectWord(){
        correctWord = randomCorrectWord();
        choices.add(correctWord);
    }
    private String randomCorrectWord(){
        Random randomGenerator = new Random();
        String s = allCorrectWordsList.get(randomGenerator.nextInt(allCorrectWordsList.size()));
        allCorrectWordsList.remove(s);
        return s;
    }

    private String randomWord(){
        Random randomGenerator = new Random();
        return allWordsList.get(randomGenerator.nextInt(allWordsList.size()));
    }
    private void setChoices(){
        for(int i = 1; i<NBR_OF_CHOICES; i++){
            choices.add(randomWord());
            System.out.println(choices.get(i));
        }
    }
    private void setView(){
        ImageView gameImage = (ImageView) findViewById(R.id.gamePicture);
        Word word = wordManager.getWordFromID(correctWord);
        mediaRepository.getImageUri(word.getImagePath(), gameImage::setImageURI);

        setRandomChoicesButtons();
        setTextLevel();
    }

    private void setRandomChoicesButtons(){
        Random randomGenerator = new Random();
        int i = 0;
        int rand;
        while(i<NBR_OF_CHOICES){
            rand = randomGenerator.nextInt(NBR_OF_CHOICES);
            if(rand<choices.size() && choices.get(rand) != null) {
                buttons.get(i).setText(choices.get(rand));
                buttons.get(i).setSelected(false);
                choices.remove(rand);
                i++;
            }
        }
    }
    private void setTextLevel(){
        TextView level = (TextView) findViewById(R.id.level);
        level.setText("" + questionNbr +"/" +NBR_OF_QUESTIONS);
    }

    public void selectButton(View view){
        for(int i = 0; i<buttons.size();i++){
            buttons.get(i).setSelected(false);
        }
        view.setSelected(true);
        selectedButton = (Button) view;
        enableNextButton(true);
    }

    public void handleAnswer(String answer){
        TextView resultView = (TextView) findViewById(R.id.resultView);
        String s;
        if(answer.equals(correctWord)){
            s= "rätt!";
            resultView.setTextColor(getResources().getColor(R.color.successGreen));
            correctAnswers++;
        }else{
            s="fel. Rätt svar var: " +correctWord;
            resultView.setTextColor(getResources().getColor(R.color.failRed));
        }
        String result = answer + " var " + s;
        answers.add(result);
        resultView.setText(result);
        resultView.setVisibility(View.VISIBLE);
    }

    public String presentAnswers(){
        return null;
    }

    public void nextQuestion(View view){
        String answer = selectedButton.getText().toString();
        handleAnswer(answer);
        newQuestion();
    }
}
