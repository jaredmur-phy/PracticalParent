package com.example.practicalparent.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;


public class TimeoutReceiver extends BroadcastReceiver {

    private static final String TIMEOUT_ACTION = "TIMEOUT_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(TIMEOUT_ACTION.equals(action)) {
            // TODO: add timeout call back
            Log.i("test", "test_clock");
            Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);

        }
    }

    public static Intent getIntent(Context c){
        Intent i = new Intent(c, TimeoutReceiver.class);
        i.setAction(TIMEOUT_ACTION);
        return i;
    }
}
