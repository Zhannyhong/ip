package gabby.task;

import java.time.temporal.TemporalAccessor;

import gabby.GabbyException;

public class TodoTask extends Task {
    public TodoTask(String description) {
        super(description);
    }

    public static TodoTask parseArgs(String args) throws GabbyException {
        if (args.isEmpty()) {
            throw new GabbyException("Oh no! The description of a todo cannot be empty!");
        }

        return new TodoTask(args);
    }

    public static TodoTask deserialize(String[] serialized) throws GabbyException {
        if (serialized.length != 3) {
            throw new GabbyException("Saved task does not have the required number of arguments!");
        }

        TodoTask task = new TodoTask(serialized[2]);

        if (serialized[1].equals("1")) {
            task.markAsDone();
        } else if (!serialized[1].equals("0")) {
            throw new GabbyException("Saved task contains invalid symbol for isDone!");
        }

        return task;
    }

    @Override
    public String serialize() {
        return String.format("T | %s | %s", super.isDone ? 1 : 0, super.description);
    }

    @Override
    public boolean isDateInRange(TemporalAccessor date) {
        return false;
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
