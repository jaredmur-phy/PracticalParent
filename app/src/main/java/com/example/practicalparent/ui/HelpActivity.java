package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.example.practicalparent.R;

// A page for sources
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setNextPage();
    }

    private void setNextPage() {
        Button buttonNextPage = (Button) findViewById(R.id.id_next_page_button);

        buttonNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNextPage();
            }
        });
    }

    private void launchNextPage() {
        Intent intent = Help2Activity.makeLaunchIntent(HelpActivity.this);
        startActivity(intent);
    }

    public static Intent getIntent(Context c) {
        return new Intent(c, HelpActivity.class);
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c);
    }

}