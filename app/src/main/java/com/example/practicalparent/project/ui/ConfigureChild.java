package com.example.practicalparent.project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

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
        registerClickCallBack();
    }

    private boolean checkSwitch() {
        Switch sw = (Switch)findViewById(R.id.deleteSwitch);
        if(sw.isChecked()) {
            return true;
        }

        return false;
    }

    private void registerClickCallBack() {
        ListView list = findViewById(R.id.childrenListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean delete = checkSwitch();
                if(delete) {
                    manager.removeChild(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });
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
                EditText clearName = ((EditText)findViewById(R.id.childname));
                clearName.getText().clear();
                adapter.notifyDataSetChanged();
                //Close the keyboard once input for child has been saved
                //Code taken from:
                //https://stackoverflow.com/questions/13593069/androidhide-keyboard-after-button-click/13593232
                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {}
            }
        });
    }

    public static Intent makeLaunchIntent(Context context) {
        return new Intent(context, ConfigureChild.class);
    }
}