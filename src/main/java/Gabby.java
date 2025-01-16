import java.util.ArrayList;
import java.util.Scanner;

public class Gabby {
    private static final String LINE = "____________________________________________________________";
    private static final ArrayList<Task> taskList = new ArrayList<Task>(100);

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

    public static void main(String[] args) {
        Gabby.greet();

        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();

        while (true) {
            switch (input) {
                case "bye":
                    Gabby.bye();
                    return;
                default:
                    addTask(input);
                    Gabby.displayMsg("added: " + input);
            }

            input = reader.nextLine();
        }
    }
}
