package ua.sumdu.j2se.Anton.tasks;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class TaskIO {
    /**
     writes the tasks from the list to the stream in a binary format
     */
    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        for (Task value : tasks) {
            //The number of tasks:
            int i = 0;
            objectOutputStream.writeInt(i);
            //The length of the name
            objectOutputStream.writeInt(value.getTitle().length());
            //name

            objectOutputStream.write(value.getTitle().getBytes());
            //Activity: 0/1
            if (value.isActive()) {
                objectOutputStream.writeInt(1);
            } else {
                objectOutputStream.writeInt(0);
            }
            //Repetition interval
            objectOutputStream.writeObject(value.getRepeatInterval());

            if (value.isRepeated()) {
                //Start time
                //End time
                objectOutputStream.writeObject(value.getStartTime());
                objectOutputStream.writeObject(value.getEndTime());
            } else {
                //Execution time
                objectOutputStream.writeObject(value.getTime());
            }
            i++;
        }
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    /**
      reads tasks from the stream to the current task list.
     */

    public static void read(AbstractTaskList tasks, InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(in);

        Iterable<Task> taskIterable = (Iterable<Task>) objectInputStream.readObject();
        objectInputStream.close();
        for (Task value : taskIterable) {
            tasks.add(value);
        }
    }

    /**
      writes tasks from the list to the file.
     */
    public static void writeBinary(AbstractTaskList tasks, File file) throws IOException {


        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        for (Task value : tasks) {
            //The number of tasks:
            int i = 0;
            objectOutputStream.writeInt(i);
            //The length of the name
            objectOutputStream.writeInt(value.getTitle().length());
            //name

            objectOutputStream.write(value.getTitle().getBytes());
            //Activity: 0/1
            if (value.isActive()) {
                objectOutputStream.writeInt(1);
            } else {
                objectOutputStream.writeInt(0);
            }
            //Repetition interval
            objectOutputStream.writeObject(value.getRepeatInterval());

            if (value.isRepeated()) {
                //Start time
                //End time
                objectOutputStream.writeObject(value.getStartTime());
                objectOutputStream.writeObject(value.getEndTime());
            } else {
                //Execution time
                objectOutputStream.writeObject(value.getTime());
            }
            i++;
        }
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    /**
     reads tasks from the file to the task list.
     */

    public static void readBinary(AbstractTaskList tasks, File file) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream( new FileInputStream(file));

        Iterable<Task> taskIterable = (Iterable<Task>) objectInputStream.readObject();
        objectInputStream.close();
        for (Task value : taskIterable) {
            tasks.add(value);
        }
    }
}
