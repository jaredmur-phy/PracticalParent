package com.example.practicalparent.project.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import com.example.practicalparent.R;

public class CoinFlipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_flip);
        headsChosen();
        tailsChosen();
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
        Intent intent = FlipResultsActivity.makeLaunchIntent(CoinFlipActivity.this);
        startActivity(intent);
    }

    public static Intent makeLaunchIntent(Context context) {
        Intent intent = new Intent(context, CoinFlipActivity.class);
        return intent;
    }
}