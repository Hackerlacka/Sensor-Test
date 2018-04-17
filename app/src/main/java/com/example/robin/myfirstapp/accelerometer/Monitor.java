package com.example.robin.myfirstapp.accelerometer;

/**
 * Created by Robin on 2018-04-17.
 */

public class Monitor {
    private boolean sound = false;
    private boolean close = false;

    public synchronized void setClose(boolean close) {
        this.close = close;
    }

    public synchronized boolean close() {
        return close;
    }

    public synchronized void setSound(boolean sound) {
        if(this.sound != sound) {
            this.sound = sound;
            if(sound) {
                notifyAll();
            }
        }
    }

    public synchronized void waitForSound() throws InterruptedException {
        while(!sound) {
            wait();
        }
    }
}
