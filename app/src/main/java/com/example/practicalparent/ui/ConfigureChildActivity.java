package com.example.practicalparent.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.example.practicalparent.model.Child;
import com.example.practicalparent.model.ChildManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// add,edit and delete a list of names given to children
public class ConfigureChildActivity extends AppCompatActivity {

    private ChildManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_child);

        manager = ChildManager.getInstance(this);

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


    private void registerClickCallBack() {
        ListView list = findViewById(R.id.id_children_list_view);
        list.setOnItemClickListener((parent, view, position, id) -> launchSaveChild(position));
    }

    private void populateListView() {
        ArrayAdapter<Child> adapter = new ChildListAdapter();
        ListView list = findViewById(R.id.id_children_list_view);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        populateListView();
    }


    private class ChildListAdapter extends ArrayAdapter<Child>{
        public ChildListAdapter(){
            super(ConfigureChildActivity.this,
                    R.layout.childrenitems,
                    manager.getList());
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.childrenitems, parent, false);
            }

            // Find the car to work with.
            Child curChild = manager.get(position);

            // Fill the view
            ImageView childImgView = itemView.findViewById(R.id.id_child_img);
            childImgView.setImageDrawable(curChild.getDrawable(ConfigureChildActivity.this));

            TextView childNameView = itemView.findViewById(R.id.id_child_name);
            childNameView.setText(curChild.getName());

            return itemView;
        }
    }

    private void setupFAB() {
        findViewById(R.id.id_add_child).setOnClickListener(v -> launchSaveChild());
    }

    private void launchSaveChild(){
        startActivity(SaveChildActivity.makeLaunchIntent(ConfigureChildActivity.this));
    }

    private void launchSaveChild(int index){
        startActivity(SaveChildActivity.makeLaunchIntent(ConfigureChildActivity.this, index));
    }

    public static Intent getIntent(Context c) {
        return new Intent(c, ConfigureChildActivity.class);
    }

    public static Intent makeLaunchIntent(Context c) {
        return getIntent(c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_task_settings:
                startActivity(TaskListActivity.makeLaunchIntent(this));
                return true;
            default:
                finish();
                return super.onOptionsItemSelected(item);
        }
    }

}