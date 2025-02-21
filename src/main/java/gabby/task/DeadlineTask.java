package gabby.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gabby.GabbyException;

/**
 * Represents a task with a deadline.
 */
public class DeadlineTask extends Task {
    private static final Pattern DEADLINE_PATTERN = Pattern.compile("(.+) /by (.+)");
    protected LocalDateTime by;

    /**
     * Creates a new deadline task.
     *
     * @param description The description of the deadline task.
     * @param by          The deadline of the task.
     */
    public DeadlineTask(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Parses the arguments to create a new deadline task.
     *
     * @param args The arguments to create the deadline task.
     * @return The new deadline task.
     * @throws GabbyException If the arguments are invalid.
     */
    public static DeadlineTask parseArgs(String args) throws GabbyException {
        Matcher parsed = DEADLINE_PATTERN.matcher(args);
        if (!parsed.matches()) {
            throw new GabbyException(
                    "Deadlines have to be in the format: deadline <description> /by <yyyy-mm-dd hhmm>");
        }

        String description = parsed.group(1).strip();
        if (description.isBlank()) {
            throw new GabbyException("Oh no! The description of a deadline cannot be empty!");
        }

        LocalDateTime by;
        try {
            by = LocalDateTime.parse(parsed.group(2).strip(), Task.DT_FORMAT);
        } catch (DateTimeParseException err) {
            throw new GabbyException(
                    "Datetime has to be in the format: yyyy-mm-dd hhmm (e.g. 2001-11-23 2025)");
        }

        assert by != null : "Parsed 'by' date should not be null!";

        return new DeadlineTask(description, by);
    }

    /**
     * Deserializes a saved deadline task.
     *
     * @param serialized The serialized task.
     * @return The deserialized deadline task.
     * @throws GabbyException If the serialized task is invalid.
     */
    public static DeadlineTask deserialize(String[] serialized) throws GabbyException {
        assert serialized != null : "Serialized data should not be null!";

        if (serialized.length != 4) {
            throw new GabbyException("Saved task does not have the required number of arguments!");
        }

        LocalDateTime by;
        try {
            by = LocalDateTime.parse(serialized[3], Task.DT_FORMAT);
        } catch (DateTimeParseException err) {
            throw new GabbyException(
                    "Datetime has to be in the format: yyyy-mm-dd hhmm (e.g. 2001-11-23 2025)");
        }

        assert by != null : "Deserialized 'by' date should not be null!";

        DeadlineTask task = new DeadlineTask(serialized[2], by);

        if (serialized[1].equals("1")) {
            task.markAsDone();
        } else if (!serialized[1].equals("0")) {
            throw new GabbyException("Saved task contains invalid symbol for isDone!");
        }

        return task;
    }

    @Override
    public String serialize() {
        return String.format(
                "D | %s | %s | %s",
                super.isDone ? 1 : 0,
                super.description,
                Task.DT_FORMAT.format(this.by)
        );
    }

    @Override
    public boolean isDateInRange(TemporalAccessor date) {
        return this.by.toLocalDate().isEqual(LocalDate.from(date));
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), Task.DT_DISPLAY.format(this.by));
    }
}
