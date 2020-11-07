package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.fragment.app.FragmentManager;

import com.example.practicalparent.R;
import com.example.practicalparent.model.ChildSelector;
import com.example.practicalparent.model.CoinFlipHistory;
import com.example.practicalparent.model.CoinFlipHistoryManager;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.Random;

// flip a coin; see the results
public class FlipResultsActivity extends AppCompatActivity {

    Button buttonFlip;
    Random generator = new Random();
    int headsOrTails;
    private final int HEADS = 0;
    Handler handler;
    ImageView imageCoin;
    private boolean isPickedHead;
    private static int DELAY = 950;


    private static final String COIN_PARAM_KEY = "COIN_PARAM_KEY";

    private ChildSelector childSelector;
    private CoinFlipHistoryManager historyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_results);
        initAttr();
        setToolBar();
        flipCoin();
    }

    private void initAttr(){
        isPickedHead = getIntent().getBooleanExtra(COIN_PARAM_KEY, true);
        historyManager = CoinFlipHistoryManager.getInstance(this);
        childSelector = ChildSelector.getInstance(this);
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_flip_coin_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void flipCoin() {
        buttonFlip = (Button) findViewById(R.id.id_flip_button);
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
                // update history
                historyManager.add(new CoinFlipHistory(childSelector.getNextChild(),
                        isPickedHead, headsOrTails == HEADS));
            }
        });
    }

    // Code taken from https://www.youtube.com/watch?v=eoPRhXoIOWA
    private void coinFlipped(final int imageId, String results) {
        imageCoin = findViewById(R.id.id_coin_image);

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

                // Code taken from: https://www.youtube.com/watch?v=fq8TDVqpmZ0
                StyleableToast.makeText(FlipResultsActivity.this,  results, R.style.resultToast).show();
                stopAnimation();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageCoin.startAnimation(fadeOut);

    }

    private void stopAnimation() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                messagePopUP();
            }
        }, DELAY);
    }

    private void messagePopUP() {
        FragmentManager manager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.show(manager, "Great");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        //finish();
    }

    public static Intent getIntent(Context c, boolean isHead){
        Intent intent = new Intent(c, FlipResultsActivity.class);
        intent.putExtra(COIN_PARAM_KEY, isHead);
        return intent;
    }

    public static Intent makeLaunchIntent(Context c, boolean isHead){
        return getIntent(c, isHead);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}