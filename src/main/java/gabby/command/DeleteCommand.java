package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.Task;
import gabby.task.TaskList;

public class DeleteCommand extends Command {
    private final int taskId;

    public DeleteCommand(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GabbyException {
        Task task = tasks.deleteTask(this.taskId - 1);
        ui.showMsg(String.format("Poof! I've removed this task:\n  %s\nNow you have %d task%s in the list.",
                task, tasks.size(), tasks.size() == 1 ? "" : "s"));
        storage.save(tasks);
    }
}
