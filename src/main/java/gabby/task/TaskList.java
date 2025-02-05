package gabby.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import gabby.GabbyException;

public class TaskList {
    private final List<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public TaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public int size() {
        return taskList.size();
    }

    public boolean addTask(Task task) {
        return this.taskList.add(task);
    }

    public Task deleteTask(int taskID) throws GabbyException {
        if (!isTaskIDValid(taskID)) {
            throw new GabbyException("gabby.task.Task ID is not in your list!");
        }

        return taskList.remove(taskID);
    }

    public Task markTask(int taskID) throws GabbyException {
        if (!isTaskIDValid(taskID)) {
            throw new GabbyException("gabby.task.Task ID is not in your list!");
        }

        Task task = taskList.get(taskID);
        task.markAsDone();

        return task;
    }

    public Task unmarkTask(int taskID) throws GabbyException {
        if (!isTaskIDValid(taskID)) {
            throw new GabbyException("gabby.task.Task ID is not in your list!");
        }

        Task task = taskList.get(taskID);
        task.markNotDone();

        return task;
    }

    public List<Task> filterTasksByDate(LocalDate filterDate) {
        return taskList.stream()
                .filter(task -> filterDate.query(task::isDateInRange))
                .toList();
    }

    /**
     * Filters tasks where the task description contains the keyword.
     *
     * @param keyword Keyword to filter tasks by.
     * @return List of tasks that contain the keyword.
     */
    public List<Task> filterTasksByKeyword(String keyword) {
        return taskList.stream()
                .filter(task -> task.getDescription().contains(keyword))
                .toList();
    }

    public String serialize() {
        StringJoiner serialized = new StringJoiner("\n");

        for (Task task : taskList) {
            serialized.add(task.serialize());
        }

        return serialized.toString();
    }

    private boolean isTaskIDValid(int taskID) {
        return taskID >= 0 && taskID < taskList.size();
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
