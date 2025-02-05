package gabby.command;

import gabby.Storage;
import gabby.Ui;
import gabby.task.TaskList;

/**
 * Represents a command to exit the program.
 */
public class ByeCommand extends Command {
    public ByeCommand() {
        super.isExit = true;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }
}
