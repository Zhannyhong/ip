package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.task.DeadlineTask;
import gabby.task.TaskList;

/**
 * Represents a command to add a deadline task.
 */
public class DeadlineCommand extends Command {
    private final DeadlineTask task;

    /**
     * Creates a new deadline command.
     *
     * @param args The arguments to create the deadline task.
     * @throws GabbyException If the arguments are invalid.
     */
    public DeadlineCommand(String args) throws GabbyException {
        this.task = DeadlineTask.parseArgs(args);
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws GabbyException {
        tasks.addTask(this.task);
        this.response = String.format(
                "Wow what a busy man huh. I've added this task:\n  %s\nNow you have %d task%s in the list.",
                task, tasks.size(), tasks.size() == 1 ? "" : "s");
        storage.save(tasks);
    }
}
