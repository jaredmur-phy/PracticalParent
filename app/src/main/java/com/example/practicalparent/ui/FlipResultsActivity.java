package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.Random;

public class FlipResultsActivity extends AppCompatActivity {

    Button buttonFlip;
    Random generator = new Random();
    int headsOrTails;
    private final int HEADS = 0;
    private final int TAILS = 1;
    ImageView imageCoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_results);
        setToolBar();
        flipCoin();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.idToolBarFlipCoin);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void flipCoin() {
        buttonFlip = (Button) findViewById(R.id.idFlip);
        final MediaPlayer soundEffect = MediaPlayer.create(this, R.raw.coinflipsound);
        buttonFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headsOrTails = generator.nextInt(2);
                soundEffect.start();
                if(headsOrTails == HEADS) {
                    coinFlipped(R.drawable.heads, "Heads");
                } else{
                    coinFlipped(R.drawable.tail, "Tails");
                }

            }
        });
    }

    // Code taken from https://www.youtube.com/watch?v=eoPRhXoIOWA
    private void coinFlipped(final int imageId, String results) {
        imageCoin = findViewById(R.id.idCoin);

        Animation fadeOut = new AlphaAnimation(1,0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                buttonFlip.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                imageCoin.setImageResource(imageId);
                Animation fadeIn = new AlphaAnimation(0,1);

                fadeIn.setInterpolator(new DecelerateInterpolator(3));
                fadeIn.setDuration(3000);
                fadeIn.setFillAfter(true);

                imageCoin.startAnimation(fadeIn);
                buttonFlip.setClickable(true);
                // Code taken from: https://www.youtube.com/watch?v=fq8TDVqpmZ0
                StyleableToast.makeText(FlipResultsActivity.this,  results, R.style.resultToast).show();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageCoin.startAnimation(fadeOut);

    }

    public static Intent  makeLaunchIntent(Context context){
        Intent intent = new Intent(context, FlipResultsActivity.class);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}