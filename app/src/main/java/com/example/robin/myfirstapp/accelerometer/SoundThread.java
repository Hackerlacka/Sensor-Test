package com.example.robin.myfirstapp.accelerometer;

import android.media.MediaPlayer;
import android.util.Log;

import com.example.robin.myfirstapp.R;

/**
 * Created by Robin on 2018-04-17.
 */

public class SoundThread extends Thread{

    private static MediaPlayer mediaPlayer;
    private Monitor monitor;

    public SoundThread(AccelerometersActivity accelerometersActivity, Monitor monitor) {
        if(mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(accelerometersActivity, R.raw.scream);
        }
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                monitor.waitForSound();
                if(!mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                }
            } catch (InterruptedException e) {
                if(monitor.close()) {
                    // Shut down thread
                    return;
                } else {
                    if(mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                }
            }
        }
    }



}
