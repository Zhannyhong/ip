public class Ui {
    public static final String LOGO = """
              _____     __   __
             / ___/__ _/ /  / /  __ __
            / (_ / _ `/ _ \\/ _ \\/ // /
            \\___/\\_,_/_.__/_.__/\\_, /
                               /___/
            """;
    private static final String LINE = "____________________________________________________________";

    public Ui() {
    }

    public void showWelcome() {
        System.out.printf(LOGO);
        showMsg("Fancy seeing you here! What can I do for you?");
    }

    public void showGoodbye() {
        showMsg("Nuuu I hate to see you go... Hope to see you again soon!");
    }

    public void showMsg(String msg) {
        System.out.println(LINE);
        System.out.println("Gabby:\n" + msg);
        System.out.println(LINE + "\n");
    }
}
