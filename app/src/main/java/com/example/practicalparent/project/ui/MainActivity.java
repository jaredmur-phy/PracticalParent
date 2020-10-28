package com.example.practicalparent.project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.practicalparent.R;

// Main menu which leads to other activities
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setCoinFlip();
        setConfigurations();
        setTimeOut();
    }

    private void setCoinFlip() {
        Button buttonCoinFlip = (Button) findViewById(R.id.idFlip);

        buttonCoinFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCoinFlip();
            }
        });

    }

    private void setConfigurations() {
        Button buttonConfig = (Button) findViewById(R.id.idConfigure);

        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchConfigurations();
            }
        });

    }

    private void setTimeOut() {
        Button buttonTimeOut = (Button) findViewById(R.id.idTimeOut);

        buttonTimeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSetTimeOut();
            }
        });

    }

    private void launchConfigurations() {
        /*Intent intent = ?.makeIntent(MainActivity.this);
        StartActivity(intent);*/
    }

    private void launchCoinFlip() {
       /* Intent intent = ?.makeIntent(MainActivity.this);
        StartActivity(intent);*/
    }

    private void launchSetTimeOut() {
       /* Intent intent = ?.makeIntent(MainActivity.this);
        StartActivity(intent);*/
    }
}