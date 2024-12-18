package com.game.core;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private int timeInSeconds = 0; 
    private Timer timer;       
    private TimerListener listener; 
    private boolean isRunning; 

    public GameTimer() {
        this.timeInSeconds = 0;
        this.isRunning = false;
    }

    public void setListener(TimerListener listener) {
        this.listener = listener;
    }

    public void start() {
        if (isRunning) {
            return; 
        }

        isRunning = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeInSeconds++; 
                notifyListener(); 
            }
        }, 1000, 1000); 
    }

    public int getTimeInSeconds() {
        return timeInSeconds;
    }

    public String getFormattedTime() {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds); 
    }

    private void notifyListener() {
        if (listener != null) {
            listener.onTimeUpdate(timeInSeconds); 
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public interface TimerListener {
        void onTimeUpdate(int seconds);
    }

    public void stop() {
        if (isRunning && timer != null) {
            timer.cancel();
            isRunning = false;
        }
    }

    public void reset() {
        timeInSeconds = 0; // Set waktu kembali ke 0
        if (listener != null) {
            listener.onTimeUpdate(timeInSeconds); // Update tampilan waktu
        }
    }

    public void setTime(int seconds) {
        this.timeInSeconds = seconds;
    }

    
    
}