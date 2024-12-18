package com.game.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu implements Panel {
    private GameWindow gameWindow;
    private JTextField playerNameField;

    public MainMenu(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    @Override
    public JPanel createPanel() {
        // Panel utama dengan latar belakang gradasi
        JPanel mainMenu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(85, 149, 255), getWidth(), getHeight(), new Color(153, 50, 204));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainMenu.setLayout(new BorderLayout());

        // Panel bagian atas untuk logo dan judul
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        // Logo game
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon(getClass().getClassLoader().getResource("img/logo.png"));
        Image scaledLogo = logoIcon.getImage().getScaledInstance(240, 240, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Judul game
        JLabel titleLabel = new JLabel("Flipsy Adventure");
        titleLabel.setFont(new Font("Luckiest Guy", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(logoLabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(30));

        // Panel bagian tengah untuk input nama
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Masukkan Nama Anda:");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        playerNameField = new JTextField(15);
        playerNameField.setMaximumSize(new Dimension(300, 40));
        playerNameField.setFont(new Font("Serif", Font.PLAIN, 18));
        playerNameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(nameLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(playerNameField);

        // Panel bagian bawah untuk tombol
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new GridLayout(1, 2, 20, 0));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        startButton.setBackground(new Color(233, 107, 222));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.addActionListener((ActionEvent e) -> {
            String playerName = playerNameField.getText().trim();
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Masukkan nama Anda!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            } else {
                gameWindow.startGame(); 
                gameWindow.showGamePanel(); 
            }
        });

        JButton exitButton = new JButton("Quit");
        exitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        exitButton.setBackground(new Color(233, 107, 222));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        bottomPanel.add(startButton);
        bottomPanel.add(exitButton);

        // Tambahkan semua panel ke dalam mainMenu
        mainMenu.add(topPanel, BorderLayout.NORTH);
        mainMenu.add(centerPanel, BorderLayout.CENTER);
        mainMenu.add(bottomPanel, BorderLayout.SOUTH);

        return mainMenu;
    }

    
}
