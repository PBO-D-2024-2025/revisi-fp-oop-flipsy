package com.game.achievements;

public abstract class Achievement {
    private final String name;

    public Achievement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean checkCondition(int level, int elapsedSeconds);
}
