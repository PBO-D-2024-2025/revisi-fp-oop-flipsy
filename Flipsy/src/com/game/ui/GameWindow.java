package com.game.ui;

import java.awt.*;
import javax.swing.*;

public class GameWindow extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MainMenu mainMenu;
    private GamePanel gamePanel;

    public GameWindow() {
        // Setup window
        setTitle("Card Matching Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize other components
        mainMenu = new MainMenu(this);
        gamePanel = new GamePanel();

        // Add panels to mainPanel
        mainPanel.add(mainMenu.createPanel(), "MainMenu");
        mainPanel.add(gamePanel.createPanel(), "GamePanel");

        // Add mainPanel to JFrame
        add(mainPanel);

        // Show the main menu
        cardLayout.show(mainPanel, "MainMenu");

        setVisible(true);
    }

    public void showGamePanel() {
        cardLayout.show(mainPanel, "GamePanel");
    }

    public void startGame() {
        gamePanel.startGame();
        cardLayout.show(mainPanel, "GamePanel");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}