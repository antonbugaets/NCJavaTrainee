

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)  {
        Emulator emulator = new Emulator();


        Task repetableTask1 = new Task("testtasik", LocalDateTime.of(2021, Month.AUGUST, 3, 14, 15), LocalDateTime.of(2022, Month.AUGUST, 29, 14, 15), Period.ofDays(5));
        Task nonrepetableTask1 = new Task("titleNon", LocalDateTime.of(2022, Month.AUGUST, 1, 14, 15));
        //      emulator.taskList.add(repetableTask1, nonrepetableTask1);

        try {
            emulator.startApplication();
        } catch (IOException e) {
            emulator.logger.error("Exception in main met", e);
        }




/*
        AbstractTaskList taskList = ListTypes.createTaskList(ListTypes.types.LINKED);
        taskList.add(repetableTask1, nonrepetableTask1);


        File fileTest = new File("testText.txt");
        TaskIO.writeText(taskList,fileTest);
        AbstractTaskList taskListRead = ListTypes.createTaskList(ListTypes.types.LINKED);

        TaskIO.readText(taskListRead, fileTest);
        System.out.println("\n*Лист из которого записываем Таски в текстовый файл: *");
        System.out.println(taskList);
        System.out.println("\n*Лист в который считываем Таски из текстового файла: *");
        System.out.println(taskListRead);

         */
    }
}



