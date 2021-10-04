

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Tasks {
    /**
     * met returns subsequence which contains Task's which should repeat in interval from-to
     *
     * @param tasks
     * @param from
     * @param to
     * @return
     */

    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime from, LocalDateTime to) {
        if (to.isBefore(from)) {
            throw new IllegalArgumentException("incoming's interval was set as a wrong number's!");
        }
      /*  return StreamSupport.stream(tasks.spliterator(), true)
                .filter(task -> isNextTimeAfter(task, from, to))
                ::iterator;

       */

/*
        return StreamSupport.stream(tasks.spliterator(), true)
                .filter(task -> isNextTimeAfter(task, from, to))
                .collect(Collectors.toList());


 */



        return StreamSupport.stream(tasks.spliterator(), false)
                .filter(task -> task.nextTimeAfter(from) != null)
                .filter(task -> !task.nextTimeAfter(from).isAfter(to))
                .collect(Collectors.toList());



    }

    private static boolean isNextTimeAfter(Object task, LocalDateTime from, LocalDateTime to) {
        Task value = (Task) task;

        for (LocalDateTime j = from; j.isBefore(to); j = j.plusMinutes(1)) {
            if (value.nextTimeAfter(j) != null) {
                if (value.nextTimeAfter(j).isBefore(to) || value.nextTimeAfter(j).isEqual(to)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method builds the calendar of tasks for the specified period – a table,
     * where each date corresponds to a set of tasks that should be performed at this time.
     * At this, one task can refer to several dates, if it should be performed multiple times for the specified period.
     *
     * @param tasks
     * @param start
     * @param end
     * @return
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {

        Iterable<Task> iterableWithIncoming = incoming(tasks, start, end);
        TreeMap<LocalDateTime, Set<Task>> resultSortedMap = new TreeMap<>();
        for (Task value : iterableWithIncoming) {
            LocalDateTime taskIterate = value.nextTimeAfter(start);
            while (true) {
                if (taskIterate.isAfter(end) || taskIterate.isAfter(value.getEndTime())) {
                    break;
                }
                if (resultSortedMap.containsKey(taskIterate) == false) {
                    Set<Task> values = new HashSet<>();
                    values.add(value);
                    resultSortedMap.put(taskIterate, values);
                } else {
                    Set<Task> values = resultSortedMap.get(taskIterate);
                    values.add(value);
                    resultSortedMap.put(taskIterate, values);
                }
                if (value.getRepeatInterval() == null) {
                    break;
                }
                taskIterate = taskIterate.plus(value.getRepeatInterval());
            }
        }
        return resultSortedMap;
    }


   public static void main(String[] args) throws InterruptedException {

        AbstractTaskList taskList = ListTypes.createTaskList(ListTypes.types.LINKED);

        //Test's for nonRepTasks:
/*
        Task nonRep1 = new Task("nonRep1", LocalDateTime.now());
        Task nonRep2 = new Task("nonRep2", LocalDateTime.now());

        Thread.sleep(2000);
        Task nonRep3 = new Task("nonRep3", LocalDateTime.now());
        Task nonRep4 = new Task("nonRep4", LocalDateTime.now());


        taskList.add(nonRep1);
        taskList.add(nonRep2);
        taskList.add(nonRep3);
        taskList.add(nonRep4);
        Thread.sleep(2000);

        TreeMap<LocalDateTime, Set<Task>> sortedMap = (TreeMap) Tasks.calendar(taskList, LocalDateTime.now().minusDays(1), LocalDateTime.now());

 */


        //Test's for RepTasks:

        Task repetableTask1 = new Task("testtasik1!", LocalDateTime.of(2021, Month.AUGUST, 3, 14, 15), LocalDateTime.of(2021, Month.AUGUST, 29, 14, 15), Period.ofDays(5));

        Task repetableTask2 = new Task("testtasik2!", LocalDateTime.of(2021, Month.AUGUST, 3, 14, 15), LocalDateTime.of(2021, Month.AUGUST, 29, 14, 15), Period.ofDays(5));

        taskList.add(repetableTask1);
        taskList.add(repetableTask2);

        SortedMap<LocalDateTime, Set<Task>> sortedMap =  Tasks.calendar(taskList, LocalDateTime.now().minusMonths(3), LocalDateTime.now());


        for (Map.Entry<LocalDateTime, Set<Task>> entry : sortedMap.entrySet()) {
            System.out.println("\nKEY: " + entry.getKey() + ". \nVALUE: " + entry.getValue());
        }

    }


}
