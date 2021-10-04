package ua.sumdu.j2se.Anton.tasks;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.Arrays;


public class TaskIO {
    /**
     * writes the tasks from the list to the stream in a binary format
     */

    private static void write(AbstractTaskList tasks, OutputStream out) throws IOException {

        DataOutputStream dataOutputStream = new DataOutputStream(out);

        dataOutputStream.writeInt(tasks.size());
        for (Task value : tasks) {
            //The length of the name
            dataOutputStream.writeInt((value.getTitle().length()));
            //The name of Task
            dataOutputStream.writeUTF(value.getTitle());
            //Activity: 0/1
            if (value.isActive()) {
                dataOutputStream.writeInt(1);
            } else {
                dataOutputStream.writeInt(0);
            }
            //Repetition interval]
            if (value.getRepeatInterval() == null) {
                dataOutputStream.writeLong(0);
            } else {
                dataOutputStream.writeLong(value.getRepeatInterval().getDays() * 86400);
            }
            //Start & end time
            if (value.isRepeated()) {
                dataOutputStream.writeLong(ZonedDateTime.of(value.getStartTime(), ZoneId.systemDefault()).toEpochSecond());
                dataOutputStream.writeLong(ZonedDateTime.of(value.getEndTime(), ZoneId.systemDefault()).toEpochSecond());
            }
            //Execution time
            else {
                dataOutputStream.writeLong(ZonedDateTime.of(value.getTime(), ZoneId.systemDefault()).toEpochSecond());
            }
        }
        dataOutputStream.close();
    }

    /**
     * reads tasks from the stream to the current task list.
     */

    private static void read(AbstractTaskList tasks, InputStream in) throws IOException, ClassNotFoundException {
        DataInputStream dataInputStream = new DataInputStream(in);
        int numOfTasks = dataInputStream.readInt();
        for (int i = 0; i < numOfTasks; i++) {
            Task value;

            int titleLength = dataInputStream.readInt();
            String title = dataInputStream.readUTF();
            int isActive = dataInputStream.readInt();
            boolean activity = false;
            if (isActive == 1) {
                activity = true;
            }

            Period interval = null;
            long intervalInSec = dataInputStream.readLong();
            if (intervalInSec > 0) {
                interval = Period.ofDays((int) (intervalInSec / 86400));
            }
            LocalDateTime startTime = null;
            LocalDateTime endTime = null;
            LocalDateTime time = null;
            if (interval == null) {
                time = LocalDateTime.ofEpochSecond(dataInputStream.readLong(), 0, (ZoneOffset) ZoneId.systemDefault());
                value = new Task(title, time);
            } else {
                startTime = LocalDateTime.ofEpochSecond(dataInputStream.readLong(), 0, (ZoneOffset) ZoneId.systemDefault());
                endTime = LocalDateTime.ofEpochSecond(dataInputStream.readLong(), 0, (ZoneOffset) ZoneId.systemDefault());
                value = new Task(title, startTime, endTime, interval);
            }
            value.setActive(activity);
            tasks.add(value);


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
        FileInputStream in = new FileInputStream(file);
        read(tasks, in);
    }
}