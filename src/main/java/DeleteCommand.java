public class DeleteCommand extends Command {
    private final int taskID;

    public DeleteCommand(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GabbyException {
        if (this.taskID < 1 || this.taskID > tasks.size()) {
            throw new GabbyException("Task ID '" + this.taskID + "' is not in your list!");
        }

        Task task = tasks.deleteTask(this.taskID - 1);
        ui.showMsg(String.format("Poof! I've removed this task:\n  %s\nNow you have %d task%s in the list.",
                task, tasks.size(), tasks.size() == 1 ? "" : "s"));
        storage.save(tasks);
    }
}
