package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.practicalparent.R;

// A page for sources
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setupHyperLinks();
    }

    public static Intent getIntent(Context c) {
        return new Intent(c, HelpActivity.class);
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c);
    }

    private void setupHyperLinks() {
        TextView t1 = (TextView)findViewById(R.id.coinFlipSource);
        t1.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t2 = (TextView)findViewById(R.id.headsSource);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t3 = (TextView)findViewById(R.id.helpSource);
        t3.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t4 = (TextView)findViewById(R.id.sunsetSource);
        t4.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t5 = (TextView)findViewById(R.id.tailsSource);
        t5.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t6 = (TextView)findViewById(R.id.timerSource);
        t6.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t7 = (TextView)findViewById(R.id.userSource);
        t7.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t8 = (TextView)findViewById(R.id.coinSoundSource);
        t8.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t9 = (TextView)findViewById(R.id.ringSource);
        t9.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t10 = (TextView)findViewById(R.id.fontSource);
        t10.setMovementMethod(LinkMovementMethod.getInstance());
    }
}