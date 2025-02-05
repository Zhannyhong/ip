package gabby.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.TaskList;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {
    private LocalDate filterDate = null;

    /**
     * Creates a new list command.
     *
     * @param args The arguments provided by the user.
     * @throws GabbyException If the date provided is in the wrong format.
     */
    public ListCommand(String args) throws GabbyException {
        if (!args.isEmpty()) {
            try {
                this.filterDate = LocalDate.parse(args, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException err) {
                throw new GabbyException(
                        "Date provided is in the wrong format. Expected: yyyy-mm-dd (e.g. 2001-11-23)");
            }
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.size() == 0) {
            ui.showMsg("You have no tasks in your list!");
            return;
        }

        if (this.filterDate == null) {
            ui.showMsg("Here are all the tasks in your list:\n" + tasks);
            return;
        }

        TaskList filteredTasks = new TaskList(tasks.filterTasksByDate(this.filterDate));
        String dateStr = filterDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        if (filteredTasks.size() == 0) {
            ui.showMsg("You have no tasks on " + dateStr + "!");
        } else {
            ui.showMsg("Here are the tasks in your list on " + dateStr + ":\n" + filteredTasks);
        }
    }
}
