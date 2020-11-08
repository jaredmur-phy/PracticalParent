package com.example.practicalparent.model;

import android.content.Context;
import android.content.SharedPreferences;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * save the coin flipping history
 * This class MUST be created inside/after "onCreate" function.
 */
public class CoinFlipHistoryManager {

    private static final String FILENAME = "history_file";
    private static final String KEY_SET = "KEY_SET";
    private static final String SUFFIX_CHILD = "_CHILD";
    private static final String SUFFIX_DATE = "_DATE";
    private static final String SUFFIX_GOT_HEAD = "_GOT_HEAD";
    private static final String SUFFIX_PICKED_HEAD = "_PICKED_HEAD";
    private static final int LIMIT = 100; // only save 100 histories

    private static CoinFlipHistoryManager historyManager;

    private ArrayList<CoinFlipHistory> historyList;
    private SharedPreferences sharedPreferences;

    private CoinFlipHistoryManager(Context context){
        historyList = new ArrayList<>();
        sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet(KEY_SET, new TreeSet<>());
        for(String prefix : set){
            String childName = sharedPreferences.getString(prefix+SUFFIX_CHILD, "");
            String date = sharedPreferences.getString(prefix+SUFFIX_DATE, "");
            boolean gotHead = sharedPreferences.getBoolean(prefix+SUFFIX_GOT_HEAD, true);
            int pickedHead = sharedPreferences.getInt(prefix+SUFFIX_PICKED_HEAD, -1);
            historyList.add(new CoinFlipHistory(new Child(childName), pickedHead, gotHead, date));
        }

    }

    public void add(CoinFlipHistory history){
        if(history == null) return;
        if(historyList.size() == LIMIT){
            historyList.remove(0);
        }
        historyList.add(history);
        write();
    }

    public int size(){
        return historyList.size();
    }

    // return which child is picked last
    public Child getLastPickedChild(){
        if(historyList.isEmpty()) return null;
        return historyList.get(0).getChild();
    }

    public CoinFlipHistory get(int index){
        return historyList.get(index);
    }

    private void write(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        TreeSet<String> keySet = new TreeSet<>();
        for(int i = 0; i < historyList.size(); i++){
            CoinFlipHistory history = historyList.get(i);
            editor.putString(i + SUFFIX_CHILD, history.getChild().getName());
            editor.putString(i + SUFFIX_DATE, history.getDate());
            editor.putBoolean(i + SUFFIX_GOT_HEAD, history.isGotHead());
            editor.putInt(i + SUFFIX_PICKED_HEAD, history.getPicked());
            keySet.add(String.valueOf(i));
        }
        editor.putStringSet(KEY_SET, keySet);
        editor.apply();
    }

    public ArrayList<CoinFlipHistory> getHistoryList() {
        return historyList;
    }

    public static CoinFlipHistoryManager getInstance(Context c){
        if(historyManager == null){
            historyManager = new CoinFlipHistoryManager(c.getApplicationContext());
        }
        return historyManager;
    }

}
