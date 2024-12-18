## Update Progress
### Folder Structure Plan
```
src/
└── com/
    └── game/
        ├── core/                 # Komponen inti game
        │   ├── Card.java         # Representasi kartu
        │   ├── Board.java        # Logika board game
        │   ├── GameTimer.java    # Timer untuk game
        │   └── Game.java         # Aturan utama game
        │
        ├── ui/                   # Elemen GUI
        │   ├── GameWindow.java   # Jendela utama
        │   ├── MainMenu.java     # Menu utama
        │   ├── GamePanel.java    # Panel permainan
        │   └── Panel.java        # Interface untuk panel GUI
        │
        ├── achievements/         # Sistem pencapaian
        │   ├── Achievement.java  # Abstraksi pencapaian
        │   ├── AchievementEvent.java  # Event untuk pencapaian
        │   ├── AchievementManager.java # Mengelola pencapaian
        │   ├── ChampionAchievement.java  # Pencapaian Champion
        │   ├── EinsteinAchievement.java  # Pencapaian Einstein
        │   ├── TurboAchievement.java     # Pencapaian Turbo
        │   └── AchievementListener.java  # Interface Listener
        │
        ├── services/             # Layanan utilitas
        │   ├── SaveLoadService.java # Layanan untuk menyimpan/memuat game
        │   └── GameUtils.java    # Utilitas umum (opsional)
        │
        └── App.java              # Entry point aplikasi

```


### Test Game Window
1. Go to the project folder: `cd /Flipsy`
2. Compile the java file: `javac src/com/game/core/*.java src/com/game/ui/*.java`
3. Go to src `cd src`
4. Run the GameWindow.java:  `java com.game.ui.GameWindow`
5. Shoulda look like this:
   ![WhatsApp Image 2024-11-20 at 23 51 34_d218ae5f](https://github.com/user-attachments/assets/02201457-8010-4069-9261-0ffda2f36dd3)

   If the name is not filled but user already click 'Mulai Permainan':
   ![image](https://github.com/user-attachments/assets/81400cda-2688-4941-ad20-8afd89fac385)

   The board and timer, card has not been added
   ![image](https://github.com/user-attachments/assets/2f7faf37-cb4c-45de-b907-cb0ca7170857)

   card when not flip
   ![WhatsApp Image 2024-11-26 at 22 24 26_52b3ea1d](https://github.com/user-attachments/assets/6e73c2c1-228e-4455-b394-be513403400b)


   card when flipped
   ![WhatsApp Image 2024-11-26 at 22 25 16_b119b504](https://github.com/user-attachments/assets/211d2893-2317-46b2-9559-9265b6c5ee88)







