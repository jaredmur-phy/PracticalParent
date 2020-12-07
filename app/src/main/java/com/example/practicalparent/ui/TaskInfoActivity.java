package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.practicalparent.model.ChildManager;
import com.example.practicalparent.model.TaskManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.practicalparent.R;

// show task info
public class TaskInfoActivity extends AppCompatActivity {
    private TaskManager taskManager;
    private ChildManager childManager;
    private final static String GET_INDEX = "GET_INDEX";
    private int editChildIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
        taskManager.getInstance(this);
        childManager.getInstance(this);

        getChildIndex();
        setToolBar();
        setChildPhoto();
        setChildName();
        setTaskName();
        setTaskDesc();

        confirmation();
        cancel();

    }

    private void getChildIndex() {
        editChildIndex = getIntent().getExtras().getInt(GET_INDEX);
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_task_info_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setChildPhoto() {
        ImageView childImgView = findViewById(R.id.id_get_child_img);
        childImgView.setImageDrawable(taskManager.getInstance(TaskInfoActivity.this).getTask(editChildIndex).peekChild().getDrawable(TaskInfoActivity.this));
    }

    private void setChildName() {
        TextView editChildName = (TextView) findViewById(R.id.id_child_text);
        editChildName.setText("Name: " + taskManager.getInstance(TaskInfoActivity.this).getTask(editChildIndex).peekChild().getName());
    }

    private void setTaskName() {
        TextView editTaskName = (TextView) findViewById(R.id.id_task_title);
        editTaskName.setText("Task: " + taskManager.getInstance(TaskInfoActivity.this).getTask(editChildIndex).getTaskName());
    }

    private void setTaskDesc() {
        TextView editTaskDesc = (TextView) findViewById(R.id.id_task_desc);
        editTaskDesc.setText("Description: " + taskManager.getInstance(TaskInfoActivity.this).getTask(editChildIndex).getDesc());
    }

    private void cancel() {
        Button cancelButton = (Button) findViewById(R.id.id_cancel_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void confirmation() {
        Button confirmationButton = (Button) findViewById(R.id.id_confirm_button);

        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskManager.getInstance(TaskInfoActivity.this).getTask(editChildIndex).moveNext();
                taskManager.getInstance(TaskInfoActivity.this).update();
                finish();
            }
        });

    }

    public static Intent getIntent(Context c, int index) {
        Intent intent = new Intent(c, TaskInfoActivity.class);
        intent.putExtra(GET_INDEX, index);
        return intent;
    }

    public static Intent makeLaunchIntent(Context c, int index) {
        return getIntent(c, index);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}