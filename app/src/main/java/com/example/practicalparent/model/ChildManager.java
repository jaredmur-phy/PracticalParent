package com.example.practicalparent.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.practicalparent.util.SerializationUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * manage child list, can save data to the disk
 */
public class ChildManager implements Iterable<Child> {

    private static final String FILENAME = "CHILD_LIST_1";
    private static final String KEY = "child_list";
    private final SerializationUtil serializationUtil;

    private List<Child> children;

    //Singleton support
    private static ChildManager instance;

    private ChildManager(Context c) {
        serializationUtil = new SerializationUtil(c, FILENAME);
        children = serializationUtil.getObject(KEY,
                new TypeToken<List<Child>>(){}.getType(), new ArrayList<>());
    }

    private void write() {
        serializationUtil.putObject(KEY, children);
    }

    public static ChildManager getInstance(Context context) {
        if (instance == null) {
            instance = new ChildManager(context.getApplicationContext());
        }
        return instance;
    }

    public void addChild(Child child) {
        children.add(child);
        write();
    }

    public List<Child> getList() {
        return children;
    }

    public Child get(int i) {
        return children.get(i);
    }

    public void removeChild(int index) {
        children.remove(index);
        write();
    }

    public boolean findChild(String name) {
        boolean anyDuplicates = false;
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getName().equals(name)) {
                anyDuplicates = true;
            }
        }
        return anyDuplicates;
    }

    //public void storeDesc(String )

    public void changeName(int index, String newName) {
        children.get(index).setName(newName);
        write();
    }

    public void updateChild(int index, Child child){
        children.set(index, child);
        write();
    }

    //code taken from: https://www.programiz.com/java-programming/examples/string-empty-null
    public boolean isNullOrEmpty(String str) {
        if (str != null && !str.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    public int size() {
        return children.size();
    }

    public boolean isEmpty() {
        return children.isEmpty();
    }

    @Override
    public Iterator<Child> iterator() {
        return children.iterator();
    }
}
