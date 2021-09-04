package ua.sumdu.j2se.Anton.tasks;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        Task repetableTask1 = new Task("testtasik", 12, 3666, 2);
        Task repetableTask2 = new Task("title", 16, 238, 16);
        Task nonrepetableTask1 = new Task("titleNon", 12);

        LinkedTaskList linkedTaskList = new LinkedTaskList();
        System.out.println(linkedTaskList.size());
        linkedTaskList.add(repetableTask1);
        linkedTaskList.add(repetableTask2);
        linkedTaskList.add(nonrepetableTask1);
        System.out.println(linkedTaskList.size());

        linkedTaskList = linkedTaskList.incoming(10,14);
        System.out.println(linkedTaskList.getTask(1).getTitle());

    }
}
