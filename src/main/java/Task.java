import java.time.format.DateTimeFormatter;

public abstract class Task {
    protected static final DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-M-d HHmm");
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

    public void markAsDone() {
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    public abstract String serialize();

    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }
}
