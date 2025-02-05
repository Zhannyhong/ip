package gabby.task;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * Represents a task in the task list.
 */
public abstract class Task {
    protected static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    protected static final DateTimeFormatter DT_DISPLAY = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm");
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task if it is marked done or not.
     *
     * @return The status icon of the task.
     */
    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markNotDone() {
        this.isDone = false;
    }

    /**
     * Serializes the task for saving.
     *
     * @return The serialized task.
     */
    public abstract String serialize();

    /**
     * Returns true if the date is on or within the task's deadline.
     *
     * @param date The date to filter by.
     * @return true if the date is on or within the task's deadline, false otherwise.
     */
    public abstract boolean isDateInRange(TemporalAccessor date);

    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }
}
