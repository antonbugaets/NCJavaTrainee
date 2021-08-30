package ua.edu.sumdu.j2se.Bugaetc;

import java.util.*;

public class ArrayTaskList implements ArrayTask {
    private Task[] tasks = new Task[]{};

    public ArrayTaskList() {

    }

    public ArrayTaskList(Task[] tasks) {
        this.tasks = tasks;
    }

    @Override
    public void add(Task task) {
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = task;
    }

    @Override
    public boolean remove(Task task) {
        // Task[] result;
        int j = 0;
        for (int i = 0; i < tasks.length; i++) {
            Task[] result = new Task[tasks.length - 1];
            if (tasks[i].equals(task)) {
                System.arraycopy(tasks, 0, result, 0, i);
                System.arraycopy(tasks, i + 1, result, i, tasks.length - i - 1);
                tasks = result;
                j++;
            }
        }
        if (j > 0) {
            System.out.println("Sum of removed items :" + j);
            return true;
        } else {
            return false;
        }
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
            }
        }
        return null;
    }

    @Override
    public ArrayTaskList incoming(int from, int to) {
        return null;
    }
}
