package com.example.robin.myfirstapp.compass;

import android.util.Log;

/**
 * Created by Robin on 2018-04-05.
 */

public class Monitor {
    private boolean soundAndVibrate = false;

    public synchronized void setSoundAndVibrate(boolean soundAndVibrate) {
        if(this.soundAndVibrate != soundAndVibrate) {
            this.soundAndVibrate = soundAndVibrate;
            if(soundAndVibrate) {
                notifyAll();
            }
        }
    }

    public synchronized void waitForSoundAndVibrate() throws InterruptedException {
        while(!soundAndVibrate) {
            wait();
        }
    }
}
