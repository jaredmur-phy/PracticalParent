package com.example.practicalparent.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.example.practicalparent.timer.Alarmer;


public class TimeoutReceiver extends BroadcastReceiver {

    private static final String TIMEOUT_ACTION = "TIMEOUT_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(TIMEOUT_ACTION.equals(action)) {
            Alarmer.getInstance().alarm();
        }
    }

    public static Intent getIntent(Context c){
        Intent i = new Intent(c, TimeoutReceiver.class);
        i.setAction(TIMEOUT_ACTION);
        return i;
    }
}
