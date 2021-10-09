import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

public class Emulator {
    AbstractTaskList taskList = ListTypes.createTaskList(ListTypes.types.LINKED);
    Scanner scanner = new Scanner(System.in);

    Task value = new Task("null", LocalDateTime.MIN);

    final File file = new File("SavedData.txt");

    public void startApplication() throws IOException, ClassNotFoundException {
        System.out.print("\n0 - Start application\n");
        if (scanner.nextLine().equals("0")) {
            TaskIO.readText(taskList, file);
            if (taskList.size() > 0) {
                viewNotifications();
            }
            todoMenu();
        } else {
            System.out.println("Enter the correct data according to the instructions");
            startApplication();
        }
    }

    private void todoMenu() {
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
                // create new Task and add in taskList
                addNewTask();

                break;
            case ("2"):
                //change param of Task(i) in taskList
                changeParamInTasK();

                break;
            case ("3"):
                //delete Task in taskList or clear TaskList
                deleteTaskOrClearList();

                break;
            case ("4"):
                //view calendar
                viewCalendar(taskList);

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

    private void viewNotifications() {
        TreeMap<LocalDateTime, String> localDateTimeTaskTreeMap = new TreeMap<>();
        for (Task value : taskList) {
            LocalDateTime localDateTimeKey = value.nextTimeAfter(LocalDateTime.now());
            if (localDateTimeKey != null) {
                localDateTimeTaskTreeMap.put(localDateTimeKey, value.getTitle());
            }
        }
        for (Map.Entry<LocalDateTime, String> entry : localDateTimeTaskTreeMap.entrySet()) {
            System.out.println("Next " + entry.getValue() + " Execution " + entry.getKey());
        }

    }

    private void viewCalendar(AbstractTaskList taskList) {
        LocalDateTime start = LocalDateTime.MIN;
        LocalDateTime end = LocalDateTime.MIN;
        System.out.println("Enter a Start time in seconds:\n");
        try {
            start = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(scanner.nextLine())), ZoneId.systemDefault());
        } catch (Exception e) {
            System.out.println("Enter the correct data of Start Time according to the instructions");
            viewCalendar(taskList);
        }
        System.out.println("Enter the End time in seconds:\n");
        try {
            end = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(scanner.nextLine())), ZoneId.systemDefault());
        } catch (Exception e) {
            System.out.println("Enter the correct data of End Time according to the instructions");
            viewCalendar(taskList);
        }
        SortedMap<LocalDateTime, Set<Task>> sortedMap = Tasks.calendar(taskList, start, end);

        for (Map.Entry<LocalDateTime, Set<Task>> entry : sortedMap.entrySet()) {
            System.out.println("\nTIME: " + entry.getKey() + ". \nTASK: " + entry.getValue());
        }
        todoMenu();
    }

    private void changeParamInTasK() {
        System.out.println(taskList);
        if (taskList.size() == 0) {
            System.out.println("TaskList is empty, please, add Task's");
            todoMenu();
        }
        System.out.println("\nEnter index of changing Task in TaskList:\n");
        int index = -1;
        try {
            index = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Enter the correct data according to the instructions");
            changeParamInTasK();
        }
        if (index < 0 || index > taskList.size() - 1) {
            System.out.println("There no Task in TaskList with this index \nEnter the correct data according to the instructions");
            changeParamInTasK();
        }
        changeParamInTaskIndex(index);
    }

    private void changeParamInTaskIndex(int index) {
        this.value = taskList.getTask(index);
        System.out.println(value);

        System.out.println("\nSelect changing data in Task" + value.getTitle() + ":");
        System.out.println("1 - change Title");
        System.out.println("2 - change activity");
        System.out.println("3 - change Times");
        System.out.println("0 - step Back to Menu\n");
        switch (scanner.nextLine()) {
            case ("0"):
                todoMenu();
                break;
            case ("1"):
                setTitleInTask();
                changeParamInTaskIndex(index);
                break;
            case ("2"):
                setActivityInTask();
                changeParamInTaskIndex(index);
                break;
            case ("3"):
                setTimesInTask();
                changeParamInTaskIndex(index);
                break;
            default:
                System.out.println("Enter the correct data according to the instructions");
                changeParamInTaskIndex(index);
                break;
        }
    }

    private void deleteTaskOrClearList() {

        if (taskList.size() == 0) {
            System.out.println("There no Task's in Task list");
            todoMenu();
        }
        System.out.println(taskList);
        System.out.println("\n1 - Delete Task in TaskList");
        System.out.println("2 - Clear Task in TaskList");
        System.out.println("0 - Step Back\n");

        switch (scanner.nextLine()) {
            case ("0"):
                todoMenu();
                break;
            case ("1"):
                deleteTaskI();
                break;
            case ("2"):
                for (Task value : taskList) {
                    taskList.remove(value);
                    System.out.println("TaskList cleared");
                    todoMenu();
                    break;
                }
            default:
                System.out.println("Enter the correct data according to the instructions");
                deleteTaskOrClearList();
                break;
        }
    }

    private void deleteTaskI() {
        System.out.println("Insert index of Task in TaskList:\n");
        int index = 0;

        try {
            index = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Enter the correct data according to the instructions");
            deleteTaskI();
        }
        if (index < 0 || index > taskList.size() - 1) {
            System.out.println("There no Task in TaskList with this index \nEnter the correct data according to the instructions");
            deleteTaskI();
        }
        for (int i = 0; i < taskList.size(); i++) {
            if (i == index) {

                if (taskList.remove(taskList.getTask(i))) {
                    System.out.println("Task removed");
                    todoMenu();
                } else {
                    System.out.println("Task isn't removed");
                    deleteTaskI();
                }
            }
        }

    }

    private void saveAndExit() {
        try {
            TaskIO.writeText(taskList, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    private void addNewTask() {
        value = new Task("null", LocalDateTime.MIN);
        System.out.println("It's Task-create menu:");
        System.out.println("1 - Start Task adding");
        System.out.println("0 - Step Back\n");
        switch (scanner.nextLine()) {
            case ("1"):
                setTitleInTask();
                System.out.println("Tittle was ser correctly");
                setTimesInTask();
                System.out.println("Times was set correctly");
                setActivityInTask();
                System.out.println("Activity was set correctly");
                taskList.add(value);
                System.out.println("Task '" + value.getTitle() + "' was added correctly ");
                todoMenu();
                break;
            case ("0"):
                todoMenu();
                break;
            default:
                System.out.println("Enter the correct data according to the instructions");
                addNewTask();
                break;
        }
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
                break;
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
                break;
        }
    }
}
