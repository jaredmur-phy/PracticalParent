package com.example.practicalparent.childmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChildManager implements Iterable<Child>{
    public int size;
    private List<Child> children = new ArrayList<>();

    //Singleton support
    private static ChildManager instance;
    private ChildManager() {

    }

    public static ChildManager getInstance() {
        if(instance == null) {
            instance = new ChildManager();
        }

        return instance;
    }

    public void addChild(Child child) {
        children.add(child);
        size++;
    }

    public List<Child> getList() {
        return children;
    }

    @Override
    public Iterator<Child> iterator() {
        return children.iterator();
    }

    public void removeChild(int index) {
        children.remove(index);
    }

    public void changeName(int index, String newName) {
        children.get(index).firstName = newName;
    }

    public int size() {
        return size;
    }
}
