package com.game.core;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class Board {
    private final ArrayList<Card> cards;
    private Card firstSelected;
    private Card secondSelected;
    private boolean isProcessing;
    private Game game;

    public Board() {
        this.cards = new ArrayList<>();
        this.isProcessing = false;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    

    public void initializeCards(JPanel boardPanel) {
        cards.clear();
        boardPanel.removeAll();

        ArrayList<ImageIcon> images = loadCardImages();
        // images = new ArrayList<>(images.subList(0, 8));
        images.addAll(images); // Duplikasi untuk pasangan kartu
        Collections.shuffle(images);
        images = new ArrayList<>(images.subList(0, 16)); // Batasi hingga 16 kartu

        ImageIcon backImage = new ImageIcon(getClass().getClassLoader().getResource("img/Backcard.jpg"));

        for (ImageIcon image : images) {
            Card card = new Card(image, backImage);
            card.setPreferredSize(new java.awt.Dimension(100, 150));
            card.addActionListener(e -> handleCardClick(card));
            cards.add(card);
            boardPanel.add(card);
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private ArrayList<ImageIcon> loadCardImages() {
        ArrayList<ImageIcon> images = new ArrayList<>();
        String[] imageNames = {
            "the-emperor.png", "the-hierophant.png", "the-chariot.png",
            "strength.png", "the-hermit.png", "wheel-of-fortune.png",
            "justice.png", "death.png"
        };

        for (String name : imageNames) {
            ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("img/" + name));
            if (image != null) {
                images.add(image);
            }
        }
        return images;
    }

    private void handleCardClick(Card card) {
        if (isProcessing || card.isFlipped() || card.isMatched()) return;

        card.flip();
        if (firstSelected == null) {
            firstSelected = card;
        } else {
            secondSelected = card;
            checkMatch();
        }
    }

    private void checkMatch() {
        isProcessing = true;

        if (firstSelected.getFrontImage().equals(secondSelected.getFrontImage())) {
            firstSelected.setMatched();
            secondSelected.setMatched();
            if (game != null) {
                game.increaseScore(100);
            }
            resetSelection();
        } else {
            Timer timer = new Timer(500, e -> {
                firstSelected.flip();
                secondSelected.flip();
                resetSelection();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void resetSelection() {
        firstSelected = null;
        secondSelected = null;
        isProcessing = false;
    }

    public boolean isLevelComplete() {
        return cards.stream().allMatch(Card::isMatched);
    }
}
