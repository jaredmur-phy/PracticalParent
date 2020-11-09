package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
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

import static com.example.practicalparent.model.PickedConstant.NOT_PICKED;
import static com.example.practicalparent.model.PickedConstant.PICKED_HEAD;
import static com.example.practicalparent.model.PickedConstant.PICKED_TAIL;

// flip a coin; see the results
public class FlipResultsActivity extends AppCompatActivity {

    private Button buttonFlip;
    private Random generator = new Random();
    private ImageView imageCoin;

    private int headsOrTails;
    private final int HEADS = 0;

    private Handler handler;

    private boolean turnOffBack;
    private int picked;

    private static final int DELAY = 950;
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

    private void initAttr() {
        picked = getIntent().getIntExtra(COIN_PARAM_KEY, -1);
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
                if (headsOrTails == HEADS) {
                    coinFlipped(R.drawable.heads, "Heads");
                } else {
                    coinFlipped(R.drawable.tail, "Tails");
                }
                // update history
                historyManager.add(new CoinFlipHistory(childSelector.getNextChild(),
                        picked, headsOrTails == HEADS));
            }
        });
    }

    // Code taken from https://www.youtube.com/watch?v=eoPRhXoIOWA
    private void coinFlipped(final int imageId, String results) {
        imageCoin = findViewById(R.id.id_coin_image);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                buttonFlip.setClickable(false);
                turnOffBack = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                imageCoin.setImageResource(imageId);
                Animation fadeIn = new AlphaAnimation(0, 1);

                fadeIn.setInterpolator(new DecelerateInterpolator(3));
                fadeIn.setDuration(3000);
                fadeIn.setFillAfter(true);

                buttonFlip.setClickable(true);
                imageCoin.startAnimation(fadeIn);
                turnOffBack = false;
                // Code taken from: https://www.youtube.com/watch?v=fq8TDVqpmZ0
                StyleableToast.makeText(FlipResultsActivity.this, results, R.style.resultToast).show();
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
    }

    @Override
    public void onBackPressed() {
        if (turnOffBack) {


        } else {
            super.onBackPressed();

        }
    }

    public static Intent getIntent(Context c, int picked) {
        Intent intent = new Intent(c, FlipResultsActivity.class);
        intent.putExtra(COIN_PARAM_KEY, picked);
        return intent;
    }

    public static Intent makeLaunchIntent(Context c, boolean isHead) {
        return getIntent(c, isHead ? PICKED_HEAD : PICKED_TAIL);
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c, NOT_PICKED);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_settings:
                startActivity(CoinFlipHistoryActivity.makeLaunchIntent(this));
                return true;
            default:
                finish();
                return super.onOptionsItemSelected(item);
        }
    }
}

