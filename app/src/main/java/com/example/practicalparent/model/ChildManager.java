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

    private static final String FILE_NAME = "CHILD_LIST_0";
    private static final String CHILDREN_KEY = "child_list";
    private static final String ORDER_KEY = "order_list";
    private final SerializationUtil serializationUtil;

    private List<Child> children;

    // save the order of child list
    // for example:
    //      children: aa, bb, cc
    //      order   : 2, 0, 1
    // then the order to flip the coin is : cc, aa, bb
    private List<Integer> order;


    //Singleton support
    private static ChildManager instance;

    private ChildManager(Context c) {
        serializationUtil = new SerializationUtil(c, FILE_NAME);
        children = serializationUtil.getObject(CHILDREN_KEY,
                new TypeToken<List<Child>>() {
                }.getType(),
                new ArrayList<>());
        order = serializationUtil.getObject(ORDER_KEY,
                new TypeToken<List<Integer>>() {
                }.getType(),
                new ArrayList<>());
    }

    private void write() {
        writeChildren();
        writeOrder();
    }

    private void writeChildren() {
        serializationUtil.putObject(CHILDREN_KEY, children);
    }

    private void writeOrder() {
        serializationUtil.putObject(ORDER_KEY, order);
    }

    public static ChildManager getInstance(Context context) {
        if (instance == null) {
            instance = new ChildManager(context.getApplicationContext());
        }
        return instance;
    }

    public boolean isValidIndex(int i) {
        return i >= 0 && i < children.size();
    }

    // return a child, which is first in the order
    public Child peek() {
        if (children.isEmpty()) {
            return null;
        }
        return children.get(order.get(0));
    }

    // return a childIndex, which is first in the order
    public int peekOrder() {
        if (order.isEmpty()) {
            return -1;
        }
        return order.get(0);
    }

    public void addChild(Child child) {
        children.add(child);
        order.add(children.size() - 1);
        write();
    }

    public List<Child> getList() {
        return children;
    }

    public List<Child> getOrderList() {
        ArrayList<Child> list = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            list.add(children.get(order.get(i)));
        }
        return list;
    }

    public Child get(int i) {
        if (i < 0 || i >= children.size()) {
            return new Child("");
        }
        return children.get(i);
    }

    public int getLastIndex() {
        return children.size() - 1;
    }

    public Child getByOrder(int orderIndex) {
        return children.get(order.get(orderIndex));
    }

    public int getOrder(int orderIndex) {
        return order.get(orderIndex);
    }

    public void removeChild(int childIndex) {
        children.remove(childIndex);
        order.remove((Integer) childIndex);

        // update order list
        for (int i = 0; i < order.size(); i++) {
            int oldIndex = order.get(i);
            if (oldIndex > childIndex) {
                order.set(i, oldIndex - 1);
            }
        }

        write();
    }

    public Child select(int childIndex) {
        if (childIndex < 0 || childIndex >= order.size()) {
            return new Child("");
        }

        int orderIndex = order.indexOf(childIndex);
        order.remove(orderIndex);
        order.add(childIndex);
        writeOrder();
        return children.get(childIndex);
    }

    public boolean checkChildName(String name) {
        return checkChildName(name, -1);
    }

    // return true if children contains name in other index
    public boolean checkChildName(String name, int index) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getName().equals(name) && i != index) {
                return true;
            }
        }
        return false;
    }

    public void changeName(int index, String newName) {
        children.get(index).setName(newName);
        write();
    }

    public void updateChild(int index, Child child) {
        children.set(index, child);
        write();
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
