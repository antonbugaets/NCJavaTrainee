package ua.sumdu.j2se.Anton.tasks;

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

    public static void main(String[] args) {


        Task repetableTask1 = new Task("testtasik", LocalDateTime.of(2021, Month.AUGUST, 3, 14, 15), LocalDateTime.of(2021, Month.AUGUST, 29, 14, 15), Period.ofDays(5));
        //   Task repetableTask2 = new Task("title", null, null, null);
        Task nonrepetableTask1 = new Task("titleNon", LocalDateTime.of(2021, Month.AUGUST, 1, 14, 15));

        System.out.println(repetableTask1);

        System.out.println(nonrepetableTask1);

        /**
         * nextTimeAfter tests:
         */
        System.out.println("nextTimeAfter tests:");
        //should be 2021-08-08T14:15
        System.out.println(repetableTask1.nextTimeAfter(LocalDateTime.of(2021, Month.AUGUST, 3, 14, 15)));
        //should be 2021-08-03T14:15 (start time)
        System.out.println();
        System.out.println(repetableTask1.nextTimeAfter(LocalDateTime.of(2021, Month.JULY, 3, 14, 15)));
        System.out.println();
        //should be null (cause after end time of task)
        System.out.println(repetableTask1.nextTimeAfter(LocalDateTime.of(2021, Month.SEPTEMBER, 3, 14, 15)));
        System.out.println();
        //should be 2021-08-13T14:15
        System.out.println(repetableTask1.nextTimeAfter(LocalDateTime.of(2021, Month.AUGUST, 12, 14, 15)));


        /**
         * incoming tests:
         */

        System.out.println("incoming tests:");

        AbstractTaskList taskList = ListTypes.createTaskList(ListTypes.types.LINKED);

        taskList.add(repetableTask1);
        taskList.add(nonrepetableTask1);
        //should be both Task's
        System.out.println(Tasks.incoming(taskList,LocalDateTime.of(2021, Month.JULY, 30, 14, 15), LocalDateTime.of(2021, Month.AUGUST, 10, 14, 15) ));

        System.out.println();
        //should be Only titleNon task
        System.out.println(Tasks.incoming(taskList,LocalDateTime.of(2021, Month.JULY, 30, 14, 15), LocalDateTime.of(2021, Month.AUGUST, 2, 14, 15) ));

    }


}
