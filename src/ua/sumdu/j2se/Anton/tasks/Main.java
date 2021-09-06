package ua.sumdu.j2se.Anton.tasks;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        Task repetableTask1 = new Task("testtasik", 12, 3666, 2);
        Task repetableTask2 = new Task("title", 16, 238, 16);
        Task nonrepetableTask1 = new Task("titleNon", 12);


      LinkedTaskList linkedList = new LinkedTaskList<>();
        System.out.println(linkedList.size());
        linkedList.add(repetableTask1);
        linkedList.add(repetableTask1);
        linkedList.add(repetableTask1);
        linkedList.add(repetableTask1);
        linkedList.add(repetableTask1);
        linkedList.add(repetableTask1);
       // linkedList.add(repetableTask1);
        linkedList.add(repetableTask2);
        linkedList.add(nonrepetableTask1);
        System.out.println(linkedList.size());

        linkedList.removeTest(repetableTask1);

        System.out.println(linkedList.size());
        for (int i = 0; i < 10; i++) {
            System.out.println(i + " " +linkedList.getTask(i).getTitle());
        }


        LinkedList linkedList1 = new LinkedList();
       // System.out.println(linkedList.incoming(10,14).getTask(1).getTitle());

    }
}
