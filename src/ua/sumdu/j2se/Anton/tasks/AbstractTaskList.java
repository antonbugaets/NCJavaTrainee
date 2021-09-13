package ua.sumdu.j2se.Anton.tasks;


public abstract class AbstractTaskList implements Iterable<Task>, Cloneable {
    int size = 0;


    abstract public void add(Task task);

    abstract public boolean remove(Task task);

    abstract public Task getTask(int index);

    public int size() {
        return size;
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
