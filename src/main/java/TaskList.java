import java.time.LocalDate;
import java.util.ArrayList;
import java.util.StringJoiner;

public class TaskList {
    private final ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public int size() {
        return taskList.size();
    }

    public boolean addTask(Task task) {
        return this.taskList.add(task);
    }

    public Task deleteTask(int taskID) {
        return taskList.remove(taskID);
    }

    public Task markTask(int taskID) {
        Task task = taskList.get(taskID);
        task.markAsDone();

        return task;
    }

    public Task unmarkTask(int taskID) {
        Task task = taskList.get(taskID);
        task.markNotDone();

        return task;
    }

    public Task[] filterTasksByDate(LocalDate filterDate) {
        return taskList.stream()
                .filter(task -> filterDate.query(task::isDateInRange))
                .toArray(Task[]::new);
    }

    public String serialize() {
        StringJoiner serialized = new StringJoiner("\n");

        for (Task task : taskList) {
            serialized.add(task.serialize());
        }

        return serialized.toString();
    }

    @Override
    public String toString() {
        StringJoiner msg = new StringJoiner("\n");

        for (int i = 0; i < taskList.size(); i++) {
            msg.add(String.format("%d.%s", i + 1, taskList.get(i)));
        }

        return msg.toString();
    }
}
