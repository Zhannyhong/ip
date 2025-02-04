package gabby;

import gabby.command.Command;
import gabby.task.TaskList;

import java.util.Scanner;

public class Gabby {
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    public Gabby(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        try {
            this.tasks = new TaskList(storage.load());
        } catch (Exception e) {
            this.tasks = new TaskList();
        }
    }

    public static void main(String[] args) {
        new Gabby("data/tasks.txt").run();
    }

    public void run() {
        this.ui.showWelcome();

        try (Scanner reader = new Scanner(System.in)) {
            while (true) {
                String input = reader.nextLine().strip();

                try {
                    Command c = Parser.parse(input);
                    c.execute(this.tasks, this.ui, this.storage);

                    if (c.isExit()) {
                        break;
                    }
                } catch (GabbyException e) {
                    this.ui.showMsg(e.getMessage());
                }
            }
        }
    }
}
