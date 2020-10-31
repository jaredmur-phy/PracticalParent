package com.example.practicalparent.project.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.practicalparent.R;

public class ChooseCoinActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_coin);
        setToolBar();
        headsChosen();
        tailsChosen();
    }


    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.idToolBarChooseCoin);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void headsChosen() {
        Button buttonHead = (Button) findViewById(R.id.idHeads);
        buttonHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchFlipResults();
            }
        });
    }

    private void tailsChosen() {
        Button buttonTails = (Button) findViewById(R.id.idTails);
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

    public static Intent makeLaunchIntent(Context context) {
        Intent intent = new Intent(context, ChooseCoinActivity.class);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}