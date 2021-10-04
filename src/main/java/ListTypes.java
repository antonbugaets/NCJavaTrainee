
public class ListTypes {
    public enum types {
        ARRAY,
        LINKED
    }

    public static AbstractTaskList createTaskList(ListTypes.types type) {
        switch (type) {
            case LINKED:
                return new LinkedTaskList<>();
            case ARRAY:
                return new ArrayTaskList();
            default:
                return null;
        }
    }
}
