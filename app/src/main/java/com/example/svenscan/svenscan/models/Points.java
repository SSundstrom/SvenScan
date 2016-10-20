package com.example.svenscan.svenscan.models;

public class Points {

    private int points;
    private int level;
    private int levelProgress;

    public Points(int points){
        this.points = points;
        while (leveledUp()) {
            increaseLevel();
        }
    }


    public int getLevel() {
        return level;
    }

    private void increaseLevel(){
        level = level + 1;
    }

    public int getPoints(){
        return this.points;
    }

    /**
     * @return percentage to next level
     */
    public int getLevelProgress() {
        return levelProgress;
    }

    public void earnPoints(int value){
        this.points = points + value;
        if (leveledUp()) {
            increaseLevel();
        }
        levelProgress = (points - getLastLevelLimit()) * 100 / (getNextLevelTarget() - getLastLevelLimit());
    }


    private boolean leveledUp(){
        return points > getNextLevelTarget();
    }

    private int getNextLevelTarget() {
        return ((100 * level)-1);
    }

    private int getLastLevelLimit() {
        return ((100 * (level-1))-1);
    }

}
