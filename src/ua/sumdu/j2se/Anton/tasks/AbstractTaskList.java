package ua.sumdu.j2se.Anton.tasks;


import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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

    final public AbstractTaskList incomingTest(int from, int to) {
        if (to <= from || from < 0) {
            throw new IllegalArgumentException("incoming's interval was set as a wrong number's!");
        }
        var ref = new Object() {
            AbstractTaskList result = null;
        };
        if (this instanceof ArrayTaskList) ref.result = new ArrayTaskList();
        if (this instanceof LinkedTaskList) ref.result = new LinkedTaskList<>();


        getStream().filter(task -> isNextTimeAfter(task, from, to))
                .forEach(task -> {
                    ref.result.add(task);
                });
        return ref.result;

    }

    private boolean isNextTimeAfter(Object task, int from, int to) {
        Task value = (Task) task;
        for (int j = from; j < to; j++) {
            if (value.nextTimeAfter(j) <= to && value.nextTimeAfter(j) != -1) {
                return true;
            }
        }
        return false;
    }


    final public AbstractTaskList incoming(int from, int to) {
        if (to <= from || from < 0) {
            throw new IllegalArgumentException("incoming's interval was set as a wrong number's!");
        }

        AbstractTaskList result = null;
        if (this instanceof ArrayTaskList) result = new ArrayTaskList();
        if (this instanceof LinkedTaskList) result = new LinkedTaskList<>();
        for (Task value : this) {
            for (int j = from; j < to; j++) {
                if (value.nextTimeAfter(j) <= to && value.nextTimeAfter(j) != -1) {
                    result.add(value);
                    break;
                }
            }
        }
        return result;
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
