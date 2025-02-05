package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.TaskList;

/**
 * Represents a command that can be executed by the user.
 */
abstract public class Command {
    protected boolean isExit = false;

    /**
     * Returns true if the command is an exit command.
     *
     * @return true if the command is an exit command, false otherwise.
     */
    public boolean isExit() {
        return this.isExit;
    }

    /**
     * Executes the command.
     *
     * @param tasks   The list of tasks.
     * @param ui      The user interface.
     * @param storage The storage object.
     * @throws GabbyException If an error occurs during execution.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws GabbyException;
}
