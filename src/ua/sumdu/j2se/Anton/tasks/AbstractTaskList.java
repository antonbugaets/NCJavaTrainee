package ua.sumdu.j2se.Anton.tasks;

public abstract class AbstractTaskList {

    abstract public void add(Task task);

    abstract public boolean remove(Task task);

    abstract public int size();

    abstract public Task getTask(int index);

    abstract public AbstractTaskList incoming(int from, int to);
}
