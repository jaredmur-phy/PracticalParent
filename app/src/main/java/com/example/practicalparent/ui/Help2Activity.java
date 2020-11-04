package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.practicalparent.R;

// The mext page for sources
public class Help2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help2);
    }

    public static Intent getIntent(Context c) {
        return new Intent(c, Help2Activity.class);
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c);
    }

}