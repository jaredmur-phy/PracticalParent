package com.example.practicalparent.timer;

import android.app.Service;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.practicalparent.R;

import java.sql.Time;

/**
 * Singleton class
 * this class do the alarming or stop alarming
 */
public class Alarmer {

    private static Alarmer alarmer;

    private Vibrator vibrator;
    private Handler vibrateHandler;
    private boolean isAlarming;
    private MediaPlayer player;
    private Runnable vibrateCallback;

    private Alarmer(Context c) {
        this.vibrator = (Vibrator) c.getSystemService(Service.VIBRATOR_SERVICE);
        this.player = MediaPlayer.create(c, R.raw.ring);
        this.vibrateHandler = new Handler();
        this.isAlarming = false;

        player.setOnCompletionListener(mp -> {
            if (isAlarming) {
                player.start();
            }
        });
        vibrateCallback = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(TimeInMills.HALF_SECOND.getValue(),
                            VibrationEffect.DEFAULT_AMPLITUDE), attributes);
                } else {
                    //deprecated in API 26
                    vibrator.vibrate(TimeInMills.HALF_SECOND.getValue(), attributes);
                }
                vibrator.vibrate(TimeInMills.HALF_SECOND.getValue(), attributes);
                vibrateHandler.postDelayed(this, TimeInMills.SECOND.getValue());
            }
        };
    }

    public void alarm() {
        if (!isAlarming) {
            isAlarming = true;
            player.start();
            vibrateHandler.post(vibrateCallback);
        }
    }

    public void stop() {
        if (isAlarming) {
            isAlarming = false;
            player.seekTo(0);
            player.pause();
            vibrateHandler.removeCallbacks(vibrateCallback);
        }
    }

    public static Alarmer getInstance(Context c) {
        if (alarmer == null) {
            alarmer = new Alarmer(c.getApplicationContext());
        }
        return alarmer;
    }

}
