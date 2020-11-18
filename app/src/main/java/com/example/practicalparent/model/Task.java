package com.example.practicalparent.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task {
    private List<Child> childList;
    private String taskName;
    private String desc;
    private int index;

    //Constructor
    public Task(List<Child> childList, String taskName, String desc) {
        validateTaskName(taskName);

        this.childList = new ArrayList<>();
        this.childList.addAll(childList);
        Collections.shuffle(this.childList);

        this.index = 0;
        this.taskName = taskName;
        this.desc = desc == null ? "" : desc;
    }

    // return the current Child in the list
    public Child peekChild(){
        if(isEmpty()) return null;
        return childList.get(index);
    }

    // move to the next child
    public void moveNext() {
        if(isEmpty()) return;
        index ++;
        index = index % childList.size();
    }

    public void nextChild(){
    }
    public boolean isEmpty(){
        return childList.isEmpty();
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDesc() {
        return desc;
    }

    public void setTaskName(String name) {
        this.taskName = name;
    }

    public void setTaskDesc(String name) {
        this.desc = name;
    }

    private void validateTaskName(String taskName){
        if(taskName == null || taskName.trim().isEmpty()){
            throw new IllegalArgumentException("Task name should not be empty");
        }
    }

    public String toString() {
        return "Task: " + taskName + " " + peekChild();
    }

}
