package ua.edu.sumdu.j2se.Bugaetc;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Task repetableTask1 = new Task("title", 0, 10, 2);
        Task repetableTask2 = new Task("title", 12, 238, 16);
        //System.out.println(repetableTask1.nextTimeAfter(4  ));

        ArrayTaskList arrayTaskList = new ArrayTaskList();

        arrayTaskList.add(repetableTask2);
        for (int i = 0; i < 20; i++) {
            arrayTaskList.add(repetableTask1);
        }

        System.out.println(arrayTaskList.size());

        arrayTaskList.remove(repetableTask2 );
        System.out.println(arrayTaskList.size());
    }
}
