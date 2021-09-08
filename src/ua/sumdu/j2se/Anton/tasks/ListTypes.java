package ua.sumdu.j2se.Anton.tasks;

public class ListTypes {
    public enum types {
        ARRAY,
        LINKED
    }

    public static AbstractTaskList createTaskList(ListTypes.types type) {
        if (type.equals(types.ARRAY)) return new ArrayTaskList();
        if (type.equals(types.LINKED)) return new LinkedTaskList<>();
        return null;
    }
}
