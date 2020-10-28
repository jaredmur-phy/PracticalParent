package com.example.practicalparent.timer;

import android.app.Service;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;

import java.sql.Time;

/**
 * Singleton class
 */
public class Alarmer{

    private Vibrator vibrator;
    private static Alarmer alarmer;
    private Handler handler = new Handler();
    private boolean isAlarming = false;
    private MediaPlayer player;
    private Runnable callback;

    private Alarmer(Context c, MediaPlayer player){
        vibrator = (Vibrator) c.getSystemService(Service.VIBRATOR_SERVICE);
        this.player = player;
        player.setOnCompletionListener(mp -> {
            if(isAlarming){
                player.start();
            }
        });
        callback = new Runnable(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();
                vibrator.vibrate(TimeInMills.HALF_SECOND.getValue(), attributes);
                handler.postDelayed(this, TimeInMills.SECOND.getValue());
            }
        };
    }

    public void alarm(){
        if(!isAlarming){
            isAlarming = true;
            player.start();
            handler.post(callback);
        }
    }

    public void stop(){
        if(isAlarming){
            isAlarming = false;
            player.seekTo(0);
            player.pause();
            handler.removeCallbacks(callback);
        }
    }

    public static Alarmer getInstance(Context c, MediaPlayer player){
        if(alarmer == null){
            alarmer = new Alarmer(c.getApplicationContext(), player);
        }
        return alarmer;
    }

    public static Alarmer getInstance(){
        if(alarmer == null){
            throw new IllegalArgumentException("should call getInstance(Context, MediaPlayer) first");
        }
        return alarmer;
    }
}
