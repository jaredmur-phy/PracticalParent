package com.example.practicalparent.receiver;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.practicalparent.R;
import com.example.practicalparent.timer.AlarmTimer;
import com.example.practicalparent.timer.Alarmer;
import com.example.practicalparent.timer.TimeInMills;
import com.example.practicalparent.ui.TimerActivity;

/**
 * receive the broadcast when time out and do the alarming
 */
public class TimeoutReceiver extends BroadcastReceiver {
    public static final String CHANNEL_1_ID = "channel1";
    private static final String TIMEOUT_ACTION = "TIMEOUT_ACTION";
    private static final int screenWakingUpTimeout = 5; // 5 min
    private static final int sleepTime = 10;            // 10 second

    private NotificationManagerCompat notificationManager;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TIMEOUT_ACTION.equals(action)) {
            AlarmTimer timer = AlarmTimer.getInstance();
            if(timer.isTimeout()) {
                wakeupScreen(context);
                notificationManager = NotificationManagerCompat.from(context);
                setupNotificationChannel(context);
                sendNotification(context);
                Alarmer.getInstance(context).alarm();
            } else if(timer.isPaused() || timer.getStatus() == AlarmTimer.TimerStatus.SET_TIMER){
                // do nothing
            } else {
                // set timer
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                PendingIntent timeoutCallback = PendingIntent.getBroadcast(context, TimeoutReceiver.class.hashCode(),
                        TimeoutReceiver.getIntent(context), 0);
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + sleepTime * TimeInMills.SECOND.getValue()
                        , timeoutCallback);
            }
        }
    }

    private void sendNotification(Context context) {

        Intent activityIntent = new Intent(context, TimerActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, activityIntent, 0);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle("Alarm Done")
                .setContentText("Click to return to alarm")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setColor(Color.RED)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(1, notification);
    }

    private PendingIntent stopAlarm(Context context) {
        Alarmer.getInstance(context).stop();
        return null;
    }

    private void setupNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel1.setDescription("Time Out Over");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
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
