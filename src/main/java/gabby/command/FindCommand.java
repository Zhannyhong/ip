package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.task.TaskList;

/**
 * Represents a command to find tasks by a keyword in the description.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a new FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for in the task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws GabbyException {
        TaskList filteredTasks = new TaskList(tasks.filterTasksByKeyword(this.keyword));

        if (filteredTasks.size() == 0) {
            this.response = "I couldn't find any tasks matching the keyword '" + this.keyword + "'! =/";
        } else {
            this.response = "Here are the matching tasks in your list:\n" + filteredTasks;
        }
    }
}
