package com.example.practicalparent.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.practicalparent.R;
import com.example.practicalparent.listener.CustomizeChangedListener;
import com.example.practicalparent.receiver.TimeoutReceiver;
import com.example.practicalparent.timer.AlarmTimer;
import com.example.practicalparent.timer.Alarmer;
import com.example.practicalparent.timer.TimeInMills;

/**
 * Timer page
 */
public class TimerActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private PendingIntent timeoutCallback;
    private AlarmTimer timer;
    private Alarmer alarmer;

    private int[] timerOptions;
    private int countDownMinutes;

    // refresh clock
    private Handler refreshCallbackHandler;
    private Runnable refreshCallback;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        setupTimerOptions();

        initAttributes();

        setupBtnOnClickListener();
        setupPeriodRefresh();
    }

    private void initAttributes(){


        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        timeoutCallback = PendingIntent.getBroadcast(this, TimeoutReceiver.class.hashCode(),
                TimeoutReceiver.getIntent(this), 0);

        alarmer = Alarmer.getInstance(this);
        timer = AlarmTimer.getInstance();

        timerOptions = getResources().getIntArray(R.array.timer_options_array_value);
        countDownMinutes = timerOptions[0];

        refreshCallbackHandler = new Handler();
        refreshCallback = new Runnable() {
            @Override
            public void run() {
                showRemainingTimes();
                refreshCallbackHandler.postDelayed(this, TimeInMills.HALF_SECOND.getValue());
            }
        };
    }

    private void setupTimerOptions(){
        // setup Spinner
        Spinner spinner = findViewById(R.id.id_timer_options);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.timer_options_array_str, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextView customizeTimerUnitTextView = findViewById(R.id.id_customize_timer);
        TextView customizeTimerPlainTextView = findViewById(R.id.id_customize_plain_text);

        customizeTimerPlainTextView.addTextChangedListener(new CustomizeChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if(!str.isEmpty()) {
                    countDownMinutes = Integer.parseInt(str);
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int minutes = timerOptions[position];
                customizeTimerPlainTextView.setText("");
                if(minutes > 0){
                    customizeTimerUnitTextView.setVisibility(View.INVISIBLE);
                    customizeTimerPlainTextView.setVisibility(View.INVISIBLE);
                    countDownMinutes = minutes;
                } else {
                    customizeTimerUnitTextView.setVisibility(View.VISIBLE);
                    customizeTimerPlainTextView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setupBtnOnClickListener(){
        TextView setTimerBtn = findViewById(R.id.id_set_timer);
        TextView resetBtn = findViewById(R.id.id_reset_btn);

        setTimerBtn.setOnClickListener((View v)->{

            stopAlarming();
            switch (timer.getStatus()){
                case SET_TIMER:
                    setTimer(countDownMinutes);
                    break;
                case PAUSE:
                    pauseTimer();
                    break;
                case RESUME:
                    resumeTimer();
                    break;
            }
            timer.changeStatus();
        });
        resetBtn.setOnClickListener((View v)-> resetTimer());

    }

    private void showRemainingTimes(){
        TextView timerView = findViewById(R.id.id_set_timer);
        StringBuilder sb = new StringBuilder();
        if(!timer.isTimeout()){
            sb.append(getShowTime()).append("\n");
        } else {
            timer.setTimeoutStatus();
        }
        sb.append(timer.getStatusDesc(this));
        timerView.setText(sb);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setTimer(int minutes){
        long triggerTime = SystemClock.elapsedRealtime() + minutes * TimeInMills.MINUTE.getValue();
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, timeoutCallback);
        timer.reset(minutes * TimeInMills.MINUTE.getValue());
    }

    /**
     * every half second it will refresh the clock
     */
    private void setupPeriodRefresh(){
        refreshCallbackHandler.post(refreshCallback);
    }

    private void stopAlarming(){
        alarmer.stop();
    }

    private void pauseTimer(){
        if(!timer.isPaused()) {
            timer.pause();
            alarmManager.cancel(timeoutCallback);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void resumeTimer(){
        if(!timer.isPaused()) {
            return;
        }
        timer.resume();
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, timer.getEndTime(), timeoutCallback);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void resetTimer(){
        alarmer.stop();
        pauseTimer();
        timer.reset(countDownMinutes * TimeInMills.MINUTE.getValue());
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, timer.getEndTime(), timeoutCallback);
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