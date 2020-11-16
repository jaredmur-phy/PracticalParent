package com.example.practicalparent.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task {
    private List<Child> childList;
    private String taskName;
    private String desc;

    //Constructor
    public Task(List<Child> childList, String taskName, String desc) {
        validateTaskName(taskName);

        this.childList = new ArrayList<>();
        this.childList.addAll(childList);
        Collections.reverse(childList);

        this.taskName = taskName;
        this.desc = desc == null ? "" : desc;
    }

    // return the current Child in the list
    public Child peekChild(){
        if(isDone()) return null;
        return childList.get(childList.size() - 1);
    }

    // move to the next child
    public void moveNext() {
        if(isDone()) return;
        childList.remove(childList.size() - 1);
    }

    public boolean isDone(){
        return childList.isEmpty();
    }



    public String getTaskName() {
        return taskName;
    }

    public String getDesc() {
        return desc;
    }


    private void validateTaskName(String taskName){
        if(taskName == null || taskName.trim().isEmpty()){
            throw new IllegalArgumentException("Task name should not be empty");
        }
    }
}
