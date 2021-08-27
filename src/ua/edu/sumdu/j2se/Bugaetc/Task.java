package ua.edu.sumdu.j2se.Bugaetc;

public class Task {
    private String title;
    private int time;

    private int start;
    private int end;
    private int interval;

    private boolean active = true;

    private boolean repeatable; //

    Task(String title, int time) { //constructor constructs an inactive task to run at a specified time without repeating with a given name.
        this.title = title;
        this.time = time;
        repeatable = false;
    }

    /*
    constructor constructs an inactive task to run within the specified time range
    (including the start and the end time) with the set repetition interval and with a given name.
     */
    Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        repeatable = true;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    Boolean isActive() {
        return active;
    }

    void setActive(boolean active) {
        this.active = active;
    }

    boolean isRepeated() {
        return repeatable;
    }

    //Methods for reading and changing execution time for non-repetitive tasks:

    int getTime() { //if the task is a repetitive one, the method must return the start time of the repetition;
        if (!isRepeated()) {
            return time;
        } else return start;

    }

    void setTime(int time) { // if the task was a repetitive one, it should become non-repetitive.
        if (isRepeated()) {
            this.time = time;
            this.start = 0;
            this.end = 0;
            this.interval = 0;
            repeatable = false;
        } else throw new RepeatableTaskEx("It's already non repeatable  task");
    }

    // Methods for reading and changing execution time for repetitive tasks:
    int getStartTime() { //if the task is a non-repetitive one, the method must return the time of the execution;
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

    void setTime(int start, int end, int interval) { //if the task is a non-repetitive one, it should become repetitive.
        if (!isRepeated()) {
            this.start = start;
            this.end = end;
            this.interval = interval;
            this.time = 0;
            repeatable = true;
        } else throw new RepeatableTaskEx("It's already repeatable  task");
    }

    /*
    You should add the int nextTimeAfter (int current)
    method to the Task class that returns the next start time of the task execution after the current time.
     If after the specified time the task is not executed anymore, the method must return -1.
     */
    int nextTimeAfter(int current) { // current - time at this moment
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
        //helpful variable's
        int count = start; //last Iteration with current
        int lastIteration = start; //Last Iteration w/o current

        while (lastIteration <= end) { ////calculation last Iteration w/o current
            lastIteration += interval;
        }
        lastIteration -= interval;

        if (current > lastIteration) { //the task will not be executed anymore
            return -1;
        }

        while (count < current) { //calculation last Iteration with current
            count += interval;
        }
        //  System.out.println("Last i: " + lastIteration);
        return count; // return last Iteration with current
    }
}

class RepeatableTaskEx extends RuntimeException {
    private String msg;

    public RepeatableTaskEx(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
