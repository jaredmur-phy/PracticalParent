package com.example.practicalparent.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.FilenameFilter;
import java.util.Set;
import java.util.TreeSet;

/**
 * help to select the next child to flip the coin
 */
public class ChildSelector {

    private static ChildManager manager;
    private static ChildSelector selector;
    private SharedPreferences sp;

    private static final String FILENAME = "child_selector_record";
    private static final String KEY_RECORD = "KEY_RECORD";

    // deal with remove and edit before select child
    private static Set<String> childNameSet;
    private ChildSelector(Context c){
        manager = ChildManager.getInstance(c);
        sp = c.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        childNameSet = sp.getStringSet(KEY_RECORD, new TreeSet<>());
    }

    public Child getNextChild(){
        if(manager.size() == 0)
            return new Child("");
        for(Child child : manager){
            String childName = child.getName();
            if(!childNameSet.contains(childName)) {
                childNameSet.add(childName);
                write();
                return child;
            }
        }
        // every child is in set, start another iteration
        childNameSet.clear();
        Child child = manager.get(0);
        childNameSet.add(child.getName());
        write();
        return child;
    }

    private void write(){
        sp.edit().putStringSet(KEY_RECORD, childNameSet).apply();
    }

    public static ChildSelector getInstance(Context c){
        if(selector == null){
            selector = new ChildSelector(c);
        }
        return selector;
    }

}
