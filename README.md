[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/y7GIjDMM)

# Laporan Akhir Final Project OOP D

## 1. Informasi Umum
* **Nama Game**: Flipsy
* **Anggota Kelompok**:
    1. Jasmine Firdhousy Muslich - 5025231051
    2. Lailatul Annisa Fitriana - 5025231202
    3. Rosidah Darman - 5025231307
    4. Dzikrina Hidayani Martin - 5025231311
* **Tech Stack**: Java

## 2. Deskripsi Game

### 2.1 Konsep Game
* **Genre**: Puzzle/Card Matching Game
* **Gameplay/Rule**: Player akan melihat kartu-kartu yang terbalik di layar. Setiap kartu punya pasangan yang serupa. Pemain harus mencocokkan dua kartu yang mirip hingga kartu habis. Jika player membalik dua kartu yang berbeda, kartu akan kembali tertutup. Jika player membalik kartu yang sama, maka kartu akan tetap terbuka.
* **Objective**: Menemukan semua pasangan kartu dalam waktu sesingkat mungkin.
* **Single/Multi Player**: Single Player

### 2.2 Fitur Utama
1. Save/Load System: fitur untuk menyimpan/melanjutkan game di lain waktu.
2. Achievement System: pemain akan mendapat achievement untuk pencapaian tertentu (Contoh: tidak pernah membuka dua kartu yang tidak cocok, menyelesaikan game dalam waktu kurang dari 20 detik untuk level pertama, kurang dari 30 detik untuk level kedua, dan kurang dari 40 detik untuk level ketiga)
3. Timer: menghitung waktu yang dihabiskan player untuk menyelesaikan permainan.
4. Level Difficulty: tingkat kesulitan yang ditentukan dengan bertambahnya jumlah kartu seiring peningkatan level/kesulitan.

## 3. Implementasi Fitur Wajib

### 3.1 Save/Load System
* **Implementasi**: menyimpan dan memuat data pemain ketika terakhir kali bermain.
* **Konsep OOP**: menggunakan class `GameState` untuk merepresentasikan informasi save/load.
* **Penerapan SOLID**:
  1. Single Responsibility Principle: class `GameState` hanya berperan untuk menyimpan informasi status permainan 
* **Design Pattern yang Digunakan**:
* **Code Snippet**:
```
// class yang merepresentasikan state permainan
// mengimplementasikan interface Serializable untuk menyimpan status permainan dalam urutan byte yang nanti dimuat/dibaca kembali dengan cara dideserialisasi
public class GameState implements Serializable {
    private int score;
    private int level;
    
    // constructor dan getter/setter
    public GameState(int score, int level) {
        this.score = score;
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

// class yang punya single responsibility menyimpan GameState
// IOException adalah exception yang terjadi saat ada masalah I/O (contoh: kesalahan saat open, read, write file)
// try-with-resources akan otomatis menutup oos setelah menulis objek ke file sebagai byte stream
public class GameStateSaver {
    public void save(GameState state, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(state);
        }
    }
}

// class yang hanya bertanggung jawab memuat GameState
public class LoadGameState {
    public GameState load(String filename) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameState) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Class tidak ditemukan", e);
        }
    }
}
```

### 3.2 Achievement System
* **Jenis Achievement**:
    1. Champion: menyelesaikan seluruh level yang ada.
    2. Einstein: menyelesaikan level tanpa membuka kartu yang salah.
    3. Turbo: menyelesaikan game di bawah 20 detik untuk level pertama, di bawah 30 detik untuk level kedua, dan di bawah 40 detik untuk level ketiga
* **Konsep OOP**: menggunakan class `Achievement` yang di-inherit oleh class khusus untuk tiap jenis achievement.
* **Penerapan SOLID**:
  1. Single Responsibility Principle: Kelas achievement khusus hanya menangani satu achievement.
  2. Liskov Substitution Principle: Objek achievement dapat diganti oleh setiap class yang meng-inherit objek achievement tanpa mengubah logika program.
