package com.game.achievements;

import com.game.core.Game;

public class ChampionAchievement extends Achievement {
    private final Game game;

    public ChampionAchievement(Game game) {
        super("Champion");
        this.game = game;
    }

    @Override
    public boolean checkCondition(int level, int elapsedSeconds) {
        // Pemain mendapatkan achievement jika telah menyelesaikan semua level
        return level == game.getLevelCount();
    }
}
