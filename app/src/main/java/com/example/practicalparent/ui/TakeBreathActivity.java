package com.example.practicalparent.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.example.practicalparent.model.NumberOfBreaths;
import com.example.practicalparent.timer.TimeInMills;

public class TakeBreathActivity extends AppCompatActivity {

    private TextView chooseBreath;

    private final State stateReady = new StateReady();
    private final State stateWaitToInhale = new StateWaitToInhale();
    private final State stateInhaling = new StateInhaling();
    private final State stateOut = new StateOut();
    private final State stateDoneInhaling = new StateDoneInhaling();
    private final State stateReadyToExhale = new StateReadyToExhale();
    private final State stateExhaling = new StateExhaling();
    private final State stateFinish = new StateFinish();

    private final static int ZERO = 0;
    private final static int ONE = 1;
    private final static int TWO = 2;
    private final static int THREE = 3;
    private final static int FOUR = 4;
    private final static int FIVE = 5;
    private final static int SIX = 6;
    private final static int SEVEN = 7;
    private final static int EIGHT = 8;
    private final static int NINE = 9;
    private final static int TEN = 10;

    private State currentState = stateReady;

    private Button button;

    private long lastClickStartTime = 0;
    private long lastClickEndTime = 0;

    NumberOfBreaths breath = NumberOfBreaths.getInstance();

    private boolean isTapping = false;
    private final Handler holdHandler = new Handler();
    private final Handler notPressHandler = new Handler();

