package com.example.practicalparent.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.practicalparent.util.SerializationUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TaskManager implements Iterable<Task> {

    private static TaskManager taskManager;
    private final SerializationUtil serializationUtil;
    private final ArrayList<Task> taskQueue;

    private static final String FILE_NAME = "task_list";
    private List<Task> checked = new ArrayList<>();

    private TaskManager(Context c) {
        serializationUtil = new SerializationUtil(c, FILE_NAME);
        taskQueue = serializationUtil.getObject(FILE_NAME,
                new TypeToken<List<Task>>() {
                }.getType(), new ArrayList<>());
    }

    private void write() {
        serializationUtil.putObject(FILE_NAME, taskQueue);
    }

    // get the nextTask in the queue
    // return null if no task in the queue
    // not change the task list
    public Task peekTask() {
        if (taskQueue.isEmpty()) {
            return null;
        }
        return taskQueue.get(0);
    }

    public List<Task> checkBoxTask() {
        return checked;
    }

    public void selectTask(Task selectedTask) {
        checked.add(selectedTask);

    }

    public void unselectTask(Task selectedTask) {
        checked.remove(selectedTask);
    }

    public boolean deselectTask(Task selectedTask) {
        return checked.contains(selectedTask);
    }

    public int selectedTaskSize() {
        return checked.size();
    }

    public void deleteSelectedTasks(List<Task> selectedTask) {
        for (Task task : selectedTask) {
            taskManager.getList().remove(task);
            taskManager.update();
        }
    }


    public boolean checkTaskName(String name) {
        for (int i = 0; i < getTaskSize(); i++) {
            if (getTask(i).getTaskName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTaskDesc(String name) {
        for (int i = 0; i < getTaskSize(); i++) {
            if (getTask(i).getDesc().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    public int getTaskSize() {
        return taskQueue.size();
    }

    public void addTask(Task task) {
        taskQueue.add(task);
        write();
    }

    public void update() {
        write();
    }

    public Task getTask(int index) {
        return taskQueue.get(index);
    }

    public void removeTask(int index) {
        taskQueue.remove(index);
        write();
    }

    public void changeTaskName(int index, String newName) {
        taskQueue.get(index).setTaskName(newName);
        write();
    }

    public void changeTaskDesc(int index, String newName) {
        taskQueue.get(index).setTaskDesc(newName);
        write();
    }

    public List<Task> getList() {
        return taskQueue;
    }

    public static TaskManager getInstance(Context c) {
        if (taskManager == null) {
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
