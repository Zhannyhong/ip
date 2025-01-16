import java.util.Scanner;

public class Gabby {
    private static final String LINE = "____________________________________________________________";

    private static void greet() {
        System.out.println(LINE);
        System.out.println("Gabby:\nHello! I'm Gabby\nWhat can I do for you?");
        System.out.println(LINE + "\n");
    }

    private static void bye() {
        System.out.println(LINE);
        System.out.println("Gabby:\nBye. Hope to see you again soon!");
        System.out.println(LINE + "\n");
    }

    public static void main(String[] args) {
        Gabby.greet();

        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();

        while (!input.equals("bye")) {
            System.out.println(LINE);
            System.out.println("Gabby:\n" + input);
            System.out.println(LINE + "\n");

            input = reader.nextLine();
        }

        Gabby.bye();
    }
}
