package com.example.practicalparent.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.practicalparent.R;
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
        showListView();
    }

    private void showListView(){
        ListView listView = findViewById(R.id.id_history_list);
        List<CoinFlipHistory> historyList = coinFlipHistoryManager.getHistoryList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.items_list, parseList(historyList))
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                TextView item = (TextView) super.getView(position,convertView,parent);
                String text = item.getText().toString();
                if(coinFlipHistoryManager.get(position).isWon()){
                    item.setText(Html.fromHtml(text + "<br><font color='#00ff00'> WON!!</font>"));
                }
                else {
                    item.setText(Html.fromHtml( text + "<br><font color='#ff0000'> LOOSE!!</font>"));
                }
                return item;
            }
        };
        listView.setAdapter(adapter);
    }

    public ArrayList<String> parseList(List<CoinFlipHistory> historyList){
        ArrayList<String> list = new ArrayList<>();
        for(int i = historyList.size()-1; i>=0; i--){
            CoinFlipHistory history = historyList.get(i);
            String sb = history.getDate() + " <br><b>" +
                    history.getChild().getName() +
                    "</b> picked <b>" + parseBoolToHead(history.isPickedHead()) +
                    "</b> got <i>" + parseBoolToHead(history.isGotHead()) + "</i>";
            list.add(sb);
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