package ua.sumdu.j2se.Anton.tasks;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;

public class Task implements Cloneable, Serializable {


    private String title;
    //private int time;

    private LocalDateTime time;

    private LocalDateTime start;
    private LocalDateTime end;
    private Period interval;

    private boolean active;

    private boolean repeatable;

    /**
     * constructor constructs an inactive task to run at a specified time without repeating with a given name.
     */
    public Task(String title, LocalDateTime time) {

        //  time.format(DateTimeFormatter.ofPattern("M::dd::hh:mm"));
        //The constructor of a Task class should necessarily generate the IllegalArgumentException exception in the case when the time was set as a negative number;

        if (title == null) {
            throw new NullPointerException("Title was set as a wrong!");
        }
        repeatable = false;
        this.title = title;
        this.time = time;


    }

    /**
     * constructor constructs an inactive task to run within the specified time range
     * (including the start and the end time) with the set repetition interval and with a given name.
     */
    public Task(String title, LocalDateTime start, LocalDateTime end, Period interval) {

        if (title == null) {
            throw new NullPointerException("Title was set as a wrong!");
        }
        repeatable = true;
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
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
                title.equals(task.title);
    }


    @Override
    public int hashCode() {
        return Objects.hash(title, time, start, end, interval, active, repeatable);
    }

    @Override
    protected Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    @Override
    public String toString() {
        if (isRepeated()) {
            return new StringBuilder("This repeatable Task with following parameters: ").append("\nActivity: ").append(active).append("\nTitle: ").append(title).append("\nStart time: ").append(start.getMonth()).append(" ").append(start.getDayOfMonth()).append(", ").append(start.getHour()).append(":").append(start.getMinute()).append("\nEnd time: ").append(end.getMonth()).append(" ").append(end.getDayOfMonth()).append(", ").append(end.getHour()).append(":").append(end.getMinute()).append("\nInterval: ").append(interval).toString();
        } else {
            return new StringBuilder("This non repeatable Task with following parameters: ").append("\nActivity: ").append(active).append("\nTitle: ").append(title).append("\nTime: ").append(time.getMonth()).append(" ").append(time.getDayOfMonth()).append(", ").append(time.getHour()).append(":").append(time.getMinute()).toString();
        }

    }

    String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new NullPointerException("Title was set as a wrong!");
        }
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRepeated() {
        return repeatable;
    }

    /**Methods for reading and changing execution time for non-repetitive tasks:v*/

    /**
     * if the task is a repetitive one, the method must return the start time of the repetition;
     */
    public LocalDateTime getTime() {
        if (!isRepeated()) {
            return time;
        } else return start;

    }

    /**
     * if the task was a repetitive one, it should become non-repetitive.
     */

    public void setTime(LocalDateTime time) {

        this.time = time;
        if (isRepeated()) {
            this.start = null;
            this.end = null;
            this.interval = null;
            repeatable = false;
        }
    }

    /** Methods for reading and changing execution time for repetitive tasks:*/

    /**
     * if the task is a non-repetitive one, the method must return the time of the execution;
     */
    public LocalDateTime getStartTime() {
        if (isRepeated()) {
            return start;
        } else return time;
    }

    public LocalDateTime getEndTime() {
        if (isRepeated()) {
            return end;
        } else return time;
    }

    public Period getRepeatInterval() {
        if (isRepeated()) {
            return interval;
        } else return null;
    }

    /**
     * if the task is a non-repetitive one, it should become repetitive.
     */

    public void setTime(LocalDateTime start, LocalDateTime end, Period interval) {

        this.start = start;
        this.end = end;
        this.interval = interval;
        if (!isRepeated()) {
            this.time = null;
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

    public LocalDateTime nextTimeAfter(LocalDateTime current) {

        //for non repeatable task:
        if (!isRepeated()) {
            if (time.isBefore(current) || time.isEqual(current)) {

                return null; //
            } else {
                return time;
            }
        }
        //for  repeatable task:
        if (current.isAfter(end) || current.isEqual(end)) {
            return null;
        }
        if (current.isBefore(start)) {
            return start;
        }

        LocalDateTime count = start;

        LocalDateTime lastIteration = start;

        while (lastIteration.isBefore(end) || lastIteration.isEqual(end)) {
            lastIteration = lastIteration.plus(getRepeatInterval());
        }
        lastIteration = lastIteration.minus(interval);
        //   System.out.println("LastIretation w/o current: " + lastIteration.getDayOfMonth());
        //calculation last Iteration with current
        while (count.isBefore(current) || count.isEqual(current)) {
            count = count.plus(getRepeatInterval());
        }


        if (current.isEqual(lastIteration) || current.isAfter(lastIteration) || count.isAfter(lastIteration)) {
            return null;
        }

        return count;

    }


}

