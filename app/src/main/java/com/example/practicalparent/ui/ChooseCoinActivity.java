package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
                launchFlipResults();
            }
        });
    }

    private void tailsChosen() {
        Button buttonTails = (Button) findViewById(R.id.id_tails_button);
        buttonTails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchFlipResults();
            }
        });
    }

    private void launchFlipResults() {
        Intent intent = FlipResultsActivity.makeLaunchIntent(ChooseCoinActivity.this);
        startActivity(intent);
    }

    public static Intent getIntent(Context c){
        return new Intent(c, ChooseCoinActivity.class);
    }

    public static Intent makeLaunchIntent(Context c){
        return getIntent(c);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}