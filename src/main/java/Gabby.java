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

    private static void addTask(String taskName) {
        Gabby.taskList.add(new Task(taskName));
    }

    private static void listTasks() {
        StringJoiner msg = new StringJoiner("\n");

        for (int i = 0; i < taskList.size(); i++) {
            msg.add(String.format("%d. %s", i + 1, taskList.get(i).name));
        }

        Gabby.displayMsg(msg.toString());
    }

    public static void main(String[] args) {
        Gabby.greet();

        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();

        while (true) {
            switch (input) {
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

            input = reader.nextLine();
        }
    }
}
