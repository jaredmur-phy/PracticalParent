package com.example.practicalparent.receiver;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import androidx.core.app.NotificationCompat;

import com.example.practicalparent.R;
import com.example.practicalparent.timer.Alarmer;
import com.example.practicalparent.timer.TimeInMills;
import com.example.practicalparent.ui.TimerActivity;

/**
 * receive the broadcast when time out and do the alarming
 */
public class TimeoutReceiver extends BroadcastReceiver {

    private static final String TIMEOUT_ACTION = "TIMEOUT_ACTION";
    private static final int screenWakingUpTimeout = 5; // 5 min

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TIMEOUT_ACTION.equals(action)) {
            wakeupScreen(context);
            Alarmer.getInstance(context).alarm();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                    .setSmallIcon(R.drawable.tail_38)
                    .setContentTitle("TIMER COMPLETE")
                    .setContentText("Finished time out")
                    .setAutoCancel(true);

            Intent intent2 = TimerActivity.makeLaunchIntent(context);
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                    Context.NOTIFICATION_SERVICE
            );
            notificationManager.notify(0, builder.build());
        }
    }

    private void wakeupScreen(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag")
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.SCREEN_DIM_WAKE_LOCK, "wakeupScreen");
        wl.acquire(screenWakingUpTimeout * TimeInMills.MINUTE.getValue());
    }

    public static Intent getIntent(Context c) {
        Intent i = new Intent(c, TimeoutReceiver.class);
        i.setAction(TIMEOUT_ACTION);
        return i;
    }
}
