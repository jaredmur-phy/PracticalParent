package com.example.practicalparent.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class TakeBreathActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private CheckBox checkBox;
    private Button buttonBreath;
    private Handler handler = new Handler();
    private final static int THREE_SECONDS = 3000;
    private final static int TEN_SECONDS = 13000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath);
        setToolBar();
        setBeginBreath();
        setChooseBreath();

    }

    private void setChooseBreath() {
        TextView chooseBreath = (TextView) findViewById(R.id.id_take_N_Breaths);

        chooseBreath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 /*dialogBuilder = new AlertDialog.Builder(TakeBreathActivity.this);
                final View changePopup = getLayoutInflater().inflate(R.layout.radio_popup, null);
                dialogBuilder.setView(changePopup);
                dialog = dialogBuilder.create();
                dialog.show();
                chooseBreath.setClickable(false);
                chooseBreath.setText();*/
            }
        });
    }

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
                                //change sound
                                final MediaPlayer soundEffect = MediaPlayer.create(TakeBreathActivity.this, R.raw.coinflipsound);
                                StyleableToast.makeText(TakeBreathActivity.this, getString(R.string.breath_in),
                                        R.style.inhaleToast).show();

                                handler.postDelayed(forThreeSeconds, THREE_SECONDS);
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

    Runnable forTenSeconds = new Runnable() {

        @Override
        public void run() {
// Turn off the music and animation
            // Delete this text
            buttonBreath.setText("stop");
        }
    };

    Runnable forThreeSeconds = new Runnable() {

        @Override
        public void run() {
            // Turn off the music and animation
            buttonBreath.setText(getString(R.string.out));
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


}