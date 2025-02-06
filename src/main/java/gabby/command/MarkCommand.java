package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.task.Task;
import gabby.task.TaskList;

/**
 * Represents a command to mark a task as done.
 */
public class MarkCommand extends Command {
    private final int taskId;

    /**
     * Creates a new mark command.
     *
     * @param taskId The ID of the task to mark.
     */
    public MarkCommand(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws GabbyException {
        Task task = tasks.markTask(this.taskId - 1);
        this.response = "Great job! I've marked this task as done:\n  " + task;
        storage.save(tasks);
    }
}
