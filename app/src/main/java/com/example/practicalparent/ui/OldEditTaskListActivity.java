package com.example.practicalparent.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.practicalparent.model.ChildManager;
import com.example.practicalparent.model.Task;
import com.example.practicalparent.model.TaskManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.practicalparent.R;

public class OldEditTaskListActivity extends AppCompatActivity {
    private TaskManager taskManager;
    private ArrayAdapter<Task> adapter;
    private ChildManager childManager;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_edit_task_list);

        taskManager = TaskManager.getInstance(this);
        childManager = ChildManager.getInstance(this);

        setToolBar();
        populateListView();
        registerClickCallBack();
    }

    private void registerClickCallBack() {
        ListView list = findViewById(R.id.id_edit_task_list_view_2);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //showID(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /*private void showID(int i) {
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
               // String changeTaskName = ((EditText) changePopup.findViewById(R.id.id_update_task_name2)).getText().toString();
               // String changeTaskDesc = ((EditText) changePopup.findViewById(R.id.id_update_task_desc2)).getText().toString();

                //TaskManager.getInstance(OldEditTaskListActivity.this).changeTaskName(i, changeTaskName);
                //TaskManager.getInstance(OldEditTaskListActivity.this).changeTaskDesc(i, changeTaskDesc);

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
    }*/

    @Override
    protected void onRestart() {
        super.onRestart();
        populateListView();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_edit_task_tool_bar_3);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void populateListView() {
        adapter = new ArrayAdapter<>(
                OldEditTaskListActivity.this,
                R.layout.taskitems,
                taskManager.getList());
        ListView list = findViewById(R.id.id_edit_task_list_view_2);
        list.setAdapter(adapter);
    }

    public static Intent getIntent(Context c) {
        return new Intent(c, OldEditTaskListActivity.class);
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list_delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_delete_settings:
                startActivity(EditTaskListActivity.makeLaunchIntent(this));
                return true;
            default:
                finish();
                return super.onOptionsItemSelected(item);
        }
    }

}