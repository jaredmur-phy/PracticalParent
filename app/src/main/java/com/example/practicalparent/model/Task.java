package com.example.practicalparent.model;

public class Task {
    private Child child;
    private String taskName;
    private String desc;

    //Constructor
    public Task(Child child, String taskName, String desc) {

        this.child = child;
        this.taskName = taskName;
        this.desc = desc;
    }

    public Child getChild(){
        return child;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDesc() {
        return desc;
    }
}
