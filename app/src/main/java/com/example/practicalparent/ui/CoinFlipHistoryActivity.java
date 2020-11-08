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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.practicalparent.R;
import com.example.practicalparent.listener.TextChangedListener;
import com.example.practicalparent.model.CoinFlipHistory;
import com.example.practicalparent.model.CoinFlipHistoryManager;

import java.util.ArrayList;
import java.util.List;

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

    private void setupFilter(){
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

    private void showListView(String name){
        ListView listView = findViewById(R.id.id_history_list);
        List<CoinFlipHistory> historyList = coinFlipHistoryManager.getHistoryList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.items_list,
                parseList(historyList, name.trim()))
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                TextView item = (TextView) super.getView(position,convertView,parent);
                String text = item.getText().toString();
                int index = coinFlipHistoryManager.size()-1-position;
                if(coinFlipHistoryManager.get(index).isWon()){
                    item.setText(Html.fromHtml(text + getString(R.string.won_text)));
                }
                else {
                    item.setText(Html.fromHtml( text + getString(R.string.lost_text)));
                }
                return item;
            }
        };
        listView.setAdapter(adapter);
    }

    public ArrayList<String> parseList(List<CoinFlipHistory> historyList, String name){
        ArrayList<String> list = new ArrayList<>();
        if(name != null) name = name.toLowerCase();
        for(int i = historyList.size()-1; i >=0 ; i--){
            CoinFlipHistory history = historyList.get(i);
            if(name != null && ("".equals(name) || name.isEmpty() ||
                    history.getChild().getName().toLowerCase().contains(name))){
                String sb = history.getDate() + " <br>";
                if(history.isPicked()){
                    sb += "<b>" +
                            history.getChild().getName() +
                            "</b> picked <b>" + parseBoolToHead(history.isPickedHead()) +
                            "</b>";
                }
                sb += " got <i>" + parseBoolToHead(history.isGotHead()) + "</i>";
                list.add(sb);
            }
        }
        return list;
    }

    private String parseBoolToHead(boolean isHead){
        if(isHead) return "Head";
        return "Tail";
    }

    public static Intent makeLaunchIntent(Context c){
        return new Intent(c, CoinFlipHistoryActivity.class);
    }

}