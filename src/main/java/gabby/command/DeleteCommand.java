package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.task.Task;
import gabby.task.TaskList;

/**
 * Represents a command to delete a task.
 */
public class DeleteCommand extends Command {
    private final int taskId;

    /**
     * Creates a new delete command.
     *
     * @param taskId The ID of the task to delete.
     */
    public DeleteCommand(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws GabbyException {
        Task task = tasks.deleteTask(this.taskId - 1);
        this.response = String.format("Poof! I've removed this task:\n  %s\nNow you have %d task%s in the list.",
                task, tasks.size(), tasks.size() == 1 ? "" : "s");
        storage.save(tasks);
    }
}
