package ua.sumdu.j2se.Anton.tasks;

import java.util.*;
import java.util.function.Consumer;

public class ArrayTaskList extends AbstractTaskList {
    private Task[] tasks = new Task[]{};

    public ArrayTaskList() {
    }

    /**
     * void add (Task task) is a method that adds the specified task to the list.
     */
    @Override
    public void add(Task task) {
        if (Objects.equals(task, null)) {
            throw new NullPointerException("The task was empty(null)!");
        }
        tasks = Arrays.copyOf(tasks, tasks.length + 1); //make tasks length + 1
        tasks[tasks.length - 1] = task; // set task to empty place in tasks
    }


    /**
     * boolean remove (Task task)is a method that removes a task from the list and returns true,
     * if such a task was in the list. If the list contains several tasks of the same type, any of them should be removed.
     */
    @Override
    public boolean remove(Task task) { //right version
        boolean isRemoved = false;
        ArrayTaskList result = new ArrayTaskList();
        for (Task value : tasks) {
            if (value.equals(task)) {
                isRemoved = true;
            } else {
                result.add(value);
            }
        }
        tasks = result.tasks;
        return isRemoved;
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
        for (int i = 0; i < this.size(); i++) {
            if (index == i) {
                return tasks[i];
            }
        }
        throw new IndexOutOfBoundsException("index exceeds the permissible limits for the list!");
        // return null;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            private int iteratorCounter = 0;

            @Override
            public boolean hasNext() {
                return iteratorCounter < size();
            }

            @Override
            public Task next() {
                return getTask(iteratorCounter++);
            }
        };
    }

}
