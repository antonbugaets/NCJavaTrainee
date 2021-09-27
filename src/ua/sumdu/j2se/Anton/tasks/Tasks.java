package ua.sumdu.j2se.Anton.tasks;

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


        return StreamSupport.stream(tasks.spliterator(), true)
                .filter(task -> isNextTimeAfter(task, from, to))
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

        /*
        1) Пробежаться по всем таскам, которые репетируют в этом интервале старт - енд
        2) Взять каждое повторение таски как ключ, если его еще нет в мапе, добавить вместе с тасками, которые повторяются в этом ключе.
        */

        //put all keys:
        for (Task value : iterableWithIncoming) {

            if (value.isRepeated()) {
                LocalDateTime taskIterate = value.getStartTime();
                while (true) {
                    if (taskIterate.isAfter(end) || taskIterate.isAfter(value.getEndTime())) {
                        break;
                    }
                    resultSortedMap.put(taskIterate, null);

                    LocalDateTime finalTaskIterate = taskIterate;
/*
                    resultSortedMap.replace(taskIterate,null, StreamSupport.stream(iterableWithIncoming.spliterator(), false)
                    .filter(object -> finalTaskIterate.equals())
                            .collect(Collectors.toSet()));

 */
                    taskIterate = taskIterate.plus(value.getRepeatInterval());

                }
            } else {
                resultSortedMap.put(value.getTime(), null);
            }

        }
        //put all values:

/*
        for (Task task : iterable) {
            if (!sortedMap.containsKey(task.getTime())) {
                LocalDateTime date_key = task.getTime();
                sortedMap.put(date_key, StreamSupport.stream(iterable.spliterator(), false)
                        .filter(task1 -> date_key.equals(task.getTime()))
                        .collect(Collectors.toSet())
                );
            }
        }
        */
        return resultSortedMap;
    }

}
