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
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.example.practicalparent.model.ChildManager;
import com.example.practicalparent.model.Task;
import com.example.practicalparent.model.TaskManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskListActivity extends AppCompatActivity {
    private TaskManager taskManager;
    private ArrayAdapter<Task> adapter;
    private ChildManager childManager;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);


        taskManager = TaskManager.getInstance(this);
        childManager = ChildManager.getInstance(this);

        setupFAB();
        populateListView();
        registerClickCallBackToEdit();
        launchTaskInfo();
        setToolBar();
    }

    private void setupFAB() {

        FloatingActionButton fab = findViewById(R.id.id_save_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = ((EditText) findViewById(R.id.id_enter_task)).getText().toString();

                String taskDescription = ((EditText) findViewById(R.id.id_enter_description)).getText().toString();

                if (!taskName.isEmpty() && !taskDescription.isEmpty()) {
                    Task task = new Task(childManager.getInstance(TaskListActivity.this).getList(), taskName, taskDescription);

                    taskManager.getInstance(TaskListActivity.this).addTask(task);

                    //duplicateCheck = ChildManager.getInstance(ConfigureChildActivity.this).findChild(firstName);

              /*  if (duplicateCheck) {
                    //code taken from: https://www.youtube.com/watch?v=fq8TDVqpmZ0
                    StyleableToast.makeText(ConfigureChildActivity.this, "Please enter a different name", R.style.errorToast).show();
                } else if (ChildManager.getInstance(ConfigureChildActivity.this).isNullOrEmpty(firstName)) {
                    //code taken from: https://www.youtube.com/watch?v=fq8TDVqpmZ0
                    StyleableToast.makeText(ConfigureChildActivity.this, "Please enter a name", R.style.errorToast).show();
                } else {*/

                    //Child child = new Child(firstName);

                    //ChildManager.getInstance(ConfigureChildActivity.this).addChild(child);

                  EditText clearTask = findViewById(R.id.id_enter_task);
                  EditText clearTaskDescription = findViewById(R.id.id_enter_description);
                    clearTask.getText().clear();
                    clearTaskDescription.getText().clear();
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
                //}
            }
        });
    }

    private void populateListView() {

        adapter = new ArrayAdapter<>(
                TaskListActivity.this,
                R.layout.taskitems,
                taskManager.getList());
        ListView list =  findViewById(R.id.id_task_list_view);
        list.setAdapter(adapter);
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_task_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void registerClickCallBackToEdit() {
        ListView list = findViewById(R.id.id_task_list_view);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean delete = checkSwitch();
                if(delete) {
                    showID(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private boolean checkSwitch() {
        Switch sw = (Switch)findViewById(R.id.id_update);
        if(sw.isChecked()) {
            return true;
        }
        return false;
    }

    public void showID(int i) {
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
                String changeTaskName = ((EditText) changePopup.findViewById(R.id.id_update_task_name)).getText().toString();
                String changeTaskDesc = ((EditText) changePopup.findViewById(R.id.id_update_task_desc)).getText().toString();

                TaskManager.getInstance(TaskListActivity.this).changeTaskName(i, changeTaskName);
                TaskManager.getInstance(TaskListActivity.this).changeTaskDesc(i, changeTaskDesc);

                adapter.notifyDataSetChanged();


                dialog.dismiss();
            }
            });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskManager.removeTask(i);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
        }

    private void launchTaskInfo() {
        ListView list = findViewById(R.id.id_task_list_view);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = TaskInfoActivity.makeLaunchIntent(TaskListActivity.this);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context c) {
        return new Intent(c, TaskListActivity.class);
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