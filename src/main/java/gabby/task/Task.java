package gabby.task;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public abstract class Task {
    protected static final DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    protected static final DateTimeFormatter dtDisplay = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm");
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    /**
     * Returns the description of the task.
     *
     * @return Description of the task.
     */
    public String getDescription() {
        return this.description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    public abstract String serialize();

    public abstract boolean isDateInRange(TemporalAccessor date);

    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }
}
