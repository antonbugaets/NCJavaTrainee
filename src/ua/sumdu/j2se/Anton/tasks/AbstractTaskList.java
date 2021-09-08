package ua.sumdu.j2se.Anton.tasks;

import java.lang.reflect.Type;

public abstract class AbstractTaskList {
    public AbstractTaskList() {

    }

    int size = 0;

    abstract public void add(Task task);

    abstract public boolean remove(Task task);

    public int size() {
        return size;
    }

    abstract public Task getTask(int index);

    abstract public AbstractTaskList incoming(int from, int to);

    public AbstractTaskList incomingTest(int from, int to) {
        if (to <= from || from < 0) {
            throw new IllegalArgumentException("incoming's interval was set as a wrong number's!");
        }

        AbstractTaskList result = null;

        if (LinkedTaskList.className != null) result = new LinkedTaskList<>();
        if (ArrayTaskList.className != null) result = new ArrayTaskList();

        for (int i = 0; i < size(); i++) {
            for (int j = from; j < to; j++) {
                if (getTask(i).nextTimeAfter(j) <= to && getTask(i).nextTimeAfter(j) != -1) {
                    result.add(getTask(i));
                    break;
                }
            }
        }
        return result;
    }

}
