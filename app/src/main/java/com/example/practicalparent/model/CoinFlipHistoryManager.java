package com.example.practicalparent.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.practicalparent.util.SerializationUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


/**
 * save the coin flipping history
 * This class MUST be created inside/after "onCreate" function.
 */
public class CoinFlipHistoryManager {

    private static final String FILENAME = "History_File_1";
    private static final String KEY_SET = "KEY_SET";

    private static final int LIMIT = 100; // only save 100 histories

    private static CoinFlipHistoryManager historyManager;

    private ArrayList<CoinFlipHistory> historyList;
    private final SerializationUtil serializationUtil;

    private CoinFlipHistoryManager(Context context) {
        serializationUtil = new SerializationUtil(context.getApplicationContext(), FILENAME);
        historyList = serializationUtil.getObject(KEY_SET, new TypeToken<ArrayList<CoinFlipHistory>>() {
        }.getType(), new ArrayList<>());
    }

    public void add(CoinFlipHistory history) {
        if (history == null) {
            return;
        }
        if (historyList.size() == LIMIT) {
            historyList.remove(0);
        }
        historyList.add(history);
        write();
    }

    public int size() {
        return historyList.size();
    }

    // return which child is picked last
    public Child getLastPickedChild() {
        if (historyList.isEmpty()) {
            return null;
        }
        return historyList.get(0).getChild();
    }

    public CoinFlipHistory get(int index) {
        return historyList.get(index);
    }

    public int getLastIndex() {
        return historyList.size() - 1;
    }

    private void write() {
        serializationUtil.putObject(KEY_SET, historyList);
    }

    public ArrayList<CoinFlipHistory> getHistoryList() {
        return historyList;
    }

    public static CoinFlipHistoryManager getInstance(Context c) {
        if (historyManager == null) {
            historyManager = new CoinFlipHistoryManager(c.getApplicationContext());
        }
        return historyManager;
    }

}