* **Design Pattern yang Digunakan**:
* **Code Snippet**:
```
public abstract class Achievement {
    public abstract boolean isAchieved(GameState state);
    public abstract String getAchievementName();
}

public class Champion extends Achievement {
    @Override
    public boolean isAchieved(GameState state) {
        return state.isAllLevelsCompleted();
    }

    @Override
    public String getAchievementName() {
        return "Champion! Kamu menamatkan game ini, selamat!";
    }
}

public class Einstein extends Achievement {
    @Override
    public boolean isAchieved(GameState state) {
        return state.getMistakes() == 0;
    }

    @Override
    public String getAchievementName() {
        return "Jenius seperti Einstein! Kamu tidak melakukan kesalahan sama sekali!";
    }
}

public class Turbo extends Achievement {
    @Override
    public boolean isAchieved(GameState state) {
        // Memeriksa apakah pemain memenuhi salah satu kondisi waktu untuk mendapatkan Turbo achievement
        return (state.getTimeElapsedLevel1() < 20) || 
               (state.getTimeElapsedLevel2() < 30) || 
               (state.getTimeElapsedLevel3() < 40);
    }

    @Override
    public String getAchievementName() {
        return "Turbo! Kamu sangat cepat!";
    }
}

```

## 4. Implementasi Fitur Lain

### 4.1 Fitur 1
* **Implementasi**:
* **Konsep OOP**:
* **Penerapan SOLID (Optional)**:
* **Implementasi**: Main Menu
* **Konsep OOP**: Encapsulation, Inheritance
* **Penerapan SOLID (Optional)**: SRP (hanya mengatur Main Menu), Interface segregation (menerapkan interface yang sesuai) 
* **Design Pattern yang Digunakan (Optional)**:
* **Code Snippet**:
* **Code Snippet**: 
```
[Code snippet here]
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
```

## 5. Screenshot dan Demo
* *Screenshot 1*: [Deskripsi]
* *Screenshot 2*: [Deskripsi]
* *Screenshot 1*: Turbo Achievement didapatkan jika menyelesaikan level sebelum waktu yang ditentukan
  ![Screenshot 2024-12-11 153734](https://github.com/user-attachments/assets/8ff987e8-d278-449b-8707-9a0013716b8b)
* *Screenshot 2*: Einstein  Achievement didapatkan jika menyelesaikan level dengan kesalahan >50
  ![Screenshot 2024-12-11 153742](https://github.com/user-attachments/assets/6087466a-c109-44ad-a0b3-baf552b1ce14)
* *Screenshot 3*: Champion  Achievement didapatkan jika telah menyelesaikan semua level (3 level)
  ![Screenshot 2024-12-11 154025](https://github.com/user-attachments/assets/4505bed1-cb07-476e-a7e0-274cbc3d4cd0)
* *Link Demo Video*: https://youtu.be/ipnDf5Oo4IY 

## 6. Panduan Instalasi dan Menjalankan Game
1. 1. git clone (link repository ini)
2. cd `Flipsy`
3. compile file java dengan `javac src/com/game/core/*.java src/com/game/ui/*.java src/com/game/services/*.java src/com/game/achievements/*.java`
4. cd `src`
5. jalankan file lewat GameWindow.java: `java com.game.ui.GameWindow `

## 7. Kendala dan Solusi
1. **Kendala 1**: Save failed karena path save kadang masih tidak ditemukan
    * Solusi: Memperjelas path menggunakan dynamic save  
2. **Kendala 2**: Dalam 1 class kadang sulit mematuhi semua SOLID principle 
    * Solusi: Lebih memperhatikan class dan merencanakan struktur directory 

## 8. Kesimpulan dan Pembelajaran
* **Kesimpulan**: OOP dan SOLID principle sangat berguna dalam pemrograman Java  
* **Pembelajaran**: Kita sebaiknya menerapkan OOP dan SOLID principle agar kode Java lebih clean, mudah terbaca, mudah jika ingin menambah fitur, dan mudah didebug/.
