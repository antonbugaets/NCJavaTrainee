

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.time.*;


public class TaskIO {

    //IO for bytes streams:

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
     * writes tasks from the list to the file.
     */
    public static void writeBinary(AbstractTaskList tasks, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        write(tasks, fileOutputStream);
    }


    /**
     * reads tasks from the stream to the current task list.
     */
    private static void read(AbstractTaskList tasks, InputStream in) throws IOException, ClassNotFoundException {
        DataInputStream dataInputStream = new DataInputStream(in);
        int numOfTasks = dataInputStream.readInt();
        for (int i = 0; i < numOfTasks; i++) {
            Task value;
            //skip one value
            int titleLength = dataInputStream.readInt();
            //read title
            String title = dataInputStream.readUTF();
            //read activity
            int isActive = dataInputStream.readInt();
            //set activity in boolean
            boolean activity = false;
            if (isActive == 1) {
                activity = true;
            }

            Period interval = null;
            //read interval in seconds
            long intervalInSec = dataInputStream.readLong();
            //set interval in Period
            if (intervalInSec > 0) {
                interval = Period.ofDays((int) (intervalInSec / 86400));
            }

            //create task
            if (interval == null) {
                value = new Task(title,
                        LocalDateTime.ofInstant(Instant.ofEpochSecond(dataInputStream.readLong()), ZoneId.systemDefault()));
            } else {

                value = new Task(title,
                        LocalDateTime.ofInstant(Instant.ofEpochSecond(dataInputStream.readLong()), ZoneId.systemDefault()),
                        LocalDateTime.ofInstant(Instant.ofEpochSecond(dataInputStream.readLong()), ZoneId.systemDefault()),
                        interval);
            }
            //set activity in task
            value.setActive(activity);
            //add task in list
            tasks.add(value);
        }
        dataInputStream.close();
    }

    /**
     * reads tasks from the file to the task list.
     */

    public static void readBinary(AbstractTaskList tasks, File file) throws IOException, ClassNotFoundException {
        FileInputStream in = new FileInputStream(file);
        read(tasks, in);
    }


    //IO for text streams:

    /**
     * writes tasks from the list to the stream in the JSON format.
     */
    private static void write(AbstractTaskList tasks, Writer out) throws IOException {
        Gson gson = new Gson();
        JsonWriter jsonWriter = new JsonWriter(out);
        jsonWriter.setIndent(" ");
        jsonWriter.beginArray();
        for (Task value :
                tasks) {
            gson.toJson(value, Task.class, jsonWriter);
        }
        jsonWriter.endArray();
        jsonWriter.close();
    }

    private static void read(AbstractTaskList tasks, Reader in) throws IOException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(in);
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            Task value = gson.fromJson(jsonReader, Task.class);
            tasks.add(value);
        }
        jsonReader.endArray();
        jsonReader.close();
    }

    public static void writeText(AbstractTaskList tasks, File file) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file)) {
            write(tasks, fileWriter);
        }
    }

    public static void readText(AbstractTaskList tasks, File file) throws IOException {
        try (FileReader fileReader = new FileReader(file)) {
            read(tasks, fileReader);
        }
    }
}