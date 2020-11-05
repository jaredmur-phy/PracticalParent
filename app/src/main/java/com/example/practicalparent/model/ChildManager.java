package com.example.practicalparent.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ChildManager implements Iterable<Child>{

    private static final String FILENAME = "children";
    private static final String KEY_SET = "KEY_SET";
    private static final String SUFFIX_NAME = "_NAME";

    private List<Child> children = new ArrayList<>();
    private SharedPreferences sp;

    //Singleton support
    private static ChildManager instance;
    private ChildManager(Context c) {
        sp = c.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        // read from disk
        Set<String> keys = sp.getStringSet(KEY_SET, new TreeSet<String>());
        for(String key: keys){
            String name = sp.getString(key + SUFFIX_NAME, "");
            children.add(new Child(name));
        }
    }

    private void write(){
        SharedPreferences.Editor editor = sp.edit();
        TreeSet<String> keySet = new TreeSet<>();
        for(int i = 0; i < children.size(); i++){
            editor.putString(i + SUFFIX_NAME, children.get(i).getName());
            keySet.add(String.valueOf(i));
        }
        editor.putStringSet(KEY_SET, keySet);
        editor.apply();
    }

    public static ChildManager getInstance(Context context) {
        if(instance == null) {
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

    @Override
    public Iterator<Child> iterator() {
        return children.iterator();
    }

    public Child get(int i) {
        return children.get(i);
    }

    public void removeChild(int index) {
        children.remove(index);
        write();
    }

    public void changeName(int index, String newName) {
        children.get(index).setName(newName);
        write();
    }

    public int size() {
        return children.size();
    }

}
