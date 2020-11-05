package com.example.practicalparent.model;

import android.content.Context;

import java.util.Set;
import java.util.TreeSet;

/**
 * help to select the next child to flip the coin
 */
public class ChildSelector {

    private static ChildManager manager;
    private static ChildSelector selector;

    // deal with remove and edit before select child
    private static Set<String> childNameSet = new TreeSet<>();
    private ChildSelector(Context c){
        manager = ChildManager.getInstance(c);
    }

    public Child getNextChild(){
        if(manager.size() == 0)
            return new Child("");
        for(Child child : manager){
            String childName = child.getName();
            if(!childNameSet.contains(childName)) {
                childNameSet.add(childName);
                return child;
            }
        }
        // every child is in set, start another iteration
        childNameSet.clear();
        Child child = manager.get(0);
        childNameSet.add(child.getName());
        return child;
    }

    public ChildSelector getInstance(Context c){
        if(selector == null){
            selector = new ChildSelector(c);
        }
        return selector;
    }

}
