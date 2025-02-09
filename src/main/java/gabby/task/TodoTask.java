package gabby.task;

import java.time.temporal.TemporalAccessor;

import gabby.GabbyException;

/**
 * Represents a todo task.
 */
public class TodoTask extends Task {
    public TodoTask(String description) {
        super(description);
    }

    /**
     * Parses the arguments to create a new todo task.
     *
     * @param args The arguments to create the todo task.
     * @return The new todo task.
     * @throws GabbyException If the arguments are invalid.
     */
    public static TodoTask parseArgs(String args) throws GabbyException {
        if (args.isEmpty()) {
            throw new GabbyException("Oh no! The description of a todo cannot be empty!");
        }

        return new TodoTask(args);
    }

    /**
     * Deserializes a saved todo task.
     *
     * @param serialized The serialized task.
     * @return The saved todo task.
     * @throws GabbyException If the serialized task is invalid.
     */
    public static TodoTask deserialize(String[] serialized) throws GabbyException {
        assert serialized != null : "Serialized data should not be null!";

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
