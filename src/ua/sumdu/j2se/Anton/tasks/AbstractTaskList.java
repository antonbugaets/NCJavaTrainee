package ua.sumdu.j2se.Anton.tasks;


public abstract class AbstractTaskList implements Iterable<Task> {
    int size = 0;


    abstract public void add(Task task);

    abstract public boolean remove(Task task);

    public int size() {
        return size;
    }

    abstract public Task getTask(int index);

    final public AbstractTaskList incoming(int from, int to) {
        if (to <= from || from < 0) {
            throw new IllegalArgumentException("incoming's interval was set as a wrong number's!");
        }

        AbstractTaskList result = null;
        if (this instanceof ArrayTaskList) result = new ArrayTaskList();
        if (this instanceof LinkedTaskList) result = new LinkedTaskList<>();
/*
        for (int i = 0; i < size(); i++) {
            for (int j = from; j < to; j++) {
                if (getTask(i).nextTimeAfter(j) <= to && getTask(i).nextTimeAfter(j) != -1) {
                    result.add(getTask(i));
                    break;
                }
            }
        }

 */
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

}
