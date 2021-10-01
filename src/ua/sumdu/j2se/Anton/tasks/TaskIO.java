package ua.sumdu.j2se.Anton.tasks;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class TaskIO {
    /**
     * writes the tasks from the list to the stream in a binary format
     */
    private static long skipBytes;

    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException {
        //The number of tasks
        skipBytes = (tasks.size() +  "\n\n").getBytes().length;
        out.write((tasks.size() + "\n\n").getBytes(StandardCharsets.UTF_8));
        for (Task value : tasks) {
            //The length of the name
            out.write((value.getTitle().length() + "\n").getBytes(StandardCharsets.UTF_8));
            //The name of Task
            out.write((value.getTitle() + "\n").getBytes(StandardCharsets.UTF_8));
            //Activity: 0/1
            if (value.isActive()) {
                out.write((1 + "\n").getBytes(StandardCharsets.UTF_8));
            } else {
                out.write((0 + "\n").getBytes(StandardCharsets.UTF_8));
            }
            //Repetition interval
            out.write((value.getRepeatInterval() + "\n").getBytes(StandardCharsets.UTF_8));

            //Start & end time
            if (value.isRepeated()) {
                out.write((value.getStartTime() + "\n").getBytes(StandardCharsets.UTF_8));
                out.write((value.getEndTime() + "\n").getBytes(StandardCharsets.UTF_8));
            }
            //Execution time
            else {
                out.write((value.getTime() + "\n").getBytes(StandardCharsets.UTF_8));
            }
            out.write("\n".getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * reads tasks from the stream to the current task list.
     */

    public static void read(AbstractTaskList tasks, InputStream in) throws IOException, ClassNotFoundException {
        in.skip(skipBytes);
        byte[] bytes = in.readAllBytes();
        String allSymbols = new String(bytes, StandardCharsets.UTF_8);
        String[] splitedallSymbols = allSymbols.split("\n");
        for (String value: splitedallSymbols) {
            String title = "";

        }
    }

    /**
     * writes tasks from the list to the file.
     */
    public static void writeBinary(AbstractTaskList tasks, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        write(tasks, fileOutputStream);
    }

    /**
     * reads tasks from the file to the task list.
     */

    public static void readBinary(AbstractTaskList tasks, File file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        read(tasks, fileInputStream);
    }
}
