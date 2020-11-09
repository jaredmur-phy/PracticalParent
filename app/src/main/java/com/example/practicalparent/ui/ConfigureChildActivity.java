package com.example.practicalparent.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.example.practicalparent.model.Child;
import com.example.practicalparent.model.ChildManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

// add,edit and delete a list of names given to children
public class ConfigureChildActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private boolean duplicateCheck = false;

    private ChildManager manager;
    private ArrayAdapter<Child> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_child);

        manager = ChildManager.getInstance(this);
        manager.iterator();

        setupFAB();
        populateListView();
        registerClickCallBack();
        setToolBar();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_configure_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void registerClickCallBack() {
        ListView list = findViewById(R.id.id_children_list_view);
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
                ConfigureChildActivity.this,
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
                String firstName = ((EditText) findViewById(R.id.id_child_name)).getText().toString();

                duplicateCheck = ChildManager.getInstance(ConfigureChildActivity.this).findChild(firstName);

                if (duplicateCheck) {
                    //code taken from: https://www.youtube.com/watch?v=fq8TDVqpmZ0
                    StyleableToast.makeText(ConfigureChildActivity.this, "Please enter a different name", R.style.errorToast).show();
                } else if (ChildManager.getInstance(ConfigureChildActivity.this).isNullOrEmpty(firstName)) {
                    //code taken from: https://www.youtube.com/watch?v=fq8TDVqpmZ0
                    StyleableToast.makeText(ConfigureChildActivity.this, "Please enter a name", R.style.errorToast).show();
                } else {

                    Child child = new Child(firstName);

                    ChildManager.getInstance(ConfigureChildActivity.this).addChild(child);

                    EditText clearName = findViewById(R.id.id_child_name);
                    clearName.getText().clear();
                    adapter.notifyDataSetChanged();
                    //Close the keyboard once input for child has been saved
                    //Code taken from:
                    //https://stackoverflow.com/questions/13593069/androidhide-keyboard-after-button-click/13593232
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                    }
                }
            }
        });
    }

    public void updatePosition(int i) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View changePopup = getLayoutInflater().inflate(R.layout.popup, null);
        dialogBuilder.setView(changePopup);
        dialog = dialogBuilder.create();
        dialog.show();

        Button saveBtn = changePopup.findViewById(R.id.saveChangeName);
        Button deleteBtn = changePopup.findViewById(R.id.deleteButton);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String changeName = ((EditText) changePopup.findViewById(R.id.changeName)).getText().toString();
                if (!ChildManager.getInstance(ConfigureChildActivity.this).isNullOrEmpty(changeName)) {
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

    public static Intent getIntent(Context c) {
        return new Intent(c, ConfigureChildActivity.class);
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c);
    }

}