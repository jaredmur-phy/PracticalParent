package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.example.practicalparent.model.Child;
import com.example.practicalparent.model.ChildManager;
import com.example.practicalparent.model.CoinFlipHistory;
import com.example.practicalparent.model.CoinFlipHistoryManager;
import com.example.practicalparent.timer.TimeInMills;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import static com.example.practicalparent.model.PickedConstant.NOT_PICKED;
import static com.example.practicalparent.model.PickedConstant.PICKED_HEAD;
import static com.example.practicalparent.model.PickedConstant.PICKED_TAIL;

// flip a coin; see the results
public class FlipResultsActivity extends AppCompatActivity {

    private Button buttonFlip;
    private Random generator = new Random();
    private ImageView imageCoin;

    private boolean isHead;

    private Handler handler;

    private boolean turnOffBack;
    private int picked;
    private int childIndex;

    private static final int DELAY = 950;
    private static final String COIN_PARAM_KEY = "COIN_PARAM_KEY";
    private static final String CHILD_INDEX_PARAM_KEY = "CHILD_INDEX_PARAM_KEY";

    private CoinFlipHistoryManager historyManager;
    private ChildManager childManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_results);
        initAttr();
        setToolBar();
        flipCoin();
    }

    private void initAttr() {
        Intent i = getIntent();
        picked = i.getIntExtra(COIN_PARAM_KEY, -1);
        childIndex = i.getIntExtra(CHILD_INDEX_PARAM_KEY, -1);
        historyManager = CoinFlipHistoryManager.getInstance(this);
        childManager = ChildManager.getInstance(this);
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
                isHead = generator.nextBoolean();
                soundEffect.start();
                coinFlipped(isHead, isHead ? "Heads" : "Tails");

                // will update history before destroy

                // locks buttons
                buttonFlip.setClickable(true);
                turnOffBack = false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        // update history
        Child child = childManager.select(childIndex);;
        historyManager.add(new CoinFlipHistory(child,
                picked, isHead));
        super.onDestroy();
    }

    private void coinFlipped(final boolean isHead, String results) {
        imageCoin = findViewById(R.id.id_coin_image);

        String prefix = isHead ? "head_" : "tail_";

        Handler handler = new Handler();
        List<Runnable> runnables = new ArrayList<Runnable>();
        for (int i = 0; i <= 100; i++) {
            // get id
            int id = getResources().getIdentifier(prefix + i, "drawable", this.getPackageName());
            final int index = i;
            Runnable r = () -> {
                imageCoin.setImageResource(id);
                if (index == 0) {         // animation start
                    buttonFlip.setClickable(false);
                    turnOffBack = true;
                } else if (index == 100) {       // animation ends

                    for (Runnable runnable : runnables) {
                        handler.removeCallbacks(runnable);
                    }
                    //code taken from: https://www.youtube.com/watch?v=fq8TDVqpmZ0
                    StyleableToast.makeText(FlipResultsActivity.this, results, R.style.resultToast).show();
                    stopAnimation();
                }
            };
            runnables.add(r);
            handler.postDelayed(r, i * TimeInMills.Hundredths_OF_A_SECOND.getValue());
        }
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

    public static Intent getIntent(Context c, int picked, int childIndex) {
        Intent intent = new Intent(c, FlipResultsActivity.class);
        intent.putExtra(COIN_PARAM_KEY, picked);
        intent.putExtra(CHILD_INDEX_PARAM_KEY, childIndex);
        return intent;
    }

    public static Intent makeLaunchIntent(Context c, boolean isHead, int childIndex) {
        return getIntent(c, isHead ? PICKED_HEAD : PICKED_TAIL, childIndex);
    }

    public static Intent makeLaunchIntent(Context c, boolean isHead) {
        return getIntent(c, isHead ? PICKED_HEAD : PICKED_TAIL, -1);
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c, NOT_PICKED, -1);
    }

    @Override
    public void onBackPressed() {
        if (turnOffBack) {
        } else {
            super.onBackPressed();
        }
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

