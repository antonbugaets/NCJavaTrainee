import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.log4j.Logger;

public class Emulator {

    Logger logger = Logger.getLogger(Emulator.class);
    private AbstractTaskList taskList = ListTypes.createTaskList(ListTypes.types.ARRAY);
    private Scanner scanner = new Scanner(System.in);
    private Task value = new Task("null", LocalDateTime.MIN);

    private final File file = new File("SavedData.txt");

    public void startApplication() throws IOException {
        System.out.print("\n0 - Start application\n");
        logger.info("User: " + System.getProperty("user.name") + " logged into the application & saves data");
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
        logger.info("User: " + System.getProperty("user.name") + " opens main Menu");
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
                logger.info("User: " + System.getProperty("user.name") + " adds a new Task");
                addNewTask();
                break;
            case ("2"):
                //change param of Task(i) in taskList
                logger.info("User: " + System.getProperty("user.name") + " changes params in existing Task");
                changeParamInTasK();
                todoMenu();
                break;
            case ("3"):
                //delete Task in taskList or clear TaskList
                logger.info("User: " + System.getProperty("user.name") + " deletes Task");
                deleteTaskOrClearList();
                todoMenu();
                break;
            case ("4"):
                //view calendar
                logger.info("User: " + System.getProperty("user.name") + " views a calendar of Tasks");
                viewCalendar(taskList);
                break;
            case ("5"):
                logger.info("User: " + System.getProperty("user.name") + " views a Task List");
                System.out.println(taskList);
                todoMenu();
                break;
            case ("0"):
                logger.info("User: " + System.getProperty("user.name") + " saves Data");
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
            if (localDateTimeKey != null && value.isActive()) {
                localDateTimeTaskTreeMap.put(localDateTimeKey, value.getTitle());
            }
        }
        for (Map.Entry<LocalDateTime, String> entry : localDateTimeTaskTreeMap.entrySet()) {
            System.out.println("Next " + entry.getValue() + " Execution " + entry.getKey());
        }
        logger.info("User: " + System.getProperty("user.name") + " views notifications");
    }

    private void viewCalendar(AbstractTaskList taskList) {
        LocalDateTime start = LocalDateTime.MIN;
        LocalDateTime end = LocalDateTime.MIN;
        System.out.println("Enter a Start time in Pattern \"yyyy-MM-dd HH:mm\":\n");
        try {
            start = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            System.out.println("Enter the correct data of Start Time according to the instructions");
            viewCalendar(taskList);
        }
        System.out.println("Enter the End time in seconds:\n");
        try {
            end = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            System.out.println("Enter the correct data of End Time according to the instructions");
            viewCalendar(taskList);
        }
        if (!start.isBefore(end)) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("Start time >= End time in Calendar interval, please enter the correct data. ");
                viewCalendar(taskList);
            }
        }
        SortedMap<LocalDateTime, Set<Task>> sortedMap = Tasks.calendar(taskList, start, end);

        for (Map.Entry<LocalDateTime, Set<Task>> entry : sortedMap.entrySet()) {
            System.out.println("\nTIME: " + entry.getKey() + ". \nTASK: " + entry.getValue());
        }
        todoMenu();
    }

    private void changeParamInTasK() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "changeParamInTasK()");
        System.out.println(taskList);
        if (taskList.size() == 0) {
            System.out.println("TaskList is empty, please, add Task's");
            //   todoMenu();
            return;
        }
        System.out.println("\nEnter index of changing Task in TaskList:\n");
        int index = -1;
        try {
            index = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            logger.error("Exception in met " + "changeParamInTasK()", e);
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
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "changeParamInTasKIndex()");
        this.value = taskList.getTask(index);
        System.out.println(value);
        System.out.println("IT's Task - changing menu");
        System.out.println("Select changing data in Task" + value.getTitle() + ":");
        System.out.println("1 - change Title");
        System.out.println("2 - change activity");
        System.out.println("3 - change Times");
        System.out.println("0 - step Back to Menu\n");
        switch (scanner.nextLine()) {
            case ("0"):
                //     todoMenu();
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
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "deleteTaskOrClearList()");
        if (taskList.size() == 0) {
            System.out.println("There no Task's in Task list");
            //  todoMenu();
            return;
        }
        System.out.println(taskList);
        System.out.println("\n1 - Delete Task in TaskList");
        System.out.println("2 - Clear TaskList");
        System.out.println("0 - Step Back\n");

        switch (scanner.nextLine()) {
            case ("0"):
                //    todoMenu();
                break;
            case ("1"):
                deleteTaskI();
                break;
            case ("2"):
                for (int i = 0; i < taskList.size(); i++) {
                    taskList.remove(taskList.getTask(i));
                }
                System.out.println("TaskList cleared");
          //      todoMenu();
                break;
            default:
                System.out.println("Enter the correct data according to the instructions");
                deleteTaskOrClearList();
                break;
        }
    }

    private void deleteTaskI() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "deleteTaskI()");
        System.out.println("Insert index of Task in TaskList:\n");
        int index = 0;
        try {
            index = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            logger.error("Exception in met " + "deleteTaskI()", e);
            System.out.println("Enter the correct data according to the instructions");
            deleteTaskI();
        }
        if (index < 0 || index > taskList.size() - 1) {
            System.out.println("There no Task in TaskList with this index \nEnter the correct index");
            deleteTaskI();
        }
        for (int i = 0; i < taskList.size(); i++) {
            if (i == index) {
                if (taskList.remove(taskList.getTask(i))) {
                    System.out.println("Task removed");
                    //     todoMenu();
                    return;
                } else {
                    System.out.println("Task isn't removed");
                    deleteTaskI();
                }
            }
        }

    }

    private void saveAndExit() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "saveAndExit()");
        try {
            TaskIO.writeText(taskList, file);
            logger.info("User: " + System.getProperty("user.name") + " logged out of the application");
        } catch (IOException e) {
            logger.error("Exception in met " + "saveAndExit()", e);
            e.printStackTrace();
        }
    }

    private void addNewTask() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "addNewTask()");
        value = new Task("null", LocalDateTime.MIN);
        System.out.println("It's Task-create menu:");
        System.out.println("1 - Start Task adding");
        System.out.println("0 - Step Back\n");
        switch (scanner.nextLine()) {
            case ("1"):
                addParamsInNewTask();
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

    private void addParamsInNewTask() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "addParamsInNewTask()");
        setTitleInTask();
        if (value.getTitle().equals("null")) {
            return;
        }
        System.out.println("Title was set correctly");
        setTimesInTask();
        if (value.getStartTime().isEqual(LocalDateTime.MIN) || value.getEndTime().isEqual(LocalDateTime.MIN) || value.getRepeatInterval().isZero()) {
            return;
        }
        System.out.println("Times was set correctly");
        setActivityInTask();
        System.out.println("Activity was set correctly");
        taskList.add(value);
        System.out.println("Task '" + value.getTitle() + "' was added correctly ");

    }

    private void setTitleInTask() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "setTitleInTask()");
        System.out.println("Insert title of the Task:");
        try {
            String nextLine = scanner.nextLine();
            value.setTitle(nextLine);
        } catch (Exception e) {
            logger.error("Exception in met " + "setTitleInTask()", e);
            System.out.println("Enter the correct data of Title according to the instructions");
            setTitleInTask();
        }
    }


    private void setTimesInTask() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "setTimesInTask()");
        System.out.println("Is this task repeatable one ?");
        System.out.println("1 - Yes, repeatable");
        System.out.println("2 - No, non repeatable");
        System.out.println("0 - back to Menu\n");
        switch (scanner.nextLine()) {
            case ("2"):
                setTimesNonRepeatableTask();
                break;
            case ("1"):
                setTimesRepeatableTask();
                break;
            case ("0"):
                //       todoMenu();
                break;
            default:
                System.out.println("Please, Enter the correct data according to the instructions");
                setTimesInTask();
                break;
        }
    }

    private void setTimesNonRepeatableTask() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "setTimesNonRepeatableTask()");
        System.out.println("Enter the time of execution of Pattern: \"yyyy-MM-dd HH:mm\":");
        System.out.println("0 - Back to Menu\n");
        try {
            String dateTime = scanner.nextLine();
            if (dateTime.equals("0")) {
                return;
            }
            value.setTime(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        } catch (Exception e) {
            logger.error("Exception in met " + "setTimesNonRepeatableTask()", e);
            System.out.println("Please, Enter the correct data of Execution according to the instructions");
            setTimesNonRepeatableTask();
        }
    }

    private void setTimesRepeatableTask() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "setTimesRepeatableTask()");
        setStartRepeatable();
        if (!value.isRepeated()) {
            return;
        }
        setEndRepeatable();
        if (value.getEndTime().isEqual(LocalDateTime.MIN)) {
            return;
        }
        setIntervalRepeatable();
        //    return;
    }

    private void setStartRepeatable() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "setStartRepeatable()");
        System.out.println("Enter the  Start time of execution of Pattern: \"yyyy-MM-dd HH:mm\":\n");
        System.out.println("0 - Back to Menu");
        try {
            String startTime = scanner.nextLine();
            if (startTime.equals("0")) {
                return;
            }
            value.setTime(LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), LocalDateTime.MIN, Period.ZERO);
        } catch (Exception e) {
            logger.error("Exception in met " + "setStartRepeatable()", e);
            System.out.println("Please, Enter the correct data of of StartTime according to the instructions");
            setStartRepeatable();
        }
    }

    private void setEndRepeatable() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "setEndRepeatable()");
        System.out.println("Enter the End time of execution of Pattern: \"yyyy-MM-dd HH:mm\":");
        System.out.println("0 - Back to Menu\n");
        try {
            String endTimeString = scanner.nextLine();
            if (endTimeString.equals("0")) {
                return;
            }
            LocalDateTime endTime = LocalDateTime.parse(endTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            if (value.getStartTime().isAfter(endTime) || value.getStartTime().isEqual(endTime)) {
                throw new Exception();
            }
            value.setTime(value.getStartTime(), endTime, Period.ZERO);
        } catch (Exception e) {
            logger.error("Exception in met " + "setEndRepeatable()", e);
            System.out.println("Please, Enter the correct data of EndTime according to the instructions");
            setEndRepeatable();
        }

    }

    private void setIntervalRepeatable() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "setIntervalRepeatable()");
        System.out.println("Enter the Interval of execution in Days");
        System.out.println("0 - Back to Menu\n");
        try {
            String nextLine = scanner.nextLine();
            if (nextLine.equals("0")) {
                return;
            }
            Period period = Period.ofDays(Integer.parseInt(nextLine));
            if (!value.getStartTime().plus(period).isBefore(value.getEndTime())) {
                throw new Exception();
            }
            value.setTime(value.getStartTime(), value.getEndTime(), period);
        } catch (Exception e) {
            logger.error("Exception in met " + "setIntervalRepeatable() ", e);
            System.out.println("Please, Enter the correct data Interval according to the instructions");
            setIntervalRepeatable();
        }


    }

    private void setActivityInTask() {
        logger.info("User: " + System.getProperty("user.name") + " starts met" + "setActivityInTask()");
        System.out.println("Is this Task Active?");
        System.out.println("1 - Yes, Active");
        System.out.println("2 - No, non Active");
        System.out.println("0 - Back to Menu\n");
        switch (scanner.nextLine()) {
            case ("0"):
                break;
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
