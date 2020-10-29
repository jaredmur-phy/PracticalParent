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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practicalparent.receiver.TimeoutReceiver;
import com.example.practicalparent.timer.AlarmTimer;
import com.example.practicalparent.timer.Alarmer;
import com.example.practicalparent.timer.TimeInMills;
import com.example.practicalparent.timer.TimerStatus;


public class TimerActivity extends AppCompatActivity {

    private AlarmManager timerManager = null;
    private PendingIntent timeoutCallback = null;
    private AlarmTimer timer = null;
    private Alarmer alarmer = null;
    private int[] timerOptions;
    private int countDownMinutes = 1;

    private TimerStatus timerStatus = TimerStatus.SET_TIMER;

    private Handler refreshCallbackHandler = new Handler();
    private Runnable refreshCallback = null;

    private TextView customizeTimerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        setupTimerOptions();

        initAttributes();
        setPeriodRefresh();
        bindOnClickListener();
    }

    private void initAttributes(){
        customizeTimerTextView = findViewById(R.id.id_customize_timer);
        timerOptions = getResources().getIntArray(R.array.timer_options_array_value);
        timerManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        timeoutCallback = PendingIntent.getBroadcast(this, TimeoutReceiver.class.hashCode(),
                TimeoutReceiver.getIntent(this), 0);
        alarmer = Alarmer.getInstance(this, MediaPlayer.create(this, R.raw.ring));
    }

    private void setupTimerOptions(){
        Spinner spinner = (Spinner) findViewById(R.id.id_timer_options);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.timer_options_array_str, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int minutes = timerOptions[position];
                if(minutes > 0){
                    customizeTimerTextView.setVisibility(View.INVISIBLE);
                    countDownMinutes = minutes;
                } else {
                    customizeTimerTextView.setVisibility(View.VISIBLE);
                    customizeTimerTextView.setText("9 min");
                    countDownMinutes = 9;
                    Toast.makeText(TimerActivity.this, "-1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void bindOnClickListener(){
        TextView setTimerBtn = findViewById(R.id.id_set_timer);
        TextView resetBtn = findViewById(R.id.id_reset_btn);

        setTimerBtn.setOnClickListener((View v)->{
            switch (timerStatus){
                case SET_TIMER:
                    setTimer(countDownMinutes);
                    timerStatus = TimerStatus.PAUSE;
                    break;
                case PAUSE:
                    pauseTimer();
                    timerStatus = TimerStatus.RESUME;
                    break;
                case RESUME:
                    resumeTimer();
                    timerStatus = TimerStatus.PAUSE;
                    break;
                case TIMEOUT:
                    stopAlarming();
                    timerStatus = TimerStatus.SET_TIMER;
                    break;
            }

        });

        resetBtn.setOnClickListener((View v)->{
            resetTimer();
        });

    }

    private void showRemainingTimes(){
        TextView view = findViewById(R.id.id_set_timer);
        if( timer != null && !timer.isTimeout() ){
            view.setText(getShowTime() + "\n" + timerStatus.getMsg(this));
        } else if(timerStatus == TimerStatus.SET_TIMER){
            view.setText(timerStatus.getMsg(this));
        } else { // time out
            timerStatus = TimerStatus.TIMEOUT;
            view.setText(timerStatus.getMsg(this));
        }
    }

    private void setTimer(int minutes){
        long triggerTime = getTriggerTime(minutes);
        timerManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, timeoutCallback);
        timer = new AlarmTimer(minutes * TimeInMills.MINUTE.getValue());
    }


    /**
     * every half second it will refresh the clock
     */
    private void setPeriodRefresh(){
        refreshCallback = new Runnable() {
            @Override
            public void run() {
                showRemainingTimes();
                refreshCallbackHandler.postDelayed(this, TimeInMills.HALF_SECOND.getValue());
            }
        };
        refreshCallbackHandler.post(refreshCallback);
    }

    private void stopAlarming(){
        alarmer.stop();
    }

    private void pauseTimer(){
        if(!timer.isPaused()) {
            timer.pause();
            timerManager.cancel(timeoutCallback);
        }
    }

    private void resumeTimer(){
        if(!timer.isPaused()) {
            return;
        }
        timer.resume();
        timerManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, timer.getEndTime(), timeoutCallback);
    }

    private void resetTimer(){
        timerStatus = TimerStatus.PAUSE;
        if(timer == null){
            setTimer(countDownMinutes);
            return;
        }
        alarmer.stop();
        pauseTimer();
        timer.reset(countDownMinutes * TimeInMills.MINUTE.getValue());
        timerManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, timer.getEndTime(), timeoutCallback);
    }

    private long getTriggerTime(int minutes){
         return SystemClock.elapsedRealtime() + minutes * TimeInMills.MINUTE.getValue();
    }

    private String getShowTime(){
        long times = timer.getRemainingTime();
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

    @Override
    protected void onDestroy() {
        refreshCallbackHandler.removeCallbacks(refreshCallback);
        super.onDestroy();
    }
}