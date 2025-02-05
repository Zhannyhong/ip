package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.Task;
import gabby.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.StringJoiner;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {
    LocalDate filterDate = null;

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
                throw new GabbyException("Date provided is in the wrong format. Expected: yyyy-mm-dd (e.g. 2001-11-23)");
            }
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.size() == 0) {
            ui.showMsg("You have no tasks in your list!");
            return;
        }

        if (filterDate == null) {
            ui.showMsg("Here are all the tasks in your list:\n" + tasks);
            return;
        }

        Task[] filteredTasks = tasks.filterTasksByDate(this.filterDate);

        if (filteredTasks.length == 0) {
            ui.showMsg("You have no tasks on " + filterDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + "!");
            return;
        }

        StringJoiner msg = new StringJoiner("\n");
        msg.add("Here are the tasks in your list on " + filterDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + ":");

        for (int i = 0; i < filteredTasks.length; i++) {
            msg.add(String.format("%d.%s", i + 1, filteredTasks[i]));
        }

        ui.showMsg(msg.toString());
    }
}
