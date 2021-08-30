package ua.edu.sumdu.j2se.Bugaetc;

import java.util.*;

public class ArrayTaskList implements ArrayTask {
    private Task[] tasks;

    public ArrayTaskList() {

    }

    public ArrayTaskList(Task[] tasks) {
        this.tasks = tasks;
    }

    @Override
    public void add(Task task) {
        tasks[tasks.length] = task;
    }

    @Override
    public boolean remove(Task task) {
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i].equals(task)) {
                tasks[i] = null;
                tasks[i] = tasks[i + 1];
                return true;
            } else continue;

        }
        return false;
    }

    @Override
    public int size() {
        return tasks.length;
    }

    @Override
    public Task getTask(int index) {
        for (int i = 0; i < tasks.length; i++) {
            if (index == i) {
                return tasks[i];
            } else {
                continue;

            }
        }
        return null;
    }
}
