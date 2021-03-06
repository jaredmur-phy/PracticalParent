package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;

// A page for sources
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setToolBar();
        setupHyperLinks();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_sources_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupHyperLinks() {
        TextView t1 = (TextView) findViewById(R.id.coinFlipSource);
        t1.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t2 = (TextView) findViewById(R.id.headsSource);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t3 = (TextView) findViewById(R.id.helpSource);
        t3.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t4 = (TextView) findViewById(R.id.sunsetSource);
        t4.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t5 = (TextView) findViewById(R.id.tailsSource);
        t5.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t6 = (TextView) findViewById(R.id.timerSource);
        t6.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t7 = (TextView) findViewById(R.id.userSource);
        t7.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t8 = (TextView) findViewById(R.id.coinSoundSource);
        t8.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t9 = (TextView) findViewById(R.id.ringSource);
        t9.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t10 = (TextView) findViewById(R.id.fontSource);
        t10.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t11 = (TextView) findViewById(R.id.id_team_name);
        t11.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t12 = (TextView) findViewById(R.id.yogaSource);
        t12.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t13 = (TextView) findViewById(R.id.relaxSoundSource);
        t13.setMovementMethod(LinkMovementMethod.getInstance());
        TextView t14 = (TextView) findViewById(R.id.takeBreathSource);
        t14.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static Intent getIntent(Context c) {
        return new Intent(c, HelpActivity.class);
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}