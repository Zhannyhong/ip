package gabby.command;

import gabby.Storage;
import gabby.task.TaskList;

/**
 * Represents a command to show the help message.
 */
public class HelpCommand extends Command {
    @Override
    public void execute(TaskList tasks, Storage storage) {
        this.response = """
                Here are the commands you can use:
                - `list [date]`: Lists all tasks. If date is provided, lists tasks on that date.
                - `todo <description>`: Adds a todo task.
                - `deadline <description> /by <datetime>`: Adds a deadline task.
                - `event <description> /from <datetime> /to <datetime>`: Adds an event task.
                - `mark <task ID>`: Marks a task as done.
                - `unmark <task ID>`: Marks a task as not done.
                - `delete <task ID>`: Deletes a task.
                - `find <keyword>`: Finds tasks by a keyword in the description.
                - `help`: Shows this help message.
                - `bye`: Exits the program.""";
    }
}
