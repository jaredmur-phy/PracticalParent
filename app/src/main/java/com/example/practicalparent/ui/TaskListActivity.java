package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.example.practicalparent.model.Child;
import com.example.practicalparent.model.ChildManager;
import com.example.practicalparent.model.Task;
import com.example.practicalparent.model.TaskManager;

// Shows task list and opens up task info
public class TaskListActivity extends AppCompatActivity {
    private TaskManager taskManager;
    private ArrayAdapter<Task> adapter;
    private ChildManager childManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        taskManager = TaskManager.getInstance(this);
        childManager = ChildManager.getInstance(this);

        setupFAB();
        populateListView();
        registerClickCallBack();
        setToolBar();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        populateListView();
    }

    private void setupFAB() {
        findViewById(R.id.id_add_task_fab).setOnClickListener(v -> launchSaveTask());
    }

    private void launchSaveTask() {
        startActivity(SaveTaskActivity.makeLaunchIntent(TaskListActivity.this));
    }

    private void registerClickCallBack() {
        ListView list = findViewById(R.id.id_task_list_view);
        list.setOnItemClickListener((parent, view, position, id) -> launchTaskInfo(position));
    }

    private class TaskListAdapter extends ArrayAdapter<Task> {
        public TaskListAdapter() {
            super(TaskListActivity.this,
                    R.layout.taskitems,
                    taskManager.getList());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.taskitems, parent, false);
            }

            Task curTask = taskManager.getTask(position);


            // Fill the view
            ImageView childImgView = itemView.findViewById(R.id.id_task_child_img);
            childImgView.setImageDrawable(curTask.peekChild().getDrawable(TaskListActivity.this));
            TextView taskNameView = itemView.findViewById(R.id.id_task);
            taskNameView.setText(curTask.toString());

            return itemView;
        }
    }

    private void populateListView() {
        ArrayAdapter<Task> adapter = new TaskListAdapter();
        ListView list = findViewById(R.id.id_task_list_view);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_task_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void launchTaskInfo(int position) {
        Intent intent = TaskInfoActivity.makeLaunchIntent(TaskListActivity.this, position);
        startActivity(intent);
    }

    public static Intent getIntent(Context c) {
        return new Intent(c, TaskListActivity.class);
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_edit_setting:
                startActivity(EditTaskListActivity.makeLaunchIntent(this));
                return true;
            default:
                finish();
                return super.onOptionsItemSelected(item);
        }
    }

}