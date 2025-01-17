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

    public static void main(String[] args) {
        Gabby.greet();

        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine().strip();

        while (true) {
            switch (input) {
                case "":
                    break;
                case "bye":
                    Gabby.bye();
                    return;
                case "list":
                    Gabby.listTasks();
                    break;
                default:
                    addTask(input);
                    Gabby.displayMsg("added: " + input);
            }

            input = reader.nextLine().strip();
        }
    }
}
