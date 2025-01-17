import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

public class Gabby {
    private static final String LINE = "____________________________________________________________";
    private static final ArrayList<Task> taskList = new ArrayList<>(100);

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

    private static void addTask(String taskDesc) {
        Gabby.taskList.add(new Task(taskDesc));
        Gabby.displayMsg("added: " + taskDesc);
    }

    private static void listTasks() {
        StringJoiner msg = new StringJoiner("\n");
        msg.add("Here are the tasks in your list:");

        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            msg.add(String.format("%d.[%s] %s", i + 1, task.getStatusIcon(), task.description));
        }

        Gabby.displayMsg(msg.toString());
    }

    private static void markTask(int taskID) {
        if (1 <= taskID && taskID <= taskList.size()) {
            Task task = taskList.get(taskID - 1);
            task.markAsDone();
            Gabby.displayMsg("Nice! I've marked this task as done:\n  [X] " + task.description);
        } else {
            Gabby.displayMsg("No such task in your list!");
        }
    }

    public static void unmarkTask(int taskID) {
        if (1 <= taskID && taskID <= taskList.size()) {
            Task task = taskList.get(taskID - 1);
            task.markNotDone();
            Gabby.displayMsg("OK, I've marked this task as not done yet:\n  [ ] " + task.description);
        } else {
            Gabby.displayMsg("No such task in your list!");
        }
    }

    public static void main(String[] args) {
        Gabby.greet();

        Scanner reader = new Scanner(System.in);
        String[] input = reader.nextLine().strip().split(" ");
        String command = input[0];

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
                    int taskID = Integer.parseInt(input[1]);
                    Gabby.markTask(taskID);
                    break;
                case "unmark":
                    taskID = Integer.parseInt(input[1]);
                    Gabby.unmarkTask(taskID);
                    break;
                default:
                    String taskDesc = String.join(" ", input);
                    Gabby.addTask(taskDesc);
            }

            input = reader.nextLine().strip().split(" ");
            command = input[0];
        }
    }
}
