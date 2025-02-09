package gabby;

import java.util.Scanner;

import gabby.command.Command;
import gabby.task.TaskList;
import gabby.ui.TextUi;

/**
 * Represents the main class of the program.
 */
public class Gabby {
    private final Storage storage;
    private TaskList tasks;
    private boolean hasEncounteredError;
    private String commandType;

    /**
     * Creates a new Gabby instance.
     *
     * @param filePath The file path to save tasks to.
     */
    public Gabby(String filePath) {
        this.storage = new Storage(filePath);

        try {
            this.tasks = new TaskList(this.storage.load());
        } catch (Exception e) {
            this.tasks = new TaskList();
        }
    }

    public static void main(String[] args) {
        new Gabby("data/tasks.txt").run();
    }

    /**
     * Runs the program.
     */
    public void run() {
        TextUi ui = new TextUi();
        ui.showWelcome();

        try (Scanner reader = new Scanner(System.in)) {
            while (true) {
                String input = reader.nextLine().strip();

                try {
                    Command c = Parser.parse(input);
                    c.execute(this.tasks, this.storage);
                    ui.showMsg(c.getResponse());

                    if (c.isExit()) {
                        break;
                    }
                } catch (GabbyException e) {
                    ui.showMsg(e.getMessage());
                }
            }
        } catch (Exception e) {
            ui.showMsg("Ahhh! Something went wrong! Please try again later.");
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        this.hasEncounteredError = false;

        try {
            Command c = Parser.parse(input);
            c.execute(this.tasks, this.storage);
            this.commandType = c.getClass().getSimpleName();
            return c.getResponse();
        } catch (GabbyException e) {
            this.commandType = "";
            this.hasEncounteredError = true;
            return e.getMessage();
        }
    }

    /**
     * Returns true if the last response generated an error.
     *
     * @return true if the last response generated an error, false otherwise.
     */
    public boolean hasEncounteredError() {
        return this.hasEncounteredError;
    }

    public String getCommandType() {
        return this.commandType;
    }
}
