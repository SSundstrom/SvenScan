package com.example.svenscan.svenscan.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.SvenScanApplication;
import com.example.svenscan.svenscan.fragments.GameFragment;
import com.example.svenscan.svenscan.models.Game;
import com.example.svenscan.svenscan.models.Word;
import com.example.svenscan.svenscan.repositories.IFavoriteRepository;
import com.example.svenscan.svenscan.repositories.IMediaRepository;
import com.example.svenscan.svenscan.repositories.IWordRepository;
import com.example.svenscan.svenscan.utils.IProgressManager;
import com.example.svenscan.svenscan.utils.SoundManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    private Game game;

    List<Button> buttons;
    Button selectedButton;

    IWordRepository wordManager;
    IFavoriteRepository favoriteWords;
    IMediaRepository mediaRepository;
    IProgressManager progressManager;



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
        progressManager = app.getProgressManager();


        initNewGame();
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

    private void initNewGame(){
        Map<String, Word> allWords = wordManager.getAllWords();
        game = new Game(allWords);

    }
    private void initButtons(){
        buttons = new ArrayList<>();

        buttons.add((Button)findViewById(R.id.button_1));
        buttons.add((Button)findViewById(R.id.button_2));
        buttons.add((Button)findViewById(R.id.button_3));
        buttons.add((Button)findViewById(R.id.button_4));
    }

    private void newQuestion(){
        if(game.getQuestionNbr()<= game.getNBR_OF_QUESTIONS()){
            enableNextButton(false);
            setView();
            game.newQuestion();

        }else{
            gameFinished();
        }
    }

    private void enableNextButton(boolean b){
        View nextButton = findViewById(R.id.nextButton);
        nextButton.setEnabled(b);
        nextButton.setBackgroundResource(b ? R.drawable.ic_arrow_forward_green : R.drawable.ic_arrow_forward_gray);
    }







    public void playRecordedSound(View view) {
        mediaRepository.getSoundUri(wordManager.getWordFromID(game.getCorrectWord()).getSoundPath(), (uri) -> new SoundManager(this).start(uri, view));
    }

    private void setView(){
        View gameLoading = findViewById(R.id.game_loading_image);
        gameLoading.setVisibility(View.VISIBLE);
        ImageView gameImage = (ImageView) findViewById(R.id.gamePicture);
        gameImage.setVisibility(View.INVISIBLE);
        Word word = wordManager.getWordFromID(game.getCorrectWord());
        mediaRepository.getImageUri(word.getImagePath(), (imageUri) -> {
            gameImage.setImageURI(imageUri);
            gameImage.setVisibility(View.VISIBLE);
            gameLoading.setVisibility(View.INVISIBLE);
        });

        setRandomChoicesButtons();
        setTextLevel();
    }

    private void setRandomChoicesButtons(){
        Random randomGenerator = new Random();
        int i = 0;
        int rand;
        while(i<game.getNBR_OF_CHOICES()){
            rand = randomGenerator.nextInt(game.getNBR_OF_CHOICES());

            if(rand<game.getChoices().size() && game.getChoices().get(rand) != null) {
                String word = game.getChoices().get(rand);
                handleWordSize(i,word);
                buttons.get(i).setText(word);
                buttons.get(i).setSelected(false);
                game.getChoices().remove(rand);
                i++;
            }
        }
    }
    private void handleWordSize(int i, String word){
        if(word.length()>8){
            buttons.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.button_text_size_smaller));
        }else{
            buttons.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.button_text_size));
        }
    }

    private void setTextLevel(){
        TextView level = (TextView) findViewById(R.id.level);
        level.setText("" + game.getQuestionNbr() +"/" + game.getNBR_OF_QUESTIONS());
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
        if(answer.equals(game.getCorrectWord())){
            s= "rätt!";
            resultView.setTextColor(getResources().getColor(R.color.successGreen));
            game.increaseCorrectAnswers();
        }else{
            s="fel. Rätt svar var: " + game.getCorrectWord();
            resultView.setTextColor(getResources().getColor(R.color.failRed));
        }
        String result = answer + " var " + s;
        game.getAnswers().add(result);
        resultView.setText(result);
        resultView.setVisibility(View.VISIBLE);
    }
    

    public void nextQuestion(View view){
        String answer = selectedButton.getText().toString();
        handleAnswer(answer);
        newQuestion();
    }

    public void gameFinished(){
        givePlayerProgress();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_game, GameFragment.newInstance(), GameFragment.TAG)
                .commit();
    }

    private void givePlayerProgress() {
        for (int i = 0; i < game.getCorrectAnswers(); i++) {
            progressManager.earnPoints("game");
        }
    }

    public void endGame(View view){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void playAgain(View view){
        getSupportFragmentManager().beginTransaction().
                remove(getSupportFragmentManager().findFragmentByTag(GameFragment.TAG)).commit();
        resetGame();
    }

    private void resetGame(){
        TextView resultView = (TextView) findViewById(R.id.resultView);
        resultView.setVisibility(View.INVISIBLE);
        game.setQuestionNbr(1);
        game.setCorrectAnswers(0);
        game.clearAnswers();

        initNewGame();
        newQuestion();
    }

    public int getScore(){
        return game.getCorrectAnswers();
    }

    public int getNBR_OF_QUESTIONS(){
        return game.getNBR_OF_QUESTIONS();
    }

}
