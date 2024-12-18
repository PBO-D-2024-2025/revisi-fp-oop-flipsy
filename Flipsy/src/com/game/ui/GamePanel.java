package com.game.ui;

import com.game.core.Board;
import com.game.core.Card;
import com.game.core.Game;
import com.game.core.GameTimer;
import com.game.services.GameState;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.*;


public class GamePanel implements Panel, GameTimer.TimerListener {
    private JPanel boardPanel;
    private JLabel timerLabel;
    private JLabel scoreLabel;    // Label untuk menampilkan skor
    private JLabel levelLabel;    // Label untuk menampilkan level
    private GameTimer gameTimer;
    private Thread timerUpdaterThread;
    private final Board board;
    private Game game;

    public GamePanel() {
        this.gameTimer = new GameTimer();
        this.board = new Board();
        this.game = new Game();
        this.gameTimer.setListener(this);  // GamePanel menjadi listener untuk GameTime

        // Mengatur callback ketika kartu cocok
        this.game.setOnCardMatch(() -> SwingUtilities.invokeLater(this::checkLevelCompletion));

        this.board.setGame(game);
    }

    @Override
    public JPanel createPanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        
        // Panel atas untuk informasi permainan
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 3, 10, 0)); 
        timerLabel = new JLabel("Time: 00:00");
        scoreLabel = new JLabel("Score: 0");
        levelLabel = new JLabel("Level: 1");

        topPanel.add(timerLabel);
        topPanel.add(scoreLabel);
        topPanel.add(levelLabel);
        gamePanel.add(topPanel);

        // Panel grid untuk kartu
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(4, 4, 5, 5)); // Grid dinamis akan diperbarui di board
        boardPanel.setPreferredSize(new Dimension(450, 600));
        gamePanel.add(boardPanel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        centerPanel.add(boardPanel);
        gamePanel.add(centerPanel);

        board.initializeCards(boardPanel);
        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(e -> saveGame());
        gamePanel.add(saveButton);

        JButton loadButton = new JButton("Load Game");
        loadButton.addActionListener(e -> loadGame());
        gamePanel.add(loadButton);
        return gamePanel;
    }

    public void startGame() {
        game.startGame();
        board.initializeCards(boardPanel);
        updateGameStatus();
        gameTimer.start();
        // startTimerUpdater();
    }

    private void updateGameStatus() {
        SwingUtilities.invokeLater(() -> {
            scoreLabel.setText("Score: " + game.getScore());
            levelLabel.setText("Level: " + game.getCurrentLevel());
        });
    }

    private void startTimerUpdater() {
        timerUpdaterThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                SwingUtilities.invokeLater(() -> {
                    int minutes = gameTimer.getTimeInSeconds() / 60;
                    int seconds = gameTimer.getTimeInSeconds() % 60;
                    timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
                    updateGameStatus();
                });
    
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        timerUpdaterThread.start();
    }
    

    // Implementasi dari TimerListener
    @Override
    public void onTimeUpdate(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, remainingSeconds));
    }
    

    private void checkLevelCompletion() {
        if (board.isLevelComplete()) {
            int elapsedSeconds = gameTimer.getTimeInSeconds();

            // Cek achievement
            List<String> unlockedAchievements = game.getAchievementManager()
                                                    .checkAchievements(game.getCurrentLevel(), elapsedSeconds);
            for (String achievement : unlockedAchievements) {
                JOptionPane.showMessageDialog(boardPanel, "Achievement Unlocked: " + achievement);
            }

            JOptionPane.showMessageDialog(boardPanel, "Level Complete!");
             game.nextLevel(); // Pindah ke level berikutnya
             game.getTimer().reset();
             board.initializeCards(boardPanel); // Reset kartu untuk level baru
             updateGameStatus();
        }
    }
    // private void checkLevelCompletion() {
    //     if (board.isLevelComplete() && game.getCurrentLevel() < game.getLevelCount()) {
    //         JOptionPane.showMessageDialog(boardPanel, "Level Complete!");
    //         game.nextLevel(); // Pindah ke level berikutnya
    //         board.initializeCards(boardPanel); // Reset kartu untuk level baru
    //         updateGameStatus();
    //     }
    // }
    

    private void handleCardMatch(Card firstSelected, Card secondSelected) {
        if (firstSelected.isMatched() && secondSelected.isMatched()) {
            System.out.println("[DEBUG] Matched card: Increasing score");
            game.increaseScore(100); // Tambah skor setiap kali kartu cocok
            updateGameStatus();
            SwingUtilities.invokeLater(() -> updateGameStatus());
        }
    }

    // Memastikan pemantauan kartu
    public void handleCardClick(Card card) {
        // Panggil logika kartu cocok di sini
        if (card.isMatched()) {
            handleCardMatch(card, card); // Kirim kartu yang cocok
        }
    }

    public void saveGame() {
        System.out.println("[DEBUG] Attempting to save game...");
        try (FileOutputStream fileOut = new FileOutputStream("savegame.dat");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
    
            GameState state = new GameState(game.getScore(), 
                                              game.getCurrentLevel(), 
                                              gameTimer.getTimeInSeconds());
            
            out.writeObject(state);
            System.out.println("[DEBUG] Game state saved: ");
            System.out.println("[DEBUG] Score = " + state.getScore());
            System.out.println("[DEBUG] Level = " + state.getCurrentLevel());
            System.out.println("[DEBUG] Elapsed Time = " + state.getElapsedTime());
    
            JOptionPane.showMessageDialog(boardPanel, "Game saved successfully!");
    
        } catch (IOException i) {
            System.err.println("[ERROR] Failed to save game.");
            i.printStackTrace();
            JOptionPane.showMessageDialog(boardPanel, "Failed to save game: " + i.getMessage());
        }
    }
    

    public void loadGame() {
        System.out.println("[DEBUG] Attempting to load game...");
        try (FileInputStream fileIn = new FileInputStream("savegame.dat");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
    
            GameState state = (GameState) in.readObject();
            
            System.out.println("[DEBUG] Game state loaded: ");
            System.out.println("[DEBUG] Score = " + state.getScore());
            System.out.println("[DEBUG] Level = " + state.getCurrentLevel());
            System.out.println("[DEBUG] Elapsed Time = " + state.getElapsedTime());
    
            // Restore game state
            game.setScore(state.getScore());
            game.setCurrentLevel(state.getCurrentLevel());
            gameTimer.setTime(state.getElapsedTime());
    
            JOptionPane.showMessageDialog(boardPanel, "Game loaded successfully!");
            updateGameStatus();
            board.initializeCards(boardPanel);
    
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[ERROR] Failed to load game.");
            e.printStackTrace();
            JOptionPane.showMessageDialog(boardPanel, "Failed to load game: " + e.getMessage());
        }
    }
    
}
