package ua.sumdu.j2se.Anton.tasks;

public class Main {

    public static void main(String[] args) {
        Task repetableTask1 = new Task("testtasik", 12, 3666, 2);
        Task repetableTask2 = new Task("title", 16, 238, 16);
        Task nonrepetableTask1 = new Task("titleNon", 12);
        //System.out.println(repetableTask1.nextTimeAfter(4  ));
        //  System.out.println(repetableTask1.nextTimeAfter(10));
        ArrayTaskList arrayTaskList = new ArrayTaskList();

        arrayTaskList.add(repetableTask1);
        arrayTaskList.add(repetableTask2);
        arrayTaskList.add(repetableTask2);
        arrayTaskList.add(repetableTask2);
        arrayTaskList.add(repetableTask2);
        arrayTaskList.add(repetableTask2);
        arrayTaskList.add(repetableTask2);
        arrayTaskList.add(nonrepetableTask1);
        System.out.println(arrayTaskList.size());

        System.out.println((arrayTaskList.remove(repetableTask2)));

        System.out.println(arrayTaskList.size());


     /*   ArrayTaskList test = arrayTaskList.incomingTest(11, 14);
        System.out.println(test.size());
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.getTask(i).getTitle());
        }

      */


    }
}
