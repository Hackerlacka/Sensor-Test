package com.example.robin.myfirstapp.compass;

import android.media.ToneGenerator;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by Robin on 2018-04-05.
 */

public class SoundAndVibrationThread extends Thread {
    private Monitor monitor;

    private Vibrator vibrator;
    private ToneGenerator toneGenerator;

    public SoundAndVibrationThread(Monitor monitor, Vibrator vibrator, ToneGenerator toneGenerator) {
        this.monitor = monitor;
        this.vibrator = vibrator;
        this.toneGenerator = toneGenerator;
    }

    @Override
    public void run() {
        while(true) {
            try {
                monitor.waitForSoundAndVibrate();
                sound();
                vibrate();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Shut down thread
                return;
            }
        }
    }

    private void sound() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200); // 200 is duration in ms
    }

    private void vibrate() {
        vibrator.vibrate(100);
    }
}
