package com.example.practicalparent.model;

public class Task {
    private String firstName;
    private String task;

    //Constructor
    public Task(String task, String firstName) {
        if (task == null || firstName == null) {
            throw new IllegalArgumentException("Please add something");
        }

        this.firstName = firstName;
        this.task = task;
    }

}
