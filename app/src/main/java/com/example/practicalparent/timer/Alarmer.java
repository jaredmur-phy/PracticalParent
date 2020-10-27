package com.example.practicalparent.timer;

import android.os.Handler;
import android.os.Vibrator;

/**
 * Singleton class
 */
public class Alarmer{

    private Vibrator vibrator;
    private static Alarmer alarmer;

    private Alarmer(Vibrator v){
        vibrator = v;
    }

    public void alarm(){
        Handler handler = new Handler();
    }

    public static Alarmer getInstance(Vibrator v){
        if(alarmer == null){
            alarmer = new Alarmer(v);
        }
        return alarmer;
    }
}
