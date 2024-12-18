package com.game.achievements;

public class TurboAchievement extends Achievement {

    public TurboAchievement() {
        super("Turbo");
    }

    @Override
    public boolean checkCondition(int level, int elapsedSeconds) {
        if (level == 1) return elapsedSeconds < 50;
        if (level == 2) return elapsedSeconds < 60;
        if (level == 3) return elapsedSeconds < 70;
        return false;
    }
}
