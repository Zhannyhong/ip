package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.DeadlineTask;
import gabby.task.TaskList;

public class DeadlineCommand extends Command {
    private final DeadlineTask task;

    public DeadlineCommand(String args) throws GabbyException {
        this.task = DeadlineTask.parseArgs(args);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GabbyException {
        tasks.addTask(this.task);
        ui.showMsg(String.format(
                "Wow what a busy man huh. I've added this task:\n  %s\nNow you have %d task%s in the list.",
                task, tasks.size(), tasks.size() == 1 ? "" : "s"));
        storage.save(tasks);
    }
}
