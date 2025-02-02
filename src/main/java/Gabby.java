import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

public class Gabby {
    static final String LOGO = """
              _____     __   __
             / ___/__ _/ /  / /  __ __
            / (_ / _ `/ _ \\/ _ \\/ // /
            \\___/\\_,_/_.__/_.__/\\_, /
                               /___/
            """;
    private static final String LINE = "____________________________________________________________";
    private static final String TASK_STORE_PATH = "./data/tasks.txt";
    private static final ArrayList<Task> taskList = new ArrayList<>();

    private static void greet() {
        System.out.printf(LOGO);
        Gabby.displayMsg("Fancy seeing you here! What can I do for you?");
    }

    private static void bye() {
        Gabby.displayMsg("Nuuu I hate to see you go... Hope to see you again soon!");
    }

    private static void displayMsg(String msg) {
        System.out.println(LINE);
        System.out.println("Gabby:\n" + msg);
        System.out.println(LINE + "\n");
    }

    private static void addTask(Task task) {
        Gabby.taskList.add(task);
        Gabby.displayMsg(String.format("Wow what a busy man huh. I've added this task:\n  %s\nNow you have %d task%s in the list.",
                task, taskList.size(), taskList.size() == 1 ? "" : "s"));
    }

    private static void listTasks(String args) {
        if (taskList.isEmpty()) {
            Gabby.displayMsg("You have no tasks in your list!");
            return;
        }

        StringJoiner msg = new StringJoiner("\n");
        Task[] filteredList;

        if (args.isEmpty()) {
            msg.add("Here are all the tasks in your list:");
            filteredList = taskList.toArray(Task[]::new);
        } else {
            LocalDate filterDate;
            try {
                filterDate = LocalDate.parse(args, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException err) {
                msg.add("Date provided is in the wrong format. Expected: yyyy-mm-dd (e.g. 2001-11-23)");
                Gabby.displayMsg(msg.toString());
                return;
            }

            filteredList = taskList.stream()
                    .filter(task -> filterDate.query(task::isDateInRange))
                    .toArray(Task[]::new);

            if (filteredList.length == 0) {
                Gabby.displayMsg("You have no tasks on " + filterDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "!");
                return;
            }

            msg.add("Here are the tasks in your list on " + filterDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + ":");
        }

        for (int i = 0; i < filteredList.length; i++) {
            msg.add(String.format("%d.%s", i + 1, filteredList[i]));
        }

        Gabby.displayMsg(msg.toString());
    }

    private static void markTask(int taskID) {
        Task task = taskList.get(taskID - 1);
        task.markAsDone();
        Gabby.displayMsg("Great job! I've marked this task as done:\n  " + task);
    }

    private static void unmarkTask(int taskID) {
        Task task = taskList.get(taskID - 1);
        task.markNotDone();
        Gabby.displayMsg("Sure! I've marked this task as not done yet:\n  " + task);
    }

    private static void deleteTask(int taskID) {
        Task task = taskList.remove(taskID - 1);
        Gabby.displayMsg(String.format("Poof! I've removed this task:\n  %s\nNow you have %d task%s in the list.",
                task, taskList.size(), taskList.size() == 1 ? "" : "s"));
    }

    private static int extractTaskID(String args) throws GabbyException {
        if (args.isEmpty()) {
            throw new GabbyException("I need to know the ID of the task!");
        }

        int taskID;
        try {
            taskID = Integer.parseInt(args);
        } catch (NumberFormatException err) {
            throw new GabbyException("'" + args + "' is not a valid integer!");
        }

        if (taskID < 1 || taskID > taskList.size()) {
            throw new GabbyException("Task ID '" + taskID + "' is not in your list!");
        }

        return taskID;
    }

    private static void loadTaskList() {
        File file = new File(TASK_STORE_PATH);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] serialized = scanner.nextLine().split(" \\| ");

                if (serialized.length < 3) {
                    Gabby.displayMsg("Saved task is in an incorrect format!");
                    continue;
                }

                try {
                    switch (serialized[0]) {
                        case "T":
                            taskList.add(TodoTask.deserialize(serialized));
                            break;
                        case "D":
                            taskList.add(DeadlineTask.deserialize(serialized));
                            break;
                        case "E":
                            taskList.add(EventTask.deserialize(serialized));
                            break;
                        default:
                            Gabby.displayMsg("Saved task is in an incorrect format!");
                    }
                } catch (GabbyException err) {
                    Gabby.displayMsg(err.getMessage());
                }
            }
        } catch (FileNotFoundException err) {
            // Chatbot might be run for the first time and there are no saved tasks to load
        }
    }

    private static void saveTaskList() {
        File file = new File(TASK_STORE_PATH);

        try {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
        } catch (SecurityException err) {
            Gabby.displayMsg("Unable to create directory to save tasks!");
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : taskList) {
                writer.write(task.serialize() + "\n");
            }
        } catch (IOException err) {
            Gabby.displayMsg("Error writing to file while saving tasks!");
        }
    }

    public static void main(String[] args) {
        Gabby.greet();
        Gabby.loadTaskList();

        Scanner reader = new Scanner(System.in);
        String[] input = reader.nextLine().strip().split(" ", 2);
        String command = input[0];
        String arg = input.length > 1 ? input[1] : "";

        while (true) {
            try {
                switch (command.toUpperCase()) {
                    case "":
                        break;
                    case "BYE":
                        Gabby.bye();
                        return;
                    case "LIST":
                        Gabby.listTasks(arg);
                        break;
                    case "MARK":
                        Gabby.markTask(Gabby.extractTaskID(arg));
                        Gabby.saveTaskList();
                        break;
                    case "UNMARK":
                        Gabby.unmarkTask(Gabby.extractTaskID(arg));
                        Gabby.saveTaskList();
                        break;
                    case "DELETE":
                        Gabby.deleteTask(Gabby.extractTaskID(arg));
                        Gabby.saveTaskList();
                        break;
                    case "TODO":
                        Gabby.addTask(TodoTask.parseArgs(arg));
                        Gabby.saveTaskList();
                        break;
                    case "DEADLINE":
                        Gabby.addTask(DeadlineTask.parseArgs(arg));
                        Gabby.saveTaskList();
                        break;
                    case "EVENT":
                        Gabby.addTask(EventTask.parseArgs(arg));
                        Gabby.saveTaskList();
                        break;
                    default:
                        Gabby.displayMsg("Sorry! I don't understand what you just said =(");
                }
            } catch (GabbyException err) {
                Gabby.displayMsg(err.getMessage());
            }

            input = reader.nextLine().strip().split(" ", 2);
            command = input[0];
            arg = input.length > 1 ? input[1] : "";
        }
    }
}
