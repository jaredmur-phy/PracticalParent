package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.example.practicalparent.model.Child;
import com.example.practicalparent.model.ChildManager;
import com.example.practicalparent.model.Task;
import com.example.practicalparent.model.TaskManager;

public class TaskListActivity extends AppCompatActivity {
    private TaskManager manager;
   private ArrayAdapter<Task> adapter;
    private ChildManager childManager;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        childManager = ChildManager.getInstance(this);
        childManager.iterator();
        
        createSpinner();
        
        
        /*populateListView();
        registerClickCallBack();*/
        setToolBar();
    }

    private void createSpinner() {
        spinner = (Spinner) findViewById(R.id.id_choose_child_spinner);
        ArrayAdapter<Child> adapter =
                new ArrayAdapter<Child>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, childManager.getList());
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_task_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

//public int size() {
//        return children.size();
//    }
    
    //public Child get(int i) {
    //        return children.get(i);
    //    }

    private void registerClickCallBack() {
        /*ListView list = findViewById(R.id.id_task_list_view);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updatePosition(position);
                adapter.notifyDataSetChanged();
            }
        });*/
    }

    private void populateListView() {
       /* adapter = new ArrayAdapter<>(
                TaskListActivity.this,
                R.layout.taskitems,
                manager.getList());
        ListView list = findViewById(R.id.id_task_list_view);
        list.setAdapter(adapter);*/
    }

    public void updatePosition(int i) {
       /* dialogBuilder = new AlertDialog.Builder(this);
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
                if (!taskManager.getInstance(taskListActivity.this).isNullOrEmpty(changeName)) {
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
        });*/
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