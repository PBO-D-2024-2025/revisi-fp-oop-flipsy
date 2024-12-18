package com.game.achievements;

import com.game.core.Game;
import java.util.ArrayList;
import java.util.List;

public class AchievementManager {
    private final List<Achievement> achievements = new ArrayList<>();

    public AchievementManager(Game game) {
        // Tambahkan semua achievement
        achievements.add(new TurboAchievement());
        achievements.add(new ChampionAchievement(game)); // Berikan objek Game ke ChampionAchievement
        achievements.add(new EinsteinAchievement(game));
    }


    public List<String> checkAchievements(int level, int elapsedSeconds) {
        List<String> unlockedAchievements = new ArrayList<>();
        for (Achievement achievement : achievements) {
            if (achievement.checkCondition(level, elapsedSeconds)) {
                unlockedAchievements.add(achievement.getName());
            }
        }
        return unlockedAchievements;
    }
}
