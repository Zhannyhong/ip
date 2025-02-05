package gabby.task;

import gabby.GabbyException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> taskList;

    /**
     * Creates a new task list.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Creates a new task list from a list of existing tasks.
     *
     * @param taskList The existing tasks.
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int size() {
        return taskList.size();
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     * @return true if the task is added successfully, false otherwise.
     */
    public boolean addTask(Task task) {
        return this.taskList.add(task);
    }

    /**
     * Deletes a task from the list.
     *
     * @param taskID The ID of the task to delete.
     * @return The deleted task.
     * @throws GabbyException If the task ID is invalid.
     */
    public Task deleteTask(int taskID) throws GabbyException {
        if (!isTaskIDValid(taskID)) {
            throw new GabbyException("gabby.task.Task ID is not in your list!");
        }

        return taskList.remove(taskID);
    }

    /**
     * Marks a task as done.
     *
     * @param taskID The ID of the task to mark.
     * @return The marked task.
     * @throws GabbyException If the task ID is invalid.
     */
    public Task markTask(int taskID) throws GabbyException {
        if (!isTaskIDValid(taskID)) {
            throw new GabbyException("gabby.task.Task ID is not in your list!");
        }

        Task task = taskList.get(taskID);
        task.markAsDone();

        return task;
    }

    /**
     * Unmarks a task as done.
     *
     * @param taskID The ID of the task to unmark.
     * @return The unmarked task.
     * @throws GabbyException If the task ID is invalid.
     */
    public Task unmarkTask(int taskID) throws GabbyException {
        if (!isTaskIDValid(taskID)) {
            throw new GabbyException("gabby.task.Task ID is not in your list!");
        }

        Task task = taskList.get(taskID);
        task.markNotDone();

        return task;
    }

    /**
     * Filters tasks by a date.
     *
     * @param filterDate The date to filter tasks by.
     * @return The tasks that match the date.
     */
    public Task[] filterTasksByDate(LocalDate filterDate) {
        return taskList.stream()
                .filter(task -> filterDate.query(task::isDateInRange))
                .toArray(Task[]::new);
    }

    /**
     * Serializes the task list.
     *
     * @return The serialized task list.
     */
    public String serialize() {
        StringJoiner serialized = new StringJoiner("\n");

        for (Task task : taskList) {
            serialized.add(task.serialize());
        }

        return serialized.toString();
    }

    /**
     * Checks if the task ID is valid.
     *
     * @param taskID The task ID to check.
     * @return true if the task ID is valid, false otherwise.
     */
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
