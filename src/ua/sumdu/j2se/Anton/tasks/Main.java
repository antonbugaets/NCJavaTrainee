package ua.sumdu.j2se.Anton.tasks;

import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        Task repetableTask1 = new Task("testtasik", 12, 3666, 2);
        Task repetableTask2 = new Task("title", 16, 238, 16);
        Task nonrepetableTask1 = new Task("titleNon", 12);

        AbstractTaskList linkedList = ListTypes.createTaskList(ListTypes.types.LINKED);
        linkedList.add(repetableTask1);
        linkedList.add(repetableTask2);
        linkedList.add(nonrepetableTask1);
        linkedList = linkedList.incoming(10, 14);

        for (Task value :
                linkedList) {
            System.out.println(value.getTitle());
        }

    }


}

