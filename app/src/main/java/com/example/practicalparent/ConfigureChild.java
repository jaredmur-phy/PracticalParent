package com.example.practicalparent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.practicalparent.project.ui.CoinFlipActivity;

public class ConfigureChild extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_child);
    }

    public static Intent createLaunchIntent(Context context) {
        return new Intent(context, ConfigureChild.class);
    }
}