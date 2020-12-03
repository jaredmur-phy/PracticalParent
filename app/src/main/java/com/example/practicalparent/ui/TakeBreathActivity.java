package com.example.practicalparent.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.os.Handler;
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
import com.muddzdev.styleabletoastlibrary.StyleableToast;
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

    private State currentState = stateReady;

    private Button button;
    private TextView helpText;

    private long startTime = 0;
    private long endTime = 0;

    NumberOfBreaths breath = NumberOfBreaths.getInstance();

    private boolean isTapping = false;
    private final Handler holdHandler = new Handler();
    private final Handler notPressHandler = new Handler();
   // private int N = 3;
    private long tenSecond = 10 * TimeInMills.SECOND.getValue();
    private long threeSecond = 3 * TimeInMills.SECOND.getValue();
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
        notPressHandler.post(notPressCallback);
        inHalingPlayer = MediaPlayer.create(this, R.raw.ring);
        inHalingPlayer.setLooping(true);
        exHalingPlayer = MediaPlayer.create(this, R.raw.ring);
        exHalingPlayer.setLooping(true);

    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.choose_breath_menu, menu);




        int index= getSavedSelected(this);



        MenuItem menuItem = menu.getItem(index);

        breath.setBreaths(++index);
        setNumberOfBreaths();
        menuItem.setChecked(true);


    }

    private final Runnable holdCallback = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            long dTime = currentTime - startTime;
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
            if(!isTapping){
                currentState.onButtonNotPressed();
                long currentTime = System.currentTimeMillis();
                long dTime = currentTime - endTime;
                if (dTime > tenSecond) {
                    currentState.onButtonNotPressed10s();
                } else if (dTime > threeSecond) {
                    currentState.onButtonNotPressed3s();
                }
                notPressHandler.postDelayed(this, TimeInMills.HALF_SECOND.getValue());
            }
        }
    };


    private void stopInHalingAnimation(){
        button.clearAnimation();
        if(inHalingPlayer.isPlaying()) {
            inHalingPlayer.pause();
        }
    }

    private void stopExHalingAnimation(){
        button.clearAnimation();
        if(exHalingPlayer.isPlaying()) {
            exHalingPlayer.pause();
        }
    }

    private void startExHalingAnimation(){
        Animation exHalingAnimation = AnimationUtils.loadAnimation(this, R.anim.ex_haling_animation);
        button.startAnimation(exHalingAnimation);
        exHalingPlayer.start();
    }

    private void startInHalingAnimation(){
        Animation inHalingAnimation = AnimationUtils.loadAnimation(this, R.anim.in_haling_animation);
        inHalingAnimation.setFillAfter(true);
        button.startAnimation(inHalingAnimation);
        inHalingPlayer.start();
    }





    private void setNumberOfBreaths(){

        chooseBreath.setText("Let's take " + breath.getBreaths() + " breaths together");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.id_one:

                breath.setBreaths(1);

                setNumberOfBreaths();




                saveSelected(0);

                item.setChecked(true);

                return true;

            case R.id.id_two:

                breath.setBreaths(2);

                setNumberOfBreaths();



                saveSelected(1);


                item.setChecked(true);

                return true;

            case R.id.id_three:

                breath.setBreaths(3);

                setNumberOfBreaths();




                saveSelected(2);

                item.setChecked(true);
                return true;
            case R.id.id_four:

                breath.setBreaths(4);

                setNumberOfBreaths();




                saveSelected(3);

                item.setChecked(true);
                return true;

            case R.id.id_five:

                breath.setBreaths(5);

                setNumberOfBreaths();



                saveSelected(4);

                item.setChecked(true);
                return true;

            case R.id.id_six:


                breath.setBreaths(6);

                setNumberOfBreaths();

                item.setChecked(true);



                saveSelected(5);


                return true;

            case R.id.id_seven:


                breath.setBreaths(7);

                setNumberOfBreaths();

                item.setChecked(true);




                saveSelected(6);

                return true;

            case R.id.id_eight:

                breath.setBreaths(8);

                setNumberOfBreaths();

                item.setChecked(true);



                saveSelected(7);

                return true;

            case R.id.id_nine:

                breath.setBreaths(9);

                setNumberOfBreaths();

                item.setChecked(true);




                saveSelected(8);


                return true;

            case R.id.id_ten:

                breath.setBreaths(10);

                setNumberOfBreaths();

                item.setChecked(true);




                saveSelected(9);


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

    private void getViews(){
        button = findViewById(R.id.id_begin_button);
        //helpText = findViewById(R.id.id_take_N_Breaths);
    }


    // Load from sharedpreferences
    static public int getSavedSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("OptionPreferences", MODE_PRIVATE);
        return prefs.getInt("Extract the selected", 2);
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
            if(eventAction == MotionEvent.ACTION_DOWN){
                isTapping = true;
                currentState.onButtonHeld();
                startTime = System.currentTimeMillis();
                holdHandler.post(holdCallback);
            }else if (eventAction == MotionEvent.ACTION_UP) {
                isTapping = false;
                currentState.onButtonRelease();
                endTime = System.currentTimeMillis();
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
    }

    // TODO: add implementation for those functions.

    private class StateReady extends State {
        @Override
        void onClick() {
            setStates(stateWaitToInhale);
            chooseBreath.setText(getString(R.string.breath_in));
            button.setText(getString(R.string.in));
            chooseBreath.setClickable(false);
        }
    }

    private class StateWaitToInhale extends State {
        @Override
        void onButtonHeld() {
            setStates(stateInhaling);
            Toast.makeText(TakeBreathActivity.this, "start animation", Toast.LENGTH_SHORT).show();
            startInHalingAnimation();
            chooseBreath.setClickable(false);
            //setNumberOfBreaths();

        }
    }

    private class StateInhaling extends State {
        @Override
        void onButtonRelease() {
            setStates(stateWaitToInhale);
            chooseBreath.setText(getString(R.string.breath_in));
            button.setText(getString(R.string.in));
            stopInHalingAnimation();
            chooseBreath.setClickable(false);
        }

        @Override
        void onButtonHeld3s() {
            setStates(stateOut);
            button.setText(getString(R.string.out));
            chooseBreath.setClickable(false);
        }
    }

    private class StateOut extends State{
        @Override
        void onButtonRelease() {
            setStates(stateDoneInhaling);
            Toast.makeText(TakeBreathActivity.this, "stop inhale animation", Toast.LENGTH_SHORT).show();
            stopInHalingAnimation();
        }

        @Override
        void onButtonHeld10s() {
            setStates(stateDoneInhaling);
            Toast.makeText(TakeBreathActivity.this, "stop inhale animation", Toast.LENGTH_SHORT).show();
            chooseBreath.setText(getString(R.string.breath_out));
            stopInHalingAnimation();
        }
    }

    private class StateDoneInhaling extends State{
        @Override
        void onButtonNotPressed() {
            setStates(stateReadyToExhale);
            button.setText(getString(R.string.out));
            // TODO: start exhale animation and sound
            Toast.makeText(TakeBreathActivity.this, "start exhale animation", Toast.LENGTH_SHORT).show();
            startExHalingAnimation();
            chooseBreath.setText(getString(R.string.breath_out));
            chooseBreath.setClickable(false);
        }
    }

    private class StateReadyToExhale extends State{
        @Override
        void onButtonNotPressed3s() {
            if(breath.getBreaths() == 0){     // if this is the last one
                button.setText(getString(R.string.good_job));
            }else{
                button.setText(getString(R.string.in));
            }
            setStates(stateExhaling);
        }
    }

    private class StateExhaling extends State{
        @Override
        void onClick() {
            stopExHalingAnimation();
            Toast.makeText(TakeBreathActivity.this, "stop exhale animation", Toast.LENGTH_SHORT).show();
            int decrementBreath = breath.getBreaths();

            breath.setBreaths(--decrementBreath);
            if(breath.getBreaths() > 0){
                //waittoinhale
                setStates(stateReady);
                saveSelected(--decrementBreath);
                setNumberOfBreaths();
                //
            } else {
                setStates(stateFinish);
                finish();
            }
        }

        @Override
        void onButtonNotPressed10s() {
            stopExHalingAnimation();
            Toast.makeText(TakeBreathActivity.this, "stop exhale animation", Toast.LENGTH_SHORT).show();

           int decrementBreath = breath.getBreaths();

            breath.setBreaths(decrementBreath);
            if(breath.getBreaths() > 0){
                setStates(stateWaitToInhale);
                chooseBreath.setClickable(true);
            } else {
                setStates(stateFinish);
                finish();
            }
        }
    }


    private class StateFinish extends State{
        @Override
        void onClick() {
            setStates(stateWaitToInhale);
            //

        }

        /*@Override
        void noMoreBreathNeeded() {
            // TODO
        }*/
    }






}