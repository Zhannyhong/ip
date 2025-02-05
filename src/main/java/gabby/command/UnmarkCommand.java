package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.Task;
import gabby.task.TaskList;

public class UnmarkCommand extends Command {
    private final int taskId;

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
