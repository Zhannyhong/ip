package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.TaskList;
import gabby.task.TodoTask;

/**
 * Represents a command to add a todo task.
 */
public class TodoCommand extends Command {
    private final TodoTask task;

    /**
     * Creates a new todo command.
     *
     * @param args The arguments to create the todo task.
     * @throws GabbyException If the arguments are invalid.
     */
    public TodoCommand(String args) throws GabbyException {
        this.task = TodoTask.parseArgs(args);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GabbyException {
        tasks.addTask(this.task);
        ui.showMsg(String.format("Wow what a busy man huh. I've added this task:\n  %s\nNow you have %d task%s in the list.",
                task, tasks.size(), tasks.size() == 1 ? "" : "s"));
        storage.save(tasks);
    }
}
