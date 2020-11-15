package com.example.practicalparent.model;

public class Task {
    private Child child;
    private String taskName;
    private String desc;

    //Constructor
    public Task(Child child, String taskName, String desc) {
        validateChild(child);
        validateTaskName(taskName);

        this.child = child;
        this.taskName = taskName;
        this.desc = desc == null ? "" : desc;
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

    private void validateChild(Child child){
        if(child == null){
            throw new IllegalArgumentException("Child should not be null");
        }
    }

    private void validateTaskName(String taskName){
        if(taskName == null || taskName.trim().isEmpty()){
            throw new IllegalArgumentException("Task name should not be empty");
        }
    }
}
