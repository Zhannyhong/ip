public class Gabby {
    private static final String LINE = "____________________________________________________________";

    private static void greet() {
        System.out.println(LINE);
        System.out.println("Hello! I'm Gabby\nWhat can I do for you?");
        System.out.println(LINE);
    }

    private static void bye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public static void main(String[] args) {
        Gabby.greet();
        Gabby.bye();
    }
}
