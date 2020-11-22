package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.example.practicalparent.model.ChildManager;
import com.example.practicalparent.model.Task;
import com.example.practicalparent.model.TaskManager;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class SaveTaskActivity extends AppCompatActivity {
    private TaskManager taskManager;
    private ChildManager childManager;
    //private boolean duplicateCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_task);

        taskManager.getInstance(this);
        childManager.getInstance(this);

        setToolBar();
        setUpFAB();
    }

    private void setUpFAB() {
        findViewById(R.id.id_save_task_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = ((EditText) findViewById(R.id.id_save_task_name)).getText().toString();

                String taskDescription = ((EditText) findViewById(R.id.id_save_task_desc)).getText().toString();

                if (taskName.trim().isEmpty() || taskDescription.trim().isEmpty()) {
                    StyleableToast.makeText(SaveTaskActivity.this, getString(R.string.task_is_empty),
                            R.style.errorToast).show();

                } else if (taskManager.getInstance(SaveTaskActivity.this).checkTaskDesc(taskDescription) &&
                        taskManager.getInstance(SaveTaskActivity.this).checkTaskName(taskName)) {
                    // duplicate name
                    StyleableToast.makeText(SaveTaskActivity.this, getString(R.string.both_task_is_same),
                            R.style.errorToast).show();
                    return;
                } else if (taskManager.getInstance(SaveTaskActivity.this).checkTaskName(taskName)) {
                    // duplicate name
                    StyleableToast.makeText(SaveTaskActivity.this, getString(R.string.task_is_same),
                            R.style.errorToast).show();
                    return;
                } else if (taskManager.getInstance(SaveTaskActivity.this).checkTaskDesc(taskDescription)) {
                    // duplicate name
                    StyleableToast.makeText(SaveTaskActivity.this, getString(R.string.task_desc_is_same),
                            R.style.errorToast).show();
                    return;
                } else {
                    Task task = new Task(childManager.getInstance(SaveTaskActivity.this).getList(), taskName, taskDescription);

                    taskManager.getInstance(SaveTaskActivity.this).addTask(task);
                }
                finish();
            }
        });
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_save_task_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public static Intent getIntent(Context c) {
        return new Intent(c, SaveTaskActivity.class);
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