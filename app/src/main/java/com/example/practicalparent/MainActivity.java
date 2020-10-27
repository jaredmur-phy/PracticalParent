package com.example.practicalparent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonTimeOut;
    Button buttonConfig;
    Button buttonCoinFlip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCoinFlip();
        setConfigurations();
        setTimeOut();
    }









    private void setCoinFlip() {
        buttonCoinFlip = findViewById(R.id.IdFlip);

        buttonCoinFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCoinFlip();

            }
        });

    }
    private void setConfigurations() {
        buttonConfig = findViewById(R.id.idConfigure);

        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchConfigurations();

            }
        });

    }

    private void setTimeOut() {
        buttonTimeOut = findViewById(R.id.idTimeOut);

        buttonTimeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSetTimeOut();

            }
        });

    }

    private void launchConfigurations() {

        /*Intent intent = ?.getIntent(MainActivity.this);
        StartActivity(intent);*/

    }
    private void launchCoinFlip() {
       /* Intent intent = ?.getIntent(MainActivity.this);
        StartActivity(intent);*/
    }

    private void launchSetTimeOut() {
       /* Intent intent = ?.getIntent(MainActivity.this);
        StartActivity(intent);*/
    }
}