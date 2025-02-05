package gabby.command;

import gabby.GabbyException;
import gabby.Storage;
import gabby.Ui;
import gabby.task.TaskList;

public abstract class Command {
    protected boolean isExit = false;

    public boolean isExit() {
        return this.isExit;
    }

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws GabbyException;
}
