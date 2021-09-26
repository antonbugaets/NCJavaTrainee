package ua.sumdu.j2se.Anton.tasks;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;

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

    public static void main(String[] args) throws CloneNotSupportedException, InterruptedException {


        Task repetableTask1 = new Task("testtasik", LocalDateTime.of(2021, Month.AUGUST, 3, 14, 15), LocalDateTime.of(2021, Month.AUGUST, 29, 14, 15), Period.ofDays(5));
        //   Task repetableTask2 = new Task("title", null, null, null);
        Task nonrepetableTask1 = new Task("titleNon", LocalDateTime.of(2021, Month.AUGUST, 3, 14, 15));

        /*
        System.out.println("Start time: " +repetableTask1.getStartTime().getDayOfMonth());

        System.out.println("End time: " +repetableTask1.getEndTime().getDayOfMonth());

        System.out.println("Repeated interval: " +repetableTask1.getRepeatInterval().getDays());

        LocalDateTime current = LocalDateTime.of(2021, Month.AUGUST, 26, 14, 15);
        System.out.println("Current time : " + current.getDayOfMonth());
        System.out.println("Next Time After: " + repetableTask1.nextTimeAfter(current).getDayOfMonth());


         */


        AbstractTaskList taskList = ListTypes.createTaskList(ListTypes.types.LINKED);

        taskList.add(repetableTask1);
      //  taskList.add(nonrepetableTask1);

        Tasks.calendar(taskList,  LocalDateTime.of(2021, Month.AUGUST, 3, 14, 15),  LocalDateTime.of(2021, Month.SEPTEMBER, 3, 14, 15));




/*
        String input = "this\n" +
                "that\n" +
                "the_other";
        Stream<String> stream = input.lines();
        Iterable<String> iterable = stream::iterator;
        for (String value : iterable) {
            System.out.println("value = " + value);
        }


 */
    }


}
