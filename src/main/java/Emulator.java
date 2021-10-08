import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Scanner;

public class Emulator {
    AbstractTaskList taskList = ListTypes.createTaskList(ListTypes.types.LINKED);
    Scanner scanner = new Scanner(System.in);

    Task value = new Task("null", LocalDateTime.MIN);

    final File file = new File("SavedData.txt");

    public void startApplication() throws IOException, ClassNotFoundException {
        System.out.print("\n0 - Start application\n");
        if (scanner.nextLine().equals("0")) {
            TaskIO.readBinary(taskList, file);
            todoMenu();
        } else {
            System.out.println("Enter the correct data according to the instructions");
            startApplication();
        }
    }

    public void todoMenu() {
        System.out.print("\nIt's Main menu\nChoose action: ");
        System.out.print("\n1 - Create new Task");
        System.out.print("\n2 - Change parameters in existing Tasks");
        System.out.print("\n3 - Delete existing Task");
        System.out.print("\n4 - View the calendar of events scheduled for a certain period of time");
        System.out.print("\n5 - View the Task List");
        System.out.print("\n0 - Save & Exit");
        System.out.println();

        switch (scanner.nextLine()) {
            case ("1"):
                addNewTask();
                //TODO create new Task and add in taskList
                break;
            case ("2"):
                //TODO change param  of Task(i) in taskList
                break;
            case ("3"):
                //TODO delete Task in taskList
                break;
            case ("4"):
                //TODO view calendar
                break;
            case ("5"):
                System.out.println(taskList);
                todoMenu();
                break;
            case ("0"):
                saveAndExit();
                break;
            default:
                System.out.println("Enter the correct data according to the instructions");
                todoMenu();
        }
    }


    private void saveAndExit() {
        try {
            TaskIO.writeBinary(taskList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public void addNewTask() {
        System.out.println("It's Task-create menu:");
        setTitleInTask();
        System.out.println("Tittle was ser correctly");
        setTimesInTask();
        System.out.println("Times was set correctly");
        setActivityInTask();
        System.out.println("Activity was set correctly");
        taskList.add(value);
        System.out.println("Task '" + value.getTitle() + "' was added correctly ");
        todoMenu();
    }

    private void setTitleInTask() {
        System.out.println("Insert title of the Task:\n");
        try {
            value.setTitle(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Enter the correct data of Title according to the instructions");
            setTitleInTask();
        }
    }

    private void setTimesInTask() {
        System.out.println("Is this task repeatable one ?");
        System.out.println("1 - Yes, repeatable");
        System.out.println("2 - No, non repeatable\n");
        switch (scanner.nextLine()) {
            case ("2"):
                setTimesNonRepeatableTask();
                break;
            case ("1"):
                setStartAndEndAndIntervalInRepeatableTask();
                break;
            default:
                System.out.println("Please, Enter the correct data according to the instructions");
                setTimesInTask();
        }
    }

    private void setTimesNonRepeatableTask() {
        System.out.println("Enter the time of execution in Seconds:\n");
        try {
            value.setTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(scanner.nextLine())), ZoneId.systemDefault()));
        } catch (Exception e) {
            System.out.println("Please, Enter the correct data of Execution according to the instructions");
            setTimesNonRepeatableTask();
        }
    }

    private void setStartAndEndAndIntervalInRepeatableTask() {
        System.out.println("Enter the Start Time of Task in Seconds:\n");
        try {
            value.setTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(scanner.nextLine())), ZoneId.systemDefault()),
                    LocalDateTime.MIN,
                    Period.ZERO);
        } catch (Exception e) {
            System.out.println("Please, Enter the correct data of StartTime according to the instructions");
            setStartAndEndAndIntervalInRepeatableTask();
        }

        System.out.println("Enter the End Time of Task in Seconds:\n");
        try {
            value.setTime(value.getStartTime(),
                    LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(scanner.nextLine())), ZoneId.systemDefault()),
                    Period.ZERO);
        } catch (Exception e) {
            System.out.println("Please, Enter the correct data of End Time according to the instructions");
            setStartAndEndAndIntervalInRepeatableTask();
        }
        System.out.println("Enter the Interval of Task in Days:\n");
        try {
            value.setTime(value.getStartTime(),
                    value.getEndTime(),
                    Period.ofDays(Integer.parseInt(scanner.nextLine())));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please, Enter the correct data of Interval according to the instructions");
            setStartAndEndAndIntervalInRepeatableTask();
        }
    }

    private void setActivityInTask() {
        System.out.println("Is this Task Active?");
        System.out.println("1 - Yes, Active");
        System.out.println("2 - No, non Active\n");
        switch (scanner.nextLine()) {
            case ("1"):
                value.setActive(true);
                break;
            case ("2"):
                value.setActive(false);
                break;
            default:
                System.out.println("Enter the correct data of Activity according to the instructions");
                setActivityInTask();
        }
    }
}
