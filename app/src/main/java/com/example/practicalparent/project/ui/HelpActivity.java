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

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setNextPage();
    }

    private void setNextPage() {
    Button buttonNextPage = (Button) findViewById(R.id.idNextPage);

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

    public static Intent makeLaunchIntent(Context context){
        Intent intent = new Intent(context,HelpActivity.class);
        return intent;
    }
}