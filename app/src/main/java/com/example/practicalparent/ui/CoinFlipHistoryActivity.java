package com.example.practicalparent.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.practicalparent.R;
import com.example.practicalparent.childmodel.CoinFlipHistoryManager;

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
        List<String> items = coinFlipHistoryManager.getList();
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.items_list, items)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                TextView item = (TextView) super.getView(position,convertView,parent);
                if(coinFlipHistoryManager.get(position).isWon()){
                    item.setTextColor(Color.parseColor("#00FF0F"));
                }
                else {
                    item.setTextColor(Color.parseColor("#FF000F"));
                }
                return item;
            }
        };
        listView.setAdapter(adapter);
    }

    public static Intent makeLaunchIntent(Context c){
        return new Intent(c, CoinFlipHistoryActivity.class);
    }

}