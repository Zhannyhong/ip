import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

public class Gabby {
    private static final String LINE = "____________________________________________________________";
    private static final ArrayList<Task> taskList = new ArrayList<>();

    private static void greet() {
        Gabby.displayMsg("Hello! I'm Gabby\nWhat can I do for you?");
    }

    private static void bye() {
        Gabby.displayMsg("Bye. Hope to see you again soon!");
    }

    private static void displayMsg(String msg) {
        System.out.println(LINE);
        System.out.println("Gabby:\n" + msg);
        System.out.println(LINE + "\n");
    }

    private static void addTask(Task task) {
        Gabby.taskList.add(task);
        Gabby.displayMsg(String.format("Got it. I've added this task:\n  %s\nNow you have %d task%s in the list.",
                task, taskList.size(), taskList.size() == 1 ? "" : "s"));
    }

    private static void listTasks() {
        if (taskList.isEmpty()) {
            Gabby.displayMsg("You have not added any tasks!");
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
        if (1 <= taskID && taskID <= taskList.size()) {
            Task task = taskList.get(taskID - 1);
            task.markAsDone();
            Gabby.displayMsg("Nice! I've marked this task as done:\n  " + task);
        } else {
            Gabby.displayMsg("Task ID '" + taskID + "' is not in your list!");
        }
    }

    public static void unmarkTask(int taskID) {
        if (1 <= taskID && taskID <= taskList.size()) {
            Task task = taskList.get(taskID - 1);
            task.markNotDone();
            Gabby.displayMsg("OK, I've marked this task as not done yet:\n  " + task);
        } else {
            Gabby.displayMsg("Task ID '" + taskID + "' is not in your list!");
        }
    }

    public static void main(String[] args) {
        Gabby.greet();

        Scanner reader = new Scanner(System.in);
        String[] input = reader.nextLine().strip().split(" ", 2);
        String command = input[0];
        String arg = input.length > 1 ? input[1] : "";

        while (true) {
            switch (command) {
                case "":
                    break;
                case "bye":
                    Gabby.bye();
                    return;
                case "list":
                    Gabby.listTasks();
                    break;
                case "mark":
                    if (arg.isEmpty()) {
                        Gabby.displayMsg("I need to know the ID of the task to mark! Format: mark <task ID>");
                    } else {
                        try {
                            Gabby.markTask(Integer.parseInt(arg));
                        } catch (NumberFormatException err) {
                            Gabby.displayMsg("'" + arg + "' is not a valid integer!");
                        }
                    }
                    break;
                case "unmark":
                    if (arg.isEmpty()) {
                        Gabby.displayMsg("I need to know the ID of the task to unmark! Format: unmark <task ID>");
                    } else {
                        try {
                            Gabby.unmarkTask(Integer.parseInt(arg));
                        } catch (NumberFormatException err) {
                            Gabby.displayMsg("'" + arg + "' is not a valid integer!");
                        }
                    }
                    break;
                case "todo":
                    try {
                        Gabby.addTask(TodoTask.parseArgs(arg));
                    } catch (GabbyException err) {
                        Gabby.displayMsg(err.getMessage());
                    }
                    break;
                case "deadline":
                    try {
                        Gabby.addTask(DeadlineTask.parseArgs(arg));
                    } catch (GabbyException err) {
                        Gabby.displayMsg(err.getMessage());
                    }
                    break;
                case "event":
                    try {
                        Gabby.addTask(EventTask.parseArgs(arg));
                    } catch (GabbyException err) {
                        Gabby.displayMsg(err.getMessage());
                    }
                    break;
                default:
                    Gabby.displayMsg("Oops! I don't understand what you just said =(");
            }

            input = reader.nextLine().strip().split(" ", 2);
            command = input[0];
            arg = input.length > 1 ? input[1] : "";
        }
    }
}
