package ua.sumdu.j2se.Anton.tasks;

public class Main {
    public static void printListWithPreviousNNext(AbstractTaskList linkedTaskList) {
        int j = 0;
        while (j < linkedTaskList.size()) {
            if (j == 0) {
                System.out.print("Previous element: " + null + "; Current element: " + linkedTaskList.getTask(j).getTitle() + "; Next element: " + linkedTaskList.getTask(j + 1).getTitle() + "\n");
            }

            if (j != 0 & j != linkedTaskList.size() - 1)
                System.out.print("Previous element: " + linkedTaskList.getTask(j - 1).getTitle() + "; Current element: " + linkedTaskList.getTask(j).getTitle() + "; Next element: " + linkedTaskList.getTask(j + 1).getTitle() + "\n");
            if (j == linkedTaskList.size() - 1) {
                System.out.print("Previous element: " + linkedTaskList.getTask(j - 1).getTitle() + "; Current element: " + linkedTaskList.getTask(j).getTitle() + "; Next element: " + null + "\n");
            }
            j++;
        }

    }

    public static void main(String[] args) throws CloneNotSupportedException {

        Task repetableTask1 = new Task("testtasik", 12, 3666, 2);
        Task repetableTask2 = new Task("title", 16, 238, 16);
        Task nonrepetableTask1 = new Task("titleNon", 12);


        //Test's for Task's methods:
        //clone & equals test
        Task cloneTask = repetableTask1.clone();

        System.out.println("Should be true: ");
        System.out.println(cloneTask.equals(repetableTask1));
        // clean equals test
        Task testTask = cloneTask;

        System.out.println("Should be true: ");
        System.out.println(cloneTask.equals(testTask));

        //ToString test:

        System.out.println(repetableTask1.toString());



        //Test's for List's methods:
        AbstractTaskList array = ListTypes.createTaskList(ListTypes.types.ARRAY);
        AbstractTaskList linked = ListTypes.createTaskList(ListTypes.types.LINKED);

        array.add(repetableTask1);
        array.add(repetableTask2);
        array.add(nonrepetableTask1);

        linked.add(repetableTask1);
        linked.add(repetableTask2);
        linked.add(nonrepetableTask1);
        //equals's test's
        System.out.println("Should be false: ");
        System.out.println(array.equals(linked));
        System.out.println("Should be true: ");
        System.out.println(array.equals(array));
        System.out.println("Should be true: ");
        System.out.println(linked.equals(linked));

        //clone's test's:
        System.out.println("Should be true: ");
        System.out.println(array.clone().equals(array));
        System.out.println("Should be true: ");
        System.out.println(linked.clone().equals(linked));


        //toString test's:

        System.out.println(array.toString());
        System.out.println(linked.toString());
        /*
        AbstractTaskList linkedList = ListTypes.createTaskList(ListTypes.types.LINKED);

        int j = 0;
        while (j < 10) {
            linkedList.add(new Task("Task №: " + j++, 10));
        }
        System.out.println("Size of List: " + linkedList.size());
        System.out.println();
        printListWithPreviousNNext(linkedList);
        System.out.println();
        System.out.println("Removed was: " + linkedList.remove(new Task("azaza", 3)));

        System.out.println();
        printListWithPreviousNNext(linkedList);
        System.out.println();
        System.out.println("Size of List: " + linkedList.size());

         */

    }


}
