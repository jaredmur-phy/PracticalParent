package com.example.practicalparent.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TaskManager implements Iterable<Task> {

    private static TaskManager taskManager;
    private SharedPreferences sp;
    private ArrayList<Task> taskQueue = new ArrayList<>();

    private static final String FILE_NAME = "task_list";

    private TaskManager(Context c){
        this.sp = c.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    // by calling this function
    // "When a child has had their turn,
    // it automatically advances to the next child's turn for that task next time"
    public void moveNext(){
        if(!taskQueue.isEmpty()) {
            taskQueue.remove(0);
        }
    }

    // get the nextTask in the queue
    // return null if no task in the queue
    public Task peekTask(){
        if(taskQueue.isEmpty()){
            return null;
        }
        return taskQueue.get(0);
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

    public List<Task> getList(){
        return taskQueue;
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
