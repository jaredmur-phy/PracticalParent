package com.example.practicalparent.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.practicalparent.model.PickedConstant;

public class SelectChildActivity extends AppCompatActivity {

    private ChildManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_child);
        setToolBar();

        setTitle(getString(R.string.select_child));
        manager = ChildManager.getInstance(this);
        hideBtn();

        populateListView();
        registerClickCallBack();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_configure_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
    }

    private void hideBtn(){
        findViewById(R.id.id_add_child).setVisibility(View.INVISIBLE);
    }

    private void registerClickCallBack() {
        ListView list = findViewById(R.id.id_children_list_view);
        list.setOnItemClickListener((parent, view, position, id) -> {
            startActivity(ChooseCoinActivity.getIntent(SelectChildActivity.this, manager.getOrder((int) id)));
            finish();
        });
    }

    private void populateListView() {
        ArrayAdapter<Child> adapter = new ChildListAdapter();
        ListView list = findViewById(R.id.id_children_list_view);
        list.setAdapter(adapter);
    }

    private class ChildListAdapter extends ArrayAdapter<Child>{
        public ChildListAdapter(){
            super(SelectChildActivity.this,
                    R.layout.childrenitems,
                    manager.getOrderList());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.childrenitems, parent, false);
            }

            Child curChild = manager.getByOrder(position);

            // Fill the view
            ImageView childImgView = itemView.findViewById(R.id.id_child_img);
            childImgView.setImageDrawable(curChild.getDrawable(SelectChildActivity.this));

            TextView childNameView = itemView.findViewById(R.id.id_child_name);
            childNameView.setText(curChild.getName());

            return itemView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.select_no_child_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_select_nobody:
                startActivity(FlipResultsActivity.getIntent(SelectChildActivity.this,
                        PickedConstant.NOT_PICKED,-1));
                finish();
        }

        return true;
    }

    public static Intent getIntent(Context c) {
        return new Intent(c, SelectChildActivity.class);
    }
}
