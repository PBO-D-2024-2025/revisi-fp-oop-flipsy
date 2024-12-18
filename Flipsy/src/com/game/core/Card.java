package com.game.core;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Card extends JButton {
    private final ImageIcon frontImage;
    private final ImageIcon backImage;
    private boolean isFlipped = false;
    private boolean isMatched = false;

    public Card(ImageIcon frontImage, ImageIcon backImage) {
        this.frontImage = frontImage;
        this.backImage = backImage;

        // Set gambar belakang default
        this.setIcon(backImage);
        this.setBorder(null);
        this.setFocusPainted(false);

        // Tunggu hingga ukuran tombol diketahui
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setIcon(scaleImageIcon(isFlipped ? frontImage : backImage));
            }
        });
    }

    public void flip() {
        if (isMatched) return; // Jika sudah cocok, tidak bisa dibalik lagi
        isFlipped = !isFlipped;
        setIcon(scaleImageIcon(isFlipped ? frontImage : backImage)); // Tampilkan gambar depan/belakang
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setMatched() {
        this.isMatched = true;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public ImageIcon getFrontImage() {
        return frontImage;
    }

    /**
     * Skalakan gambar agar sesuai dengan ukuran tombol.
     */
    private ImageIcon scaleImageIcon(ImageIcon icon) {
        int width = getWidth();
        int height = getHeight();

        // Pastikan ukuran tombol valid sebelum menskalakan gambar
        if (width <= 0 || height <= 0) {
            return icon; // Jika ukuran belum valid, gunakan ukuran asli
        }

        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
