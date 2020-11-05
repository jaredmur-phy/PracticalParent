package com.example.practicalparent.childmodel;

public class Child {
    public String firstName;

    //Constructor
    public Child(String name) {
        if(name == null) {
            throw new IllegalArgumentException("Null child name");
        }

        this.firstName = name;
    }

    public void setName(String name) {
        this.firstName = name;
    }

    public String getName() {
        return this.firstName;
    }

    public String toString() {
        return "Name: " + firstName;
    }
}
