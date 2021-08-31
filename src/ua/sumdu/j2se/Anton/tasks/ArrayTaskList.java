package ua.sumdu.j2se.Anton.tasks;

import java.util.*;

public class ArrayTaskList implements ArrayTask {
    private Task[] tasks = new Task[]{};

    public ArrayTaskList() {

    }

    public ArrayTaskList(Task[] tasks) {
        this.tasks = tasks;
    }

    /**
     * void add (Task task) is a method that adds the specified task to the list.
     */
    @Override
    public void add(Task task) {
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = task;
    }


    /**
     * boolean remove (Task task)is a method that removes a task from the list and returns true,
     * if such a task was in the list. If the list contains several tasks of the same type, any of them should be removed.
     */

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

    /**
     * int size() is a method that returns several tasks from the list .
     */

    @Override
    public int size() {
        //take Test[] lenght
        return tasks.length;
    }

    /**
     * Task getTask(int index) is a method that returns a task which takes the specified place in the list;
     * the index of the first task is 0.
     */

    @Override
    public Task getTask(int index) {
        for (int i = 0; i < tasks.length; i++) {
            if (index == i) {
                return tasks[i];
            }
        }
        return null;
    }

    /**
     * Besides, the application should know which tasks from the list are scheduled at least once in a certain interval,
     * for example, which tasks are scheduled for the next week. To implement this, create the ArrayTaskList incoming(int from, int to)
     * method in the ArrayTaskList class. This method returns a subset of tasks that are scheduled for execution at least once
     * after the "from" time, and not later than the "to" time.
     */

    @Override
    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList result = new ArrayTaskList();

        for (int i = 0; i < tasks.length; i++) {
            //for non - repeatable task's:
            if (!tasks[i].isRepeated()) {
                if (tasks[i].getTime() < to && tasks[i].getTime() > from) {
                    result.add(tasks[i]);
                }
            }
            //for  repeatable task's:
            if (tasks[i].isRepeated()) {
                for (int j = from; j <= to; j++) {
                    if (tasks[i].nextTimeAfter(j) <= to && tasks[i].nextTimeAfter(j) != -1) {
                        result.add(tasks[i]);
                        break;
                    }
                }
            }
        }
        return result;
    }
}
