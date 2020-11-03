package com.example.practicalparent.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.practicalparent.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.practicalparent.childmodel.Child;
import com.example.practicalparent.childmodel.ChildManager;

public class ConfigureChild extends AppCompatActivity {

    private ChildManager manager;
    private ArrayAdapter<Child> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_child);

        manager = ChildManager.getInstance();
        manager.iterator();

        setupFAB();
        populateListView();
    }

    private void populateListView() {
        adapter = new ArrayAdapter<>(
                ConfigureChild.this,
                R.layout.childrenitems,
                manager.getList());
        ListView list = findViewById(R.id.childrenListView);
        list.setAdapter(adapter);
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.saveChild);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = ((EditText)findViewById(R.id.childname)).getText().toString();

                Child child = new Child(firstName);
                ChildManager.getInstance().addChild(child);
                finish();
            }
        });
    }

    public static Intent makeLaunchIntent(Context context) {
        return new Intent(context, ConfigureChild.class);
    }
}