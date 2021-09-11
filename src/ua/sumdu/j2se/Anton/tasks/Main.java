package ua.sumdu.j2se.Anton.tasks;

import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.LinkedList;

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

    public static void main(String[] args) {
        /*
        Task repetableTask1 = new Task("testtasik", 12, 3666, 2);
        Task repetableTask2 = new Task("title", 16, 238, 16);
        Task nonrepetableTask1 = new Task("titleNon", 12);

         */


        AbstractTaskList linkedList = ListTypes.createTaskList(ListTypes.types.LINKED);

        int j = 0;
        while (j < 10) {
            linkedList.add(new Task("Task №: " + j++, 10));
        }
        System.out.println("Size of List: " + linkedList.size());
        System.out.println();
        printListWithPreviousNNext(linkedList);
        System.out.println();
        System.out.println("Removed was: " + linkedList.remove(linkedList.getTask(4)));

        System.out.println();
        printListWithPreviousNNext(linkedList);
        System.out.println();
        System.out.println("Size of List: " + linkedList.size());

    }


}
