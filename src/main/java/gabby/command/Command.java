package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.task.TaskList;

/**
 * Represents a command that can be executed by the user.
 */
public abstract class Command {
    protected boolean isExit = false;
    protected String response;

    /**
     * Returns true if the command is an exit command.
     *
     * @return true if the command is an exit command, false otherwise.
     */
    public boolean isExit() {
        return this.isExit;
    }

    public String getResponse() {
        return this.response;
    }

    /**
     * Executes the command.
     *
     * @param tasks   The list of tasks.
     * @param storage The storage object.
     * @throws GabbyException If an error occurs during execution.
     */
    public abstract void execute(TaskList tasks, Storage storage) throws GabbyException;
}
