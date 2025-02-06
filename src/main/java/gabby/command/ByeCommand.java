package gabby.command;

import gabby.Storage;
import gabby.task.TaskList;

/**
 * Represents a command to exit the program.
 */
public class ByeCommand extends Command {
    public ByeCommand() {
        this.isExit = true;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) {
        this.response = "Nuuu I hate to see you go... Hope to see you again soon!";
    }
}
