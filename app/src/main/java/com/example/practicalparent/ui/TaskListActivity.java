package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.List;

public class TaskListActivity extends AppCompatActivity {
    private TaskManager taskManager;
   private ArrayAdapter<Task> adapter;
    private ChildManager childManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

//
        taskManager = TaskManager.getInstance(this);
        childManager = ChildManager.getInstance(this);

        setupFAB();
        populateListView();
        registerClickCallBack();
        setToolBar();
    }

    private void setupFAB() {

        FloatingActionButton fab = findViewById(R.id.id_save_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = ((TextInputEditText) findViewById(R.id.id_enter_task)).getText().toString();

                String taskDescription = ((TextInputEditText) findViewById(R.id.id_enter_description)).getText().toString();

                Task task = new Task(childManager.getInstance(TaskListActivity.this).getList(),taskName,taskDescription);

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

                TextInputEditText clearTask = findViewById(R.id.id_enter_task);
                TextInputEditText clearTaskDescription = findViewById(R.id.id_enter_description);
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
        });
    }

    private void populateListView() {

        adapter = new ArrayAdapter<>(
                TaskListActivity.this,
                R.layout.taskitems,
                //fix
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



    private void registerClickCallBack() {
        /*ListView list = findViewById(R.id.id_task_list_view);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showID(position);
                adapter.notifyDataSetChanged();
            }
        });*/
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