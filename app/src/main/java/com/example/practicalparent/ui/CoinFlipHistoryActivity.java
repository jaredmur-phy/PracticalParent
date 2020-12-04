package com.example.practicalparent.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.example.practicalparent.listener.TextChangedListener;
import com.example.practicalparent.model.Child;
import com.example.practicalparent.model.CoinFlipHistory;
import com.example.practicalparent.model.CoinFlipHistoryManager;

import java.util.ArrayList;
import java.util.List;

// show the coin flipping history, "when" "who" picked "what" and got "what".
public class CoinFlipHistoryActivity extends AppCompatActivity {

    private CoinFlipHistoryManager coinFlipHistoryManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_flip_history);

        coinFlipHistoryManager = CoinFlipHistoryManager.getInstance(this);
        setToolBar();
        showListView();
        setupFilter();
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.id_coin_flip_history_tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void setupFilter() {
        EditText nameTextBox = findViewById(R.id.id_child_name_history_filter);
        nameTextBox.addTextChangedListener(new TextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                showListView(s.toString());
            }
        });
    }

    private void showListView() {
        showListView("");
    }

    private void showListView(String name) {
        ArrayAdapter<CoinFlipHistory> adapter = new HistoryListAdapter(parseList(
                coinFlipHistoryManager.getHistoryList(), name));
        ListView list = findViewById(R.id.id_history_list);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private class HistoryListAdapter extends ArrayAdapter<CoinFlipHistory> {

        List<CoinFlipHistory> historyList;

        public HistoryListAdapter(List<CoinFlipHistory> historyList) {
            super(CoinFlipHistoryActivity.this,
                    R.layout.childrenitems, historyList);
            this.historyList = historyList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.childrenitems, parent, false);
            }
            CoinFlipHistory history = historyList.get(position);
            Child curChild = history.getChild();

            // Fill the view
            if (curChild.hasDrawable()) {
                ImageView childImgView = itemView.findViewById(R.id.id_child_img);
                childImgView.setImageDrawable(curChild.getDrawable(CoinFlipHistoryActivity.this));
            }
            TextView textView = itemView.findViewById(R.id.id_child_name);
            if (history.isWon()) {
                textView.setText(Html.fromHtml(history.toString() + getString(R.string.won_text)));
            } else {
                textView.setText(Html.fromHtml(history.toString() + getString(R.string.lost_text)));
            }
            return itemView;
        }
    }

    public ArrayList<CoinFlipHistory> parseList(List<CoinFlipHistory> historyList, String name) {
        ArrayList<CoinFlipHistory> list = new ArrayList<>();
        if (name != null) {
            name = name.toLowerCase();
        }
        for (int i = historyList.size() - 1; i >= 0; i--) {
            CoinFlipHistory history = historyList.get(i);
            if (name != null && ("".equals(name) || name.isEmpty() ||
                    history.getChild().getName().toLowerCase().contains(name))) {
                list.add(history);
            }
        }
        return list;
    }


    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, CoinFlipHistoryActivity.class);
    }

}