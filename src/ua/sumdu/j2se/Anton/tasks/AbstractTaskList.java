package ua.sumdu.j2se.Anton.tasks;

public abstract class AbstractTaskList {
    static int size = 0;

    abstract public void add(Task task);

    abstract public boolean remove(Task task);

    public int size() {
        return size;
    }

    abstract public Task getTask(int index);

    abstract public AbstractTaskList incoming(int from, int to);
    /*
    public AbstractTaskList incoming(int from, int to) {
        AbstractTaskList result = null;
        if (to <= from || from < 0) {
            throw new IllegalArgumentException("incoming's interval was set as a wrong number's!");
        }
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

     */
}
