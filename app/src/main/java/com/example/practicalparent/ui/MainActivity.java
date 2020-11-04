package com.example.practicalparent.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.example.practicalparent.R;

// Main menu which leads to other activities
public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setCoinFlip();
        setConfigurations();
        setTimeOut();
        setHelp();
        requestIgnoreBatteryOptimizations();
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

    private void setHelp() {
        Button buttonHelp = (Button) findViewById(R.id.idHelp);

        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHelp();
            }
        });
    }

    private void launchCoinFlip() {
        Intent intent = ChooseCoinActivity.makeLaunchIntent(MainActivity.this);
        startActivity(intent);
    }

    private void launchConfigurations() {
        Intent intent = ConfigureChild.makeLaunchIntent(MainActivity.this);
        startActivity(intent);
    }

    private void launchSetTimeOut() {
        Intent intent = TimerActivity.makeLaunchIntent(MainActivity.this);
        startActivity(intent);
    }

    private void launchHelp() {
        Intent intent = HelpActivity.makeLaunchIntent(MainActivity.this);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestIgnoreBatteryOptimizations() {

        // do nothing if is already ignoring
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
        if(isIgnoring) return;

        // otherwise request ignoring the battery optimization
        try {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}