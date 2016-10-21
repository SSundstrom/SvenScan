package com.example.svenscan.svenscan.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game {

    private final int NBR_OF_CHOICES = 4;

    private int questionNbr = 1;
    private int correctAnswers = 0;

    private Map allWords;

    private List<String> allWordsList;
    private List<String> allCorrectWordsList;

    private List<String> answers;

    private List<String> choices;
    private String correctWord;

    public Game(Map allWords){
        this.allWords = allWords;
        answers = new ArrayList<>();
        allWordsList = new ArrayList<>();
        createAllWorsLists();
    }

    private void createAllWorsLists(){
        allWordsList = new ArrayList<>(allWords.keySet());
        allCorrectWordsList = new ArrayList<>(allWords.keySet());
    }


    public void newQuestion(){
            choices = new ArrayList<>();
            setRandomCorrectWord();
            setChoices();

    }

    private void setRandomCorrectWord(){
        correctWord = randomCorrectWord();
        choices.add(correctWord);
    }

    private String randomCorrectWord(){
        Random randomGenerator = new Random();
        String s = allCorrectWordsList.get(randomGenerator.nextInt(allCorrectWordsList.size()));
        allCorrectWordsList.remove(s);
        System.out.print(s);
        return s;
    }

    private void setChoices(){
        for(int i = 1; i<NBR_OF_CHOICES; i++){
            boolean newWordIsSet = false;
            while (!newWordIsSet){
                boolean isUnique = true;
                String randomword = randomWord();
                for(int j = 0; j<i; j++){
                    if(randomword.equals(choices.get(j))){
                        isUnique=false;
                    }
                }if(isUnique){
                    choices.add(i,randomword);
                    newWordIsSet = true;
                }
            }

        }
    }

    private String randomWord(){
        Random randomGenerator = new Random();
        return allWordsList.get(randomGenerator.nextInt(allWordsList.size()));
    }


    public int getNBR_OF_QUESTIONS(){
        final int NBR_OF_QUESTIONS = 5;
        return  NBR_OF_QUESTIONS;

    }

    public int getNBR_OF_CHOICES(){
        return this.NBR_OF_CHOICES;
    }

    public int getQuestionNbr(){
        return this.questionNbr;
    }

    public int getCorrectAnswers(){
        return this.correctAnswers;
    }

    public List<String> getAllWordsList(){
        return this.allWordsList;
    }

    public List<String> getAllCorrectWordsList(){
        return this.allCorrectWordsList;
    }

    public List<String> getAnswers(){
        return this.answers;
    }

    public List<String> getChoices(){
        return this.choices;
    }

    public String getCorrectWord(){
        return this.correctWord;
    }

    public void increaseCorrectAnswers(){
        this.correctAnswers = correctAnswers + 1;
    }

    public void setQuestionNbr(int nbr){
        this.questionNbr = nbr;
    }

    public void setCorrectAnswers(int nbr){
        this.correctAnswers = nbr;
    }

    public void clearAnswers(){
        answers = null;
    }

    public void increaseQuestionNbr(){
        this.questionNbr = questionNbr + 1;
    }

}
