package com.example.practicalparent.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;

public class TakeBreathActivity extends AppCompatActivity {

    private final State stateReady = new StateReady();
    private final State stateWaitToInhale = new StateWaitToInhale();
    private final State stateInhaling = new StateInhaling();
    private final State stateOut = new StateOut();
    private final State stateDoneInhaling = new StateDoneInhaling();
    private final State stateReadyToExhale = new StateReadyToExhale();
    private final State stateExhaling = new StateExhaling();
    private final State stateFinish = new StateFinish();

    private State currentState = stateReady;

    private Button button;
    private TextView helpText;

    private long startTime = 0;
    private long endTime = 0;

    private boolean isTapping = false;
    private final Handler holdHandler = new Handler();
    private final Handler notPressHandler = new Handler();
    private int N = 3;


    private final Runnable holdCallback = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            long dTime = currentTime - startTime;
            if (dTime > 10000) {
                currentState.onButtonHeld10s();
            } else {
                if (dTime > 3000) {
                    currentState.onButtonHeld3s();
                }
                holdHandler.postDelayed(this, 500);
            }
        }
    };

    private final Runnable notPressCallback = new Runnable() {
        @Override
        public void run() {
            if(!isTapping){
                currentState.onButtonNotPressed();
                long currentTime = System.currentTimeMillis();
                long dTime = currentTime - endTime;
                if (dTime > 10000) {
                    currentState.onButtonNotPressed10s();
                } else if (dTime > 3000) {
                    currentState.onButtonNotPressed3s();
                }
                notPressHandler.postDelayed(this, 2000);
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath);
        setToolBar();
        getViews();
        setupButton();
        notPressHandler.post(notPressCallback);
    }

    private void getViews(){
        button = findViewById(R.id.id_begin_button);
        helpText = findViewById(R.id.id_take_N_Breaths);
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_take_breath_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void setupButton(){
        button.setOnClickListener(v -> currentState.onClick());
        button.setOnTouchListener((v, event) -> {
            int eventAction = event.getAction();
            if (eventAction == MotionEvent.ACTION_UP) {
                isTapping = false;
                currentState.onButtonRelease();
                endTime = System.currentTimeMillis();
                holdHandler.removeCallbacks(holdCallback);
                notPressHandler.post(notPressCallback);
            }else if(eventAction == MotionEvent.ACTION_DOWN){
                isTapping = true;
                currentState.onButtonHeld();
                startTime = System.currentTimeMillis();
                holdHandler.post(holdCallback);
            }
            return false;
        });
    }


    public static Intent getIntent(Context c) {
        return new Intent(c, TakeBreathActivity.class);
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void setStates(State state){
        currentState = state;
    }


    @Override
    protected void onDestroy() {
        notPressHandler.removeCallbacks(notPressCallback);
        super.onDestroy();
    }

    private abstract class State{
        void onClick(){}
        void onButtonRelease(){}
        void onButtonHeld(){}
        void onButtonHeld3s(){}
        void onButtonHeld10s(){}
        void onButtonNotPressed(){}
        void onButtonNotPressed3s(){}
        void onButtonNotPressed10s(){}

        void onMoreBreathNeeded(){ }

    }

    // TODO: add implementation for those functions.

    private class StateReady extends State{
        @Override
        void onClick() {
            setStates(stateWaitToInhale);
            helpText.setText("Hold button and breath in.");
            button.setText("In");
        }
    }

    private class StateWaitToInhale extends State{
        @Override
        void onButtonHeld() {
            setStates(stateInhaling);
            // TODO: start animation
            // TODO: start sound
            Toast.makeText(TakeBreathActivity.this, "start animation", Toast.LENGTH_SHORT).show();
        }
    }

    private class StateInhaling extends State{
        @Override
        void onButtonRelease() {
            setStates(stateWaitToInhale);
            helpText.setText("Hold button and breath in.");
            button.setText("In");
        }

        @Override
        void onButtonHeld3s() {
            setStates(stateOut);
        }
    }

    private class StateOut extends State{
        @Override
        void onButtonRelease() {
            setStates(stateDoneInhaling);
            // TODO: stop animation
            // TODO: stop sound
            Toast.makeText(TakeBreathActivity.this, "stop inhale animation", Toast.LENGTH_SHORT).show();
        }

        @Override
        void onButtonHeld10s() {
            setStates(stateDoneInhaling);
            // TODO: stop animation
            // TODO: stop sound
            Toast.makeText(TakeBreathActivity.this, "stop inhale animation", Toast.LENGTH_SHORT).show();
        }
    }

    private class StateDoneInhaling extends State{
        @Override
        void onButtonNotPressed() {
            setStates(stateReadyToExhale);
            button.setText("Out");
            // TODO: start exhale animation and sound
            Toast.makeText(TakeBreathActivity.this, "start exhale animation", Toast.LENGTH_SHORT).show();
        }
    }

    private class StateReadyToExhale extends State{
        @Override
        void onButtonNotPressed3s() {
            setStates(stateExhaling);
            if(N == 1){
                button.setText("good job");
            }else{
                button.setText("In");
            }
        }
    }

    private class StateExhaling extends State{
        @Override
        void onClick() {
            setStates(stateFinish);
            // TODO: stop animation and sound
            Toast.makeText(TakeBreathActivity.this, "stop exhale animation", Toast.LENGTH_SHORT).show();
            N --;
            if(N > 0){
                setStates(stateWaitToInhale);
            }
        }

        @Override
        void onButtonNotPressed10s() {
            setStates(stateFinish);
            // TODO: stop animation and sound
            Toast.makeText(TakeBreathActivity.this, "stop exhale animation", Toast.LENGTH_SHORT).show();
            N --;
            if(N > 0){
                setStates(stateWaitToInhale);
            }
        }
    }


    private class StateFinish extends State{
        @Override
        void onMoreBreathNeeded() {
            setStates(stateWaitToInhale);
        }
    }






}