package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.example.practicalparent.model.ChildManager;

// Select a coin; heads or tails
public class ChooseCoinActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "KEY_INDEX";
    private ChildManager selector;
    private int childIndex = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_coin);

        selector = ChildManager.getInstance(this);
        getIndex();

        setChildInfo(childIndex);
        setChildOnClickListener();
        setToolBar();
        headsChosen();
        tailsChosen();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        childIndex = selector.peekOrder();
        setChildInfo(childIndex);
    }

    private void getIndex() {
        childIndex = getIntent().getIntExtra(KEY_INDEX, -1);
        if (childIndex == -1) {
            childIndex = selector.peekOrder();
        }
    }

    private void setChildInfo(int index) {
        if (index == -1) return;
        ImageView imageView = findViewById(R.id.id_next_child_img);
        TextView textView = findViewById(R.id.id_next_child_name);
        imageView.setImageDrawable(selector.get(index).getDrawable(this));
        textView.setText(selector.get(index).getName());
    }

    private void setChildOnClickListener() {
        ImageView imageView = findViewById(R.id.id_next_child_img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SelectChildActivity.getIntent(ChooseCoinActivity.this));
                finish();
            }
        });
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_choose_coin_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void headsChosen() {
        Button buttonHead = (Button) findViewById(R.id.id_heads_button);
        buttonHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchFlipResults(true);
            }
        });
    }

    private void tailsChosen() {
        Button buttonTails = (Button) findViewById(R.id.id_tails_button);
        buttonTails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchFlipResults(false);
            }
        });
    }

    private void launchFlipResults(boolean isHead) {
        Intent intent = FlipResultsActivity.makeLaunchIntent(ChooseCoinActivity.this, isHead, childIndex);
        startActivity(intent);
    }

    public static Intent getIntent(Context c) {
        return getIntent(c, -1);
    }

    public static Intent getIntent(Context c, int index) {
        Intent i = new Intent(c, ChooseCoinActivity.class);
        i.putExtra(KEY_INDEX, index);
        return i;
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c);
    }

    public static Intent makeLaunchIntent(Context c, int index) {
        return getIntent(c, index);
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