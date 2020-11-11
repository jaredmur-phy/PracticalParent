package com.example.practicalparent.model;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class TaskManager implements Iterable<Task> {

    private static TaskManager taskManager;
    private Context context;
    private ArrayList<Task> taskQueue = new ArrayList<>();

    private TaskManager(Context c){
        this.context = c;
    }

    // by calling this function
    // "When a child has had their turn,
    // it automatically advances to the next child's turn for that task next time"
    public void next(){

    }

    public boolean isEmpty(){
        return taskQueue.isEmpty();
    }

    public int getTaskSize(){
        return taskQueue.size();
    }

    public void addTask(Task task){
        taskQueue.add(task);
    }

    public Task getTask(int index){
        return taskQueue.get(index);
    }

    public void removeTask(int index){
        taskQueue.remove(index);
    }

    public static TaskManager getInstance(Context c){
        if(taskManager == null){
            taskManager = new TaskManager(c.getApplicationContext());
        }
        return taskManager;
    }

    @NonNull
    @Override
    public Iterator<Task> iterator() {
        return taskQueue.iterator();
    }
}
