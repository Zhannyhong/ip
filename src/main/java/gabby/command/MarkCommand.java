package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.Task;
import gabby.task.TaskList;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final int taskID;

    /**
     * Creates a new mark command.
     *
     * @param taskID The ID of the task to mark.
     */
    public MarkCommand(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GabbyException {
        Task task = tasks.markTask(this.taskID - 1);
        ui.showMsg("Great job! I've marked this task as done:\n  " + task);
        storage.save(tasks);
    }
}
