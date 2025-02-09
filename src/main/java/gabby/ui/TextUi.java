package gabby.ui;

import gabby.Gabby;

/**
 * Represents the user interface of the program.
 */
public class TextUi {
    public static final String LOGO = """
              _____     __   __
             / ___/__ _/ /  / /  __ __
            / (_ / _ `/ _ \\/ _ \\/ // /
            \\___/\\_,_/_.__/_.__/\\_, /
                               /___/
            """;
    private static final String LINE = "____________________________________________________________";

    public TextUi() {
    }

    /**
     * Shows the welcome message.
     */
    public void showWelcome() {
        System.out.printf(LOGO);
        showMsg("Fancy seeing you here! What can I do for you?");
    }

    /**
     * Shows a message to the user.
     *
     * @param msg The message to show.
     */
    public void showMsg(String msg) {
        System.out.println(LINE);
        System.out.println(Gabby.NAME + ":\n" + msg);
        System.out.println(LINE + "\n");
    }
}
