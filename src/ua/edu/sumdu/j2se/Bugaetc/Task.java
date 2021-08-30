package ua.edu.sumdu.j2se.Bugaetc;

import java.util.Objects;

public class Task {
    private String title;
    private int time;

    private int start;
    private int end;
    private int interval;

    private boolean active;

    private boolean repeatable;

    /**
     * constructor constructs an inactive task to run at a specified time without repeating with a given name.
     */
    public Task(String title, int time) {
        this.title = title;
        this.time = time;
        repeatable = false;
    }

    /**
     * constructor constructs an inactive task to run within the specified time range
     * (including the start and the end time) with the set repetition interval and with a given name.
     */
    public Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        repeatable = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return time == task.time &&
                start == task.start &&
                end == task.end &&
                interval == task.interval &&
                active == task.active &&
                repeatable == task.repeatable &&
                Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time, start, end, interval, active, repeatable);
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    boolean isActive() {
        return active;
    }

    void setActive(boolean active) {
        this.active = active;
    }

    boolean isRepeated() {
        return repeatable;
    }

    /**Methods for reading and changing execution time for non-repetitive tasks:v*/

    /**
     * if the task is a repetitive one, the method must return the start time of the repetition;
     */
    int getTime() {
        if (!isRepeated()) {
            return time;
        } else return start;

    }

    /**
     * if the task was a repetitive one, it should become non-repetitive.
     */

    void setTime(int time) {
        this.time = time;
        if (isRepeated()) {
            this.start = 0;
            this.end = 0;
            this.interval = 0;
            repeatable = false;
        }
    }

    /** Methods for reading and changing execution time for repetitive tasks:*/

    /**
     * if the task is a non-repetitive one, the method must return the time of the execution;
     */
    int getStartTime() {
        if (isRepeated()) {
            return start;
        } else return time;
    }

    int getEndTime() {
        if (isRepeated()) {
            return end;
        } else return time;
    }

    int getRepeatInterval() {
        if (isRepeated()) {
            return interval;
        } else return 0;
    }

    /**
     * if the task is a non-repetitive one, it should become repetitive.
     */

    void setTime(int start, int end, int interval) {
        this.start = start;
        this.end = end;
        this.interval = interval;
        if (!isRepeated()) {
            this.time = 0;
            repeatable = true;
        }
    }

    /**
     You should add the int nextTimeAfter (int current)
     method to the Task class that returns the next start time of the task execution after the current time.
     If after the specified time the task is not executed anymore, the method must return -1.
     */

    /**
     * current - time at this moment
     */
    int nextTimeAfter(int current) {

        //for non repeatable task:
        if (!isRepeated()) {
            if (time < current) {
                //active =false;
                return -1; //
            } else {
                return time;
            }
        }
        //for  repeatable task:
        if (current > end) {
            return -1;
        }
        if (current < start) {
            return start;
        }
        //helpful variable's
        //last Iteration with current
        int count = start;
        // calculation last Iteration w/o current
        long lastIteration = end - start % interval == 0 ? end : end - ((end - start) % interval);
        System.out.println("LastIretation w/o current: " + lastIteration);
        //the task will not be executed anymore
        //calculation last Iteration with current
        while (count <= current) {
            count += interval;
        }
        if (current >= lastIteration | count > lastIteration) {
            return -1;
        }
        // return last Iteration with current
        return count;

    }
}

