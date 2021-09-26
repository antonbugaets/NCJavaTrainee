package ua.sumdu.j2se.Anton.tasks;


import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {
    int size = 0;


    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract Task getTask(int index);

    public int size() {
        return size;
    }


    public Stream<Task> getStream() {
        Stream<Task> resultStream = Stream.of(getTask(0));
        for (int i = 1; i < size(); i++) {
            resultStream = Stream.concat(resultStream, Stream.of(getTask(i)));
        }
        return resultStream;
    }


    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    @Override
    protected AbstractTaskList clone() throws CloneNotSupportedException {
        if (this instanceof ArrayTaskList) return (ArrayTaskList) super.clone();
        if (this instanceof LinkedTaskList) return (LinkedTaskList) super.clone();
        return null;
    }


}
