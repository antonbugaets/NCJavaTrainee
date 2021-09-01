package ua.sumdu.j2se.Anton.tasks;

import java.util.*;

public class ArrayTaskList implements ArrayTask {
    private Task[] tasks = new Task[]{};

    public ArrayTaskList() { //default constructor

    }

    public ArrayTaskList(Task[] tasks) { // constructor with parameter's
        this.tasks = tasks;
    }

    /**
     * void add (Task task) is a method that adds the specified task to the list.
     */
    @Override
    public void add(Task task) {
        tasks = Arrays.copyOf(tasks, tasks.length + 1); //make tasks length + 1
        tasks[tasks.length - 1] = task; // set task to empty place in tasks
    }


    /**
     * boolean remove (Task task)is a method that removes a task from the list and returns true,
     * if such a task was in the list. If the list contains several tasks of the same type, any of them should be removed.
     */
    @Override
    public boolean remove(Task task) { //right version
        boolean b = false;
        ArrayTaskList result = new ArrayTaskList();
        for (int i = 0; i < this.size(); i++) {
            if (!task.equals(tasks[i])) {
                result.add(tasks[i]);
                tasks = result.tasks;
                b = true;
            }
        }
        return b;
    }


    /**
     * int size() is a method that returns several tasks from the list .
     */

    @Override
    public int size() {
        //take Test[] length
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
        for (int i = 0; i < this.size(); i++) {
            //after the "from" time, and not later than the "to" time
            for (int j = from + 1; j <= to; j++) {
                //calling "nextTimeAfter" met from "Task" class, If one task execution is in the interval ( from - to ):
                if (tasks[i].nextTimeAfter(j) <= to && tasks[i].nextTimeAfter(j) != -1) {
                    //Then we add this task to the result array
                    result.add(tasks[i]);
                    //add this task and break invested "for-j";
                    break;
                }
            }
        }
        return result;
    }


    /*
    public boolean removeOneTask(Task task) {
        //get index from original array
        for (int i = 0; i < tasks.length; i++) {
            // Array which will contain the result.
            Task[] result = new Task[tasks.length - 1];
            //equals - overridden met in Task class
            if (tasks[i].equals(task)) {
                // Copy the elements at the left of the index.
                System.arraycopy(tasks, 0, result, 0, i);
                // Copy the elements at the right of the index.
                System.arraycopy(tasks, i + 1, result, i, tasks.length - i - 1);
                //assignment result to original array
                tasks = result;
                // if one element added return true
                return true;
            }
        }
        // if no one element's added return false
        return false;
    }


    @Override
    public boolean remove(Task task) {
        int count = 0;
        //calling helpful met "removeOneTask", while removeOneTask return's true count will count++
        while (removeOneTask(task)) {
            count++;
        }
        return count > 0;
    }

     */

   /* public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList result = new ArrayTaskList();

        for (int i = 0; i < tasks.length; i++) {
            //for non - repeatable task's:
            if (!tasks[i].isRepeated()) {
                //calling "getTime" met from Task Class, If task's time is in the interval ( from - to ):
                if (tasks[i].getTime() <= to && tasks[i].getTime() > from) {
                    //Then we add this task to the result array
                    result.add(tasks[i]);
                }
            }

            //for  repeatable task's:
            if (tasks[i].isRepeated()) {
                for (int j = from; j <= to; j++) {
                    //calling "nextTimeAfter" met from Task Class, If one task execution is in the interval ( from - to ):
                    if (tasks[i].nextTimeAfter(j) <= to && tasks[i].nextTimeAfter(j) != -1) {
                        //Then we add this task to the result array
                        result.add(tasks[i]);
                        break;
                    }
                }
            }
        }
        return result;
    }

    */
}
