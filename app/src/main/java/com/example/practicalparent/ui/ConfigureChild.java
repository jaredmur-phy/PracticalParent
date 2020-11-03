package com.example.practicalparent.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.example.practicalparent.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.practicalparent.model.Child;
import com.example.practicalparent.model.ChildManager;

// add,edit and delete a list of names given to children
public class ConfigureChild extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

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

    private void registerClickCallBack() {
        ListView list = findViewById(R.id.childrenListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updatePosition(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void populateListView() {
        adapter = new ArrayAdapter<>(
                ConfigureChild.this,
                R.layout.childrenitems,
                manager.getList());
        ListView list = findViewById(R.id.id_children_list_view);
        list.setAdapter(adapter);
    }

    private void setupFAB() {
        FloatingActionButton fab = findViewById(R.id.id_save_child);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = ((EditText)findViewById(R.id.id_child_name)).getText().toString();

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

    public void updatePosition(int i) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View changePopup = getLayoutInflater().inflate(R.layout.popup, null);
        dialogBuilder.setView(changePopup);
        dialog = dialogBuilder.create();
        dialog.show();

        Button saveBtn = (Button)changePopup.findViewById(R.id.saveChangeName);
        Button deleteBtn = (Button)changePopup.findViewById(R.id.deleteButton);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String changeName = ((EditText)changePopup.findViewById(R.id.changeName)).getText().toString();
                if(changeName != null) {
                    manager.changeName(i, changeName);
                    adapter.notifyDataSetChanged();
                }

                dialog.dismiss();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.removeChild(i);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }

    public static Intent getIntent(Context c){
        return new Intent(c, ConfigureChild.class);
    }

    public static Intent makeLaunchIntent(Context c){
        return getIntent(c);
    }

}