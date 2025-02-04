public class UnmarkCommand extends Command {
    private final int taskID;

    public UnmarkCommand(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GabbyException {
        if (this.taskID < 1 || this.taskID > tasks.size()) {
            throw new GabbyException("Task ID '" + this.taskID + "' is not in your list!");
        }

        Task task = tasks.unmarkTask(this.taskID - 1);
        ui.showMsg("Sure! I've marked this task as not done yet:\n  " + task);
        storage.save(tasks);
    }
}
