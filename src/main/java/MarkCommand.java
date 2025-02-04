public class MarkCommand extends Command {
    private final int taskID;

    public MarkCommand(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws GabbyException {
        Task task = tasks.markTask(this.taskID - 1);
        ui.showMsg("Great job! I've marked this task as done:\n  " + task);
        storage.save(tasks);
    }
}
