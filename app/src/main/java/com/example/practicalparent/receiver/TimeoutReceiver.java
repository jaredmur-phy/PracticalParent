package com.example.practicalparent.receiver;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;


import com.example.practicalparent.project.ui.TimerActivity;
import com.example.practicalparent.timer.Alarmer;
import com.example.practicalparent.timer.TimeInMills;

/**
 * receive the broadcast when time out and do the alarming
 */
public class TimeoutReceiver extends BroadcastReceiver {

    private static final String TIMEOUT_ACTION = "TIMEOUT_ACTION";
    private static final int screenWakingUpTimeout = 5; // 5 min
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(TIMEOUT_ACTION.equals(action)) {
            wakeupScreen(context);
            Alarmer.getInstance(context).alarm();
        }
    }

    private void wakeupScreen(Context context){
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag")
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.SCREEN_DIM_WAKE_LOCK,"wakeupScreen");
        wl.acquire(screenWakingUpTimeout * TimeInMills.MINUTE.getValue());
    }

    public static Intent getIntent(Context c){
        Intent i = new Intent(c, TimeoutReceiver.class);
        i.setAction(TIMEOUT_ACTION);
        return i;
    }
}
