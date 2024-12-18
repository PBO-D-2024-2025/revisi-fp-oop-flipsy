package com.game.services;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private int score;
    private int currentLevel;
    private int elapsedTime;

    public GameState(int score, int currentLevel, int elapsedTime) {
        this.score = score;
        this.currentLevel = currentLevel;
        this.elapsedTime = elapsedTime;
    }

    public int getScore() {
        return score;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }
}
