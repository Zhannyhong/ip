package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.Task;
import gabby.task.TaskList;

/**
 * Represents a command to unmark a task to be not done.
 */
public class UnmarkCommand extends Command {
    private final int taskId;

    /**
     * Creates a new unmark command.
     *
     * @param taskId The ID of the task to unmark.
     */
    public UnmarkCommand(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GabbyException {
        Task task = tasks.unmarkTask(this.taskId - 1);
        ui.showMsg("Sure! I've marked this task as not done yet:\n  " + task);
        storage.save(tasks);
    }
}
