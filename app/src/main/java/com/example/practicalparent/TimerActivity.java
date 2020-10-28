package com.example.practicalparent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.example.practicalparent.receiver.TimeoutReceiver;
import com.example.practicalparent.timer.AlarmTimer;
import com.example.practicalparent.timer.Alarmer;
import com.example.practicalparent.timer.TimeInMills;
import com.example.practicalparent.timer.TimerStatus;


public class TimerActivity extends AppCompatActivity {

    private AlarmManager timerManager = null;
    private PendingIntent callback = null;
    private AlarmTimer timer = null;
    private Alarmer alarmer = null;
    private TimerStatus status = TimerStatus.SET_TIMER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // set timer
        timerManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        callback = PendingIntent.getBroadcast(this, TimeoutReceiver.class.hashCode(),
                TimeoutReceiver.getIntent(this), 0);
        alarmer = Alarmer.getInstance(this, MediaPlayer.create(this, R.raw.ring));
        setPeriodRefresh();
        bindOnClickListener();
    }


    private void bindOnClickListener(){
        TextView setTimerBtn = findViewById(R.id.id_set_timer);
        TextView resetBtn = findViewById(R.id.id_reset_btn);

        setTimerBtn.setOnClickListener((View v)->{
            switch (status){
                case SET_TIMER:
                    // TODO set time
                    setTimer(10);
                    status = TimerStatus.PAUSE;
                    break;
                case PAUSE:
                    pauseTimer();
                    status = TimerStatus.RESUME;
                    break;
                case RESUME:
                    resumeTimer();
                    status = TimerStatus.PAUSE;
                    break;
                case TIMEOUT:
                    stopAlarming();
                    status = TimerStatus.SET_TIMER;
                    break;
            }

        });

        resetBtn.setOnClickListener((View v)->{
            resetTimer();
        });

    }

    private void showRestTimes(){
        TextView view = findViewById(R.id.id_set_timer);
        if( timer != null && !timer.isTimeout() ){
            view.setText(getShowTime() + "\n" + status.getMsg());
        }else if(status == TimerStatus.SET_TIMER){
            view.setText(status.getMsg());
        }else { // time out
            status = TimerStatus.TIMEOUT;
            view.setText(status.getMsg());
        }
    }

    private void setTimer(int minutes){
        long triggerTime = getTriggerTime(minutes);
        timerManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, callback);
        timer = new AlarmTimer(minutes * TimeInMills.SECOND.getValue());
    }

    private void setPeriodRefresh(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                showRestTimes();
                handler.postDelayed(this, TimeInMills.HALF_SECOND.getValue());
            }
        });
    }

    private void stopAlarming(){
        alarmer.stop();
    }

    private void pauseTimer(){
        if(!timer.isPaused()) {
            timer.pause();
            timerManager.cancel(callback);
        }
    }

    private void resumeTimer(){
        if(!timer.isPaused()) {
            return;
        }
        timer.resume();
        timerManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, timer.getEndTime(), callback);
    }

    private void resetTimer(){
        if(!timer.isPaused()){
            timerManager.cancel(callback);
        }
        status = TimerStatus.PAUSE;
        timer.reset();
        timerManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, timer.getEndTime(), callback);
    }

    private long getTriggerTime(int minutes){
        // TODO: convert min to sec
//         return SystemClock.elapsedRealtime() + minutes * 60 * SECOND;
         return SystemClock.elapsedRealtime() + minutes * TimeInMills.SECOND.getValue();
    }

    private String getShowTime(){
        long times = timer.getRestTime();
        long minute = times / TimeInMills.MINUTE.getValue();
        long second = (times % TimeInMills.MINUTE.getValue()) / TimeInMills.SECOND.getValue();
        return minute + " : " + second;
    }

    public static Intent getIntent(Context c){
        return new Intent(c, TimerActivity.class);
    }

    public static Intent makeLaunchIntent(Context c){
        return getIntent(c);
    }


}