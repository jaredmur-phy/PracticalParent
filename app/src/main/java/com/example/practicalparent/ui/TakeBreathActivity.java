package com.example.practicalparent.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.example.practicalparent.model.NumberOfBreaths;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class TakeBreathActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private CheckBox checkBox;
    private Button buttonBreath;
    private Handler handler = new Handler();
    private final static int THREE_SECONDS = 3000;
    private final static int TEN_SECONDS = 13000;
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


    NumberOfBreaths breath = NumberOfBreaths.getInstance();

    private int storeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath);
        setToolBar();
        setBeginBreath();
        setChooseBreath();
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.choose_breath_menu, menu);

        if(breath.getID() == 0 ){
            MenuItem menuItem = menu.getItem(2);

            menuItem.setChecked(true);

        }

        else {
            for (int i = 0; i < menu.size(); ++i) {
                MenuItem menuItem = menu.getItem(i);

                if (menuItem.getItemId() == breath.getID()) {
                    menuItem.setChecked(true);
                }

            }
        }
    }


    private void setNumberOfBreaths(){

        chooseBreath.setText("Let's take " + breath.getBreaths() + " breaths together");

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        RadioButton radio;
        switch(item.getItemId()){

            case R.id.id_one:

                breath.setBreaths(1);

                setNumberOfBreaths();

                breath.setID(R.id.id_one);


                //storeID = R.id.id_one;

                item.setChecked(true);

                return true;

            case R.id.id_two:

                breath.setBreaths(2);

                setNumberOfBreaths();

                breath.setID(R.id.id_two);
                //storeID = R.id.id_two;


                item.setChecked(true);

                return true;

            case R.id.id_three:

                breath.setBreaths(3);

                setNumberOfBreaths();


                breath.setID(R.id.id_three);
                //storeID = R.id.id_three;

                item.setChecked(true);
                return true;
            case R.id.id_four:

                breath.setBreaths(4);

                setNumberOfBreaths();

                //storeID = R.id.id_four;
                breath.setID(R.id.id_four);


                item.setChecked(true);
                return true;

            case R.id.id_five:

                breath.setBreaths(5);

                setNumberOfBreaths();

                breath.setID(R.id.id_five);
                //storeID = R.id.id_five;

                item.setChecked(true);
                return true;

            case R.id.id_six:


                breath.setBreaths(6);

                setNumberOfBreaths();

                item.setChecked(true);

                breath.setID(R.id.id_six);
                //storeID = R.id.id_six;


                return true;
            case R.id.id_seven:


                breath.setBreaths(7);

                setNumberOfBreaths();

                item.setChecked(true);

                breath.setID(R.id.id_seven);
                //storeID = R.id.id_seven;

                return true;
            case R.id.id_eight:

                breath.setBreaths(8);

                setNumberOfBreaths();

                item.setChecked(true);

                breath.setID(R.id.id_eight);
                //storeID = R.id.id_eight;

                return true;

            case R.id.id_nine:

                breath.setBreaths(9);

                setNumberOfBreaths();

                item.setChecked(true);


                breath.setID(R.id.id_nine);
                //storeID = R.id.id_nine;


                return true;

            case R.id.id_ten:

                breath.setBreaths(10);

                setNumberOfBreaths();

                item.setChecked(true);

                breath.setID(R.id.id_ten);
                //storeID = R.id.id_ten;


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
    /*private void saveSelected() {
        int spinner1Position = Menu.

        SharedPreferences prefs = this.getSharedPreferences("OptionPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("Spinner2 values", spinner2Position);
        editor.apply();
    }


    // Load from sharedpreferences
    static public int getSavedSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("OptionPreferences", MODE_PRIVATE);
        return prefs.getInt("Spinner1 values", 0);
    }*/


    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_take_breath_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setBeginBreath() {
        buttonBreath = (Button) findViewById(R.id.id_begin_button);

                buttonBreath.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View arg0, MotionEvent arg1) {
                        switch (arg1.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                buttonBreath.setText(getString(R.string.in));
                                // start inhale animation and music
                                //change sound
                                final MediaPlayer soundEffect = MediaPlayer.create(TakeBreathActivity.this, R.raw.coinflipsound);
                                StyleableToast.makeText(TakeBreathActivity.this, getString(R.string.breath_in),
                                        R.style.inhaleToast).show();

                                handler.postDelayed(forThreeSeconds, THREE_SECONDS);

                                /*if(){

                                // 7 more seconds stop exhale animation and music
                                buttonBreath.setText(getString(R.string.in));
                                //update remaining breaths
                                // allow button to be pressed
                                buttonBreath.setClickable(true);


                                } else{

                                 // 7 more seconds stop exhale animation and music
                                //update remaining breaths to 0
                                buttonBreath.setText("Good job");
                                //lock button
                                buttonBreath.setClickable(false);
                                }
                                 */


                                handler.postDelayed(forTenSeconds, TEN_SECONDS);
                                break;

                            default:
                                handler.removeCallbacks(forThreeSeconds);
                                break;
                        }
                        return true;
                    }
                });

            }

    Runnable forThreeSeconds = new Runnable() {

        @Override
        public void run() {
            // stop inhale animation and music
            // start exhale animation and music?
            buttonBreath.setText(getString(R.string.out));

        }
    };

    Runnable forTenSeconds = new Runnable() {

        @Override
        public void run() {
// Turn off the music and animation
            // Delete this text
            buttonBreath.setText("stop");
            StyleableToast.makeText(TakeBreathActivity.this, getString(R.string.breath_out),
                    R.style.exhaleToast).show();

        }
    };

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



    private abstract class State{
        void onClick(){}
        void onButtonRelease(){}
        void onButtonHeld(){}
        void onButtonHeld3s(){}
        void onButtonHeld7s(){}
        void onButtonHeld10s(){}
        void onButtonNotPressed(){}
        void onButtonNotPressed3s(){}
        void onButtonNotPressed7s(){}

        void onMoreBreathNeeded(){ }
        void noMoreBreathNeeded(){ }

    }

    // TODO: add implementation for those functions.

    private class StateReady extends State{
        @Override
        void onClick() {
            setStates(stateWaitToInhale);
        }
    }

    private class StateWaitToInhale extends State{
        @Override
        void onButtonHeld() {
            setStates(stateInhaling);
        }
    }

    private class StateInhaling extends State{
        @Override
        void onButtonRelease() {
            setStates(stateWaitToInhale);
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
        }

        @Override
        void onButtonHeld10s() {
            setStates(stateDoneInhaling);
        }
    }

    private class StateDoneInhaling extends State{
        @Override
        void onButtonNotPressed() {
            setStates(stateReadyToExhale);
        }
    }

    private class StateReadyToExhale extends State{
        @Override
        void onButtonNotPressed3s() {
            setStates(stateExhaling);
        }
    }

    private class StateExhaling extends State{
        @Override
        void onClick() {
            setStates(stateFinish);
        }

        @Override
        void onButtonNotPressed7s() {
            setStates(stateFinish);
        }
    }


    private class StateFinish extends State{
        @Override
        void onMoreBreathNeeded() {
            setStates(stateWaitToInhale);
        }

        @Override
        void noMoreBreathNeeded() {
            // TODO
        }
    }






}