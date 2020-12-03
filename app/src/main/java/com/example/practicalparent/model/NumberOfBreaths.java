package com.example.practicalparent.model;

public class NumberOfBreaths {


    private int breaths;

    private static NumberOfBreaths instance;

    private NumberOfBreaths() {
    }

    public static NumberOfBreaths getInstance() {
        if (instance == null) {
            instance = new NumberOfBreaths();
        }
        return instance;
    }

    public int getBreaths() {

   return breaths;

    }


    public void setBreaths(int breaths){
        this.breaths = breaths;
    }




}