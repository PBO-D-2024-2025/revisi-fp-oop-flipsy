package com.game.achievements;

import com.game.core.Game;

public class EinsteinAchievement extends Achievement {
    private final Game game;

    public EinsteinAchievement(Game game) {
        super("Einstein");
        this.game = game;
    }

    @Override
    public boolean checkCondition(int level, int elapsedSeconds) {
        // Pemain mendapatkan achievement jika tidak pernah membuat kesalahan
        return game.getMistakes() <= 50;
    }
}
