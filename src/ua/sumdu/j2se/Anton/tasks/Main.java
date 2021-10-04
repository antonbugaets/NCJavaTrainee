package ua.sumdu.j2se.Anton.tasks;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.util.List;

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


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Task repetableTask1 = new Task("testtasik", LocalDateTime.of(2021, Month.AUGUST, 3, 14, 15), LocalDateTime.of(2021, Month.AUGUST, 29, 14, 15), Period.ofDays(5));
        Task nonrepetableTask1 = new Task("titleNon", LocalDateTime.of(2021, Month.AUGUST, 1, 14, 15));

        AbstractTaskList taskList = ListTypes.createTaskList(ListTypes.types.LINKED);

       // taskList.add(repetableTask1, nonrepetableTask1);

        taskList.add(nonrepetableTask1);
        taskList.add(repetableTask1);

      //  System.out.println(taskList);
        File fileTest = new File("test.txt");

        TaskIO.writeBinary(taskList,  fileTest);
        AbstractTaskList taskListRead = ListTypes.createTaskList(ListTypes.types.LINKED);

        TaskIO.readBinary(taskListRead, fileTest);

        System.out.println(taskListRead);
    }
}