    private final long tenSecond = 10 * TimeInMills.SECOND.getValue();
    private final long threeSecond = 3 * TimeInMills.SECOND.getValue();
    private MediaPlayer inHalingPlayer;
    private MediaPlayer exHalingPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath);

        setToolBar();
        setChooseBreath();
        getViews();
        setupButton();
        setHeading();
        playMusic();
    }


    private void setHeading() {
        int index = getSavedSelected(this);
        breath.setBreaths(++index);
        setNumberOfBreaths();
    }


    private void playMusic() {
        notPressHandler.post(notPressCallback);
        inHalingPlayer = MediaPlayer.create(this, R.raw.relax);
        inHalingPlayer.setLooping(true);
        exHalingPlayer = MediaPlayer.create(this, R.raw.relax);
        exHalingPlayer.setLooping(true);
    }


    private final Runnable holdCallback = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            long dTime = currentTime - lastClickStartTime;
            if (dTime > tenSecond) {
                currentState.onButtonHeld10s();
            } else {
                if (dTime > threeSecond) {
                    currentState.onButtonHeld3s();
                }
                holdHandler.postDelayed(this, TimeInMills.HALF_SECOND.getValue());
            }
        }
    };

    private final Runnable notPressCallback = new Runnable() {
        @Override
        public void run() {
            if (!isTapping) {
                currentState.onButtonNotPressed();
                long currentTime = System.currentTimeMillis();
                long dTime = currentTime - lastClickEndTime;
                if (dTime > tenSecond) {
                    currentState.onButtonNotPressed10s();
                } else if (dTime > threeSecond) {
                    currentState.onButtonNotPressed3s();
                }
                notPressHandler.postDelayed(this, TimeInMills.HALF_SECOND.getValue());
            }
        }
    };


    private void stopInHalingAnimation() {
        button.clearAnimation();
        if (inHalingPlayer.isPlaying()) {
            inHalingPlayer.pause();
        }
    }

    private void stopExHalingAnimation() {
        button.clearAnimation();
        if (exHalingPlayer.isPlaying()) {
            exHalingPlayer.pause();
        }
    }

    private void startExHalingAnimation() {
        Animation exHalingAnimation = AnimationUtils.loadAnimation(this, R.anim.ex_haling_animation);
        button.startAnimation(exHalingAnimation);
        exHalingPlayer.start();
    }

    private void startInHalingAnimation() {
        Animation inHalingAnimation = AnimationUtils.loadAnimation(this, R.anim.in_haling_animation);
        inHalingAnimation.setFillAfter(true);
        button.startAnimation(inHalingAnimation);
        inHalingPlayer.start();
    }

    private void setNumberOfBreaths() {
        chooseBreath.setText(getString(R.string.lets) + " " + breath.getBreaths() + " " + getString(R.string.take));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.choose_breath_menu, menu);

        int index = getSavedSelected(this);

        MenuItem menuItem = menu.getItem(index);

        breath.setBreaths(++index);
        setNumberOfBreaths();
        menuItem.setChecked(true);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.id_one:

                breath.setBreaths(ONE);

                setNumberOfBreaths();

                saveSelected(ZERO);

                item.setChecked(true);

                return true;

            case R.id.id_two:

                breath.setBreaths(TWO);

                setNumberOfBreaths();

                saveSelected(ONE);

                item.setChecked(true);

                return true;

            case R.id.id_three:

                breath.setBreaths(THREE);

                setNumberOfBreaths();

                saveSelected(TWO);

                item.setChecked(true);

                return true;

            case R.id.id_four:

                breath.setBreaths(FOUR);

                setNumberOfBreaths();

                saveSelected(THREE);

                item.setChecked(true);

                return true;

            case R.id.id_five:

                breath.setBreaths(FIVE);

                setNumberOfBreaths();

                saveSelected(FOUR);

                item.setChecked(true);

                return true;

            case R.id.id_six:

                breath.setBreaths(SIX);

                setNumberOfBreaths();

                item.setChecked(true);

                saveSelected(FIVE);

                return true;

            case R.id.id_seven:

                breath.setBreaths(SEVEN);

                setNumberOfBreaths();

                item.setChecked(true);

                saveSelected(SIX);

                return true;

            case R.id.id_eight:

                breath.setBreaths(EIGHT);

                setNumberOfBreaths();

                item.setChecked(true);

                saveSelected(SEVEN);

                return true;

            case R.id.id_nine:

                breath.setBreaths(NINE);

                setNumberOfBreaths();

                item.setChecked(true);

                saveSelected(EIGHT);

                return true;

            case R.id.id_ten:

                breath.setBreaths(TEN);

                setNumberOfBreaths();

                item.setChecked(true);

                saveSelected(NINE);

                return true;

            default:

                return super.onContextItemSelected(item);
        }
    }

    private void setChooseBreath() {
        chooseBreath = (TextView) findViewById(R.id.id_take_N_Breaths);

        chooseBreath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerForContextMenu(v);
                openContextMenu(v);
            }
        });
    }

    // Save to sharedpreferences
    private void saveSelected(int index) {
        SharedPreferences prefs = this.getSharedPreferences("OptionPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("Extract the selected", index);
        editor.apply();
    }

    private void getViews() {
        button = findViewById(R.id.id_begin_button);
    }

    // Load from sharedpreferences
    static public int getSavedSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("OptionPreferences", MODE_PRIVATE);
        return prefs.getInt("Extract the selected", TWO);
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_take_breath_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setupButton() {
        button.setOnClickListener(v -> currentState.onClick());
        button.setOnTouchListener((v, event) -> {
            int eventAction = event.getAction();
            if (eventAction == MotionEvent.ACTION_DOWN) {
                isTapping = true;
                currentState.onButtonHeld();
                lastClickStartTime = System.currentTimeMillis();
                holdHandler.post(holdCallback);
            } else if (eventAction == MotionEvent.ACTION_UP) {
                isTapping = false;
                currentState.onButtonRelease();
                lastClickEndTime = System.currentTimeMillis();
                holdHandler.removeCallbacks(holdCallback);
                notPressHandler.post(notPressCallback);
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

    private void setStates(State state) {
        currentState = state;
    }

    @Override
    protected void onDestroy() {
        notPressHandler.removeCallbacks(notPressCallback);
        if (exHalingPlayer.isPlaying()) {
            exHalingPlayer.pause();
        }
        if (inHalingPlayer.isPlaying()) {
            inHalingPlayer.pause();
        }
        super.onDestroy();
    }

    private abstract class State {
        void onClick() {
        }

        void onButtonRelease() {
        }

        void onButtonHeld() {
        }

        void onButtonHeld3s() {
        }

        void onButtonHeld10s() {
        }

        void onButtonNotPressed() {
        }

        void onButtonNotPressed3s() {
        }

        void onButtonNotPressed10s() {
        }
    }

    private class StateReady extends State {
        @Override
        void onClick() {
            setStates(stateWaitToInhale);
            chooseBreath.setText(getString(R.string.breath_in));
            button.setText(getString(R.string.in));
            chooseBreath.setEnabled(false);
        }
    }

    private class StateWaitToInhale extends State {
        @Override
        void onButtonHeld() {
            setStates(stateInhaling);
            startInHalingAnimation();
        }
    }

    private class StateInhaling extends State {
        @Override
        void onButtonRelease() {
            setStates(stateWaitToInhale);
            chooseBreath.setText(getString(R.string.breath_in));
            button.setText(getString(R.string.in));
            stopInHalingAnimation();
        }

        @Override
        void onButtonHeld3s() {
            setStates(stateOut);
            button.setText(getString(R.string.out));
        }
    }

    private class StateOut extends State {
        @Override
        void onButtonRelease() {
            setStates(stateDoneInhaling);
            stopInHalingAnimation();
        }

        @Override
        void onButtonHeld10s() {
            setStates(stateDoneInhaling);
            chooseBreath.setText(getString(R.string.breath_out));
            stopInHalingAnimation();
        }
    }

    private class StateDoneInhaling extends State {
        @Override
        void onButtonNotPressed() {
            setStates(stateReadyToExhale);
            button.setText(getString(R.string.out));
            startExHalingAnimation();
            chooseBreath.setText(getString(R.string.breath_out));
        }
    }

    private class StateReadyToExhale extends State {
        @Override
        void onButtonNotPressed3s() {
            int decrementBreath = breath.getBreaths();

            int index = --decrementBreath;

            breath.setBreaths(index);

            saveSelected(--index);

            if (breath.getBreaths() == ZERO) {     // if this is the last one
                button.setText(getString(R.string.good_job));
                saveSelected(TWO);
                setNumberOfBreaths();
            } else {
                button.setText(getString(R.string.in));
            }
            setStates(stateExhaling);
            setNumberOfBreaths();
        }

    }

    private class StateExhaling extends State {
        @Override
        void onClick() {
            stopExHalingAnimation();

            if (breath.getBreaths() > ZERO) {
                setStates(stateWaitToInhale);
            } else {
                setStates(stateFinish);
                finish();
            }
        }

        @Override
        void onButtonNotPressed10s() {
            stopExHalingAnimation();

            int decrementBreath = breath.getBreaths();

            breath.setBreaths(--decrementBreath);
            if (breath.getBreaths() > ZERO) {
                setStates(stateWaitToInhale);
            } else {
                setStates(stateFinish);
                finish();
            }
        }
    }

    private class StateFinish extends State {
        @Override
        void onClick() {
            setStates(stateWaitToInhale);
        }
    }
}