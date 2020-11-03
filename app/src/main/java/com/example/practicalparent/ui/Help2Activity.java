package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.practicalparent.R;

public class Help2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help2);
    }

    public static Intent makeLaunchIntent(Context context) {
        Intent intent = new Intent(context, Help2Activity.class);
        return intent;
    }
}