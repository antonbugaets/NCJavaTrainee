package ua.sumdu.j2se.Anton.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.SortedMap;
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

        return StreamSupport.stream(tasks.spliterator(), true)
                .filter(task -> isNextTimeAfter(task, from, to))
                ::iterator;
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
        //take subsequence
        Iterable<Task> tasksWithIncoming = incoming(tasks, start, end);


        SortedMap<LocalDateTime, Set<Task>> resultSortedMap = null;

        for (Task value : tasksWithIncoming) {
            ArrayList<LocalDateTime> eachTaskRepetition = new ArrayList<>();

            for (LocalDateTime j = start; j.isBefore(end); j = j.plus(value.getRepeatInterval())) {
                if (value.nextTimeAfter(j) != null) {
                    if (value.nextTimeAfter(j).isBefore(end) || value.nextTimeAfter(j).isEqual(end)) {
                        eachTaskRepetition.add(value.nextTimeAfter(j));
                    }
                }
            }
            for (LocalDateTime var :
                    eachTaskRepetition) {
                System.out.println(var.getDayOfMonth());
            }
            
        }
        return resultSortedMap;
    }

}
