package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.Task;
import gabby.task.TaskList;

public class MarkCommand extends Command {
    private final int taskId;

    public MarkCommand(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GabbyException {
        Task task = tasks.markTask(this.taskId - 1);
        ui.showMsg("Great job! I've marked this task as done:\n  " + task);
        storage.save(tasks);
    }
}
