import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

public class Gabby {
    static final String LOGO = """
              _____     __   __      \s
             / ___/__ _/ /  / /  __ __
            / (_ / _ `/ _ \\/ _ \\/ // /
            \\___/\\_,_/_.__/_.__/\\_, /\s
                               /___/ \s
            """;
    private static final String LINE = "____________________________________________________________";
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

    private static void listTasks() {
        if (taskList.isEmpty()) {
            Gabby.displayMsg("You have no tasks in your list!");
            return;
        }

        StringJoiner msg = new StringJoiner("\n");
        msg.add("Here are the tasks in your list:");

        for (int i = 0; i < taskList.size(); i++) {
            msg.add(String.format("%d.%s", i + 1, taskList.get(i)));
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

    public static void main(String[] args) {
        Gabby.greet();

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
                        Gabby.listTasks();
                        break;
                    case "MARK":
                        Gabby.markTask(Gabby.extractTaskID(arg));
                        break;
                    case "UNMARK":
                        Gabby.unmarkTask(Gabby.extractTaskID(arg));
                        break;
                    case "DELETE":
                        Gabby.deleteTask(Gabby.extractTaskID(arg));
                        break;
                    case "TODO":
                        Gabby.addTask(TodoTask.parseArgs(arg));
                        break;
                    case "DEADLINE":
                        Gabby.addTask(DeadlineTask.parseArgs(arg));
                        break;
                    case "EVENT":
                        Gabby.addTask(EventTask.parseArgs(arg));
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
