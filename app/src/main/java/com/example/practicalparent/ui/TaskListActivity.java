package com.example.practicalparent.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.practicalparent.R;

public class TaskListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
    }



    public static Intent getIntent(Context c) {
        return new Intent(c, TaskListActivity.class);
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c);
    }
}