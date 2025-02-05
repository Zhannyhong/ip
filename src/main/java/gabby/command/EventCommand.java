package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.EventTask;
import gabby.task.TaskList;

/**
 * Represents a command to add an event task.
 */
public class EventCommand extends Command {
    private final EventTask task;

    /**
     * Creates a new event command.
     *
     * @param args The arguments to create the event task.
     * @throws GabbyException If the arguments are invalid.
     */
    public EventCommand(String args) throws GabbyException {
        this.task = EventTask.parseArgs(args);
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
