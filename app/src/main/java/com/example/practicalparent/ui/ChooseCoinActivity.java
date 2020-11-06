package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.practicalparent.R;

// Select a coin; heads or tails
public class ChooseCoinActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_coin);
        setToolBar();
        headsChosen();
        tailsChosen();
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
        Intent intent = FlipResultsActivity.makeLaunchIntent(ChooseCoinActivity.this, isHead);
        startActivity(intent);
    }

    public static Intent getIntent(Context c){
        return new Intent(c, ChooseCoinActivity.class);
    }

    public static Intent makeLaunchIntent(Context c){
        return getIntent(c);
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