package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.TaskList;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GabbyException {
        TaskList filteredTasks = new TaskList(tasks.filterTasksByKeyword(this.keyword));

        if (filteredTasks.size() == 0) {
            ui.showMsg("I couldn't find any tasks matching the keyword '" + this.keyword + "'! =/");
        } else {
            ui.showMsg("Here are the matching tasks in your list:\n" + filteredTasks);
        }
    }
}
