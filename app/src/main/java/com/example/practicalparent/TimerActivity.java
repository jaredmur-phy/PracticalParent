package com.example.practicalparent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.example.practicalparent.receiver.TimeoutReceiver;
import com.example.practicalparent.timer.AlarmTimer;
import com.example.practicalparent.timer.TimerStatus;


public class TimerActivity extends AppCompatActivity {

    private AlarmManager timerManager = null;
    private PendingIntent callback = null;
    private AlarmTimer timer = null;

    private TimerStatus status = TimerStatus.SET_TIMER;

    private static final long SECOND = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // set timer
        timerManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        callback = PendingIntent.getBroadcast(this, TimeoutReceiver.class.hashCode(),
                TimeoutReceiver.getIntent(this), 0);

        TextView setTimerBtn = findViewById(R.id.id_set_timer);
        TextView resetBtn = findViewById(R.id.id_reset_btn);

        setTimerBtn.setOnClickListener((View v)->{

            switch (status){
                case SET_TIMER:
                    setTimer(10);
                    break;
                case PAUSE:
                    pauseTimer();
                    break;
                case RESUME:
                    resumeTimer();
                    break;
            }

        });

        resetBtn.setOnClickListener((View v)->{
            resetTimer();
        });

        setPeriodRefresh();
    }

    // TODO: make it private
    public void showRestTimes(){
        TextView view = findViewById(R.id.id_set_timer);
        if( timer != null && !timer.isTimeout() ){
            view.setText("" + timer.getRestTime()/SECOND + "\t" + status.getMsg());
        }else {
            status = TimerStatus.SET_TIMER;
            view.setText("set Timer");
        }
    }

    private void setTimer(int minutes){
        long triggerTime = getTriggerTime(minutes);
        timerManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, callback);
        timer = new AlarmTimer(minutes * SECOND);
        status = TimerStatus.PAUSE;
    }

    private void setPeriodRefresh(){
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                showRestTimes();
                handler.postDelayed(this, SECOND / 2);
            }
        });
    }

    private void pauseTimer(){
        if(!timer.isPaused()) {
            status = TimerStatus.RESUME;
            timer.pause();
            timerManager.cancel(callback);
        }
    }

    private void resumeTimer(){
        if(!timer.isPaused()) {
            return;
        }
        status = TimerStatus.PAUSE;
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
         return SystemClock.elapsedRealtime() + minutes * SECOND;
    }

    public static Intent getIntent(Context c){
        return new Intent(c, TimerActivity.class);
    }
}