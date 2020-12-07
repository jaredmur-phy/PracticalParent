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

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.practicalparent.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

// Can edit tasks; delete or update
public class EditTaskListActivity extends AppCompatActivity {
    private TaskManager taskManager;
    private ArrayAdapter<Task> adapter;
    private ChildManager childManager;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private CheckBox checkBox;
    private Toolbar toolbar;

    private ActionMode actionMode = null;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_list);

        taskManager = TaskManager.getInstance(this);
        childManager = ChildManager.getInstance(this);

        setToolBar();
        populateListView();
        registerClickCallBack();

    }

    private void showID(int i) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View changePopup = getLayoutInflater().inflate(R.layout.update_popup, null);
        dialogBuilder.setView(changePopup);
        dialog = dialogBuilder.create();
        dialog.show();

        Button saveBtn = changePopup.findViewById(R.id.id_save_task_button);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String changeTaskName = ((EditText) changePopup.findViewById(R.id.id_update_task_name)).getText().toString();
                String changeTaskDesc = ((EditText) changePopup.findViewById(R.id.id_update_task_desc)).getText().toString();

                if (changeTaskName.trim().isEmpty() && changeTaskDesc.trim().isEmpty()) {
                    StyleableToast.makeText(EditTaskListActivity.this, getString(R.string.task_is_empty),
                            R.style.errorToast).show();

                } else if (taskManager.getInstance(EditTaskListActivity.this).checkTaskDesc(changeTaskDesc) &&
                        taskManager.getInstance(EditTaskListActivity.this).checkTaskName(changeTaskName)) {
                    // duplicate name
                    StyleableToast.makeText(EditTaskListActivity.this, getString(R.string.both_task_is_same),
                            R.style.errorToast).show();

                    return;
                } else if (taskManager.getInstance(EditTaskListActivity.this).checkTaskName(changeTaskName)) {
                    // duplicate name
                    StyleableToast.makeText(EditTaskListActivity.this, getString(R.string.task_is_same),
                            R.style.errorToast).show();
                    return;
                } else if (taskManager.getInstance(EditTaskListActivity.this).checkTaskDesc(changeTaskDesc)) {
                    // duplicate name
                    StyleableToast.makeText(EditTaskListActivity.this, getString(R.string.task_desc_is_same),
                            R.style.errorToast).show();
                    return;
                } else if (!changeTaskName.trim().isEmpty() && changeTaskDesc.trim().isEmpty()) {
                    TaskManager.getInstance(EditTaskListActivity.this).changeTaskName(i, changeTaskName);
                } else if (changeTaskName.trim().isEmpty() && !changeTaskDesc.trim().isEmpty()) {
                    TaskManager.getInstance(EditTaskListActivity.this).changeTaskDesc(i, changeTaskDesc);
                } else {
                    TaskManager.getInstance(EditTaskListActivity.this).changeTaskName(i, changeTaskName);
                    TaskManager.getInstance(EditTaskListActivity.this).changeTaskDesc(i, changeTaskDesc);
                }
                adapter.notifyDataSetChanged();


                dialog.dismiss();
            }
        });

        Button cancelBtn = changePopup.findViewById(R.id.id_cancel_update_button);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        populateListView();
    }

    private void setToolBar() {
        toolbar = findViewById(R.id.id_edit_task_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void toolBarVisibility() {
        if (toolbar.getVisibility() == View.VISIBLE) {
            toolbar.setVisibility(View.INVISIBLE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    private void checkBoxVisibility() {
        if (toolbar.getVisibility() != View.VISIBLE) {
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }
    }

    private void populateListView() {
        adapter = new EditTaskListAdapter();
        list = findViewById(R.id.id_edit_task_list);
        list.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        list.setMultiChoiceModeListener(modeListener);
        list.setAdapter(adapter);
    }

    private void registerClickCallBack() {
        ListView list = findViewById(R.id.id_edit_task_list);
        list.setOnItemClickListener((parent, view, position, id) -> showID(position));
    }


    private class EditTaskListAdapter extends ArrayAdapter<Task> {
        public EditTaskListAdapter() {
            super(EditTaskListActivity.this,
                    R.layout.edit_task_items,
                    taskManager.getList());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.edit_task_items, parent, false);
            }

            Task curTask = taskManager.getTask(position);

            // Fill the view
            ImageView childImgView = itemView.findViewById(R.id.id_edit_task_child_img);
            childImgView.setImageDrawable(curTask.peekChild().getDrawable(EditTaskListActivity.this));

            TextView taskNameView = itemView.findViewById(R.id.id_task);
            taskNameView.setText(curTask.toString());

            checkBox = itemView.findViewById(R.id.id_check_box);
            checkBox.setTag(position);

            checkBoxVisibility();

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (taskManager.deselectTask(curTask)) {
                        taskManager.unselectTask(curTask);
                    } else {
                        taskManager.selectTask(curTask);
                    }
                    actionMode.setTitle(taskManager.selectedTaskSize() + " tasks selected:");
                }
            });

            return itemView;
        }
    }

    private void selectAllItems() {
        for (int i = 0; i < list.getCount(); i++) {
            View v = list.getChildAt(i);
            checkBox = v.findViewById(R.id.id_check_box);
            checkBox.setChecked(true);
        }
    }

    private void unselectAllItems() {
        for (int i = 0; i < list.getCount(); i++) {
            View v = list.getChildAt(i);
            checkBox = v.findViewById(R.id.id_check_box);
            checkBox.setChecked(false);
        }
    }

    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Works by long click
            toolBarVisibility();
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.task_list_menu_deleter, menu);
            actionMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {

                case R.id.action_trash_can:
                    taskManager.deleteSelectedTasks(taskManager.checkBoxTask());
                    adapter.notifyDataSetChanged();
                    mode.finish();
                    return true;

                case R.id.id_action_checkbox:
                    if (!item.isChecked()) {
                        item.setChecked(true);
                        item.setIcon(R.drawable.ic_baseline_check_box_on);
                        selectAllItems();
                    } else {
                        item.setChecked(false);
                        item.setIcon(R.drawable.ic_baseline_check_box_off);
                        unselectAllItems();
                    }
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            unselectAllItems();
            toolBarVisibility();
            checkBoxVisibility();
            actionMode = null;
        }
    };

    @Override
    public void onBackPressed() {
        unselectAllItems();
        super.onBackPressed();
    }

    public static Intent getIntent(Context c) {
        return new Intent(c, EditTaskListActivity.class);
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