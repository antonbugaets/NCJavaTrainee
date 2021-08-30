package ua.sumdu.j2se.sAnton.tasks;

public interface ArrayTask {
    void add (Task task);
    boolean remove (Task task);
    int size();
    Task getTask(int index);
    ArrayTaskList incoming(int from, int to);
}
