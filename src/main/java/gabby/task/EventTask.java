package gabby.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gabby.GabbyException;

/**
 * Represents an event task with a start and end deadline.
 */
public class EventTask extends Task {
    private static final Pattern EVENT_PATTERN = Pattern.compile("(.+) /from (.+) /to (.+)");
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Creates a new event task.
     *
     * @param description The description of the event task.
     * @param from        The start time of the event.
     * @param to          The end time of the event.
     */
    public EventTask(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Parses the arguments to create a new event task.
     *
     * @param args The arguments to create the event task.
     * @return The new event task.
     * @throws GabbyException If the arguments are invalid.
     */
    public static EventTask parseArgs(String args) throws GabbyException {
        Matcher parsed = EVENT_PATTERN.matcher(args);
        if (!parsed.matches()) {
            throw new GabbyException(
                    "Events have to be in the format: event <description> /from <yyyy-mm-dd hhmm> /to <yyyy-mm-dd hhmm>"
            );
        }

        String description = parsed.group(1).strip();
        if (description.isBlank()) {
            throw new GabbyException("Oh no! The description of an event cannot be empty!");
        }

        LocalDateTime from;
        LocalDateTime to;
        try {
            from = LocalDateTime.parse(parsed.group(2).strip(), Task.DT_FORMAT);
            to = LocalDateTime.parse(parsed.group(3).strip(), Task.DT_FORMAT);
        } catch (DateTimeParseException err) {
            throw new GabbyException(
                    "Datetime has to be in the format: yyyy-mm-dd hhmm (e.g. 2001-11-23 2025)");
        }

        assert from != null : "Parsed 'from' date should not be null!";
        assert to != null : "Parsed 'to' date should not be null!";

        if (to.isBefore(from)) {
            throw new GabbyException("Event end time cannot be before start time!");
        }

        return new EventTask(description, from, to);
    }

    /**
     * Deserializes a saved event task.
     *
     * @param serialized The serialized task.
     * @return The deserialized event task.
     * @throws GabbyException If the serialized task is invalid.
     */
    public static EventTask deserialize(String[] serialized) throws GabbyException {
        assert serialized != null : "Serialized data should not be null!";

        if (serialized.length != 5) {
            throw new GabbyException("Saved task does not have the required number of arguments!");
        }

        LocalDateTime from;
        LocalDateTime to;
        try {
            from = LocalDateTime.parse(serialized[3], Task.DT_FORMAT);
            to = LocalDateTime.parse(serialized[4], Task.DT_FORMAT);
        } catch (DateTimeParseException err) {
            throw new GabbyException(
                    "Datetime has to be in the format: yyyy-mm-dd hhmm (e.g. 2001-11-23 2025)");
        }

        assert from != null : "Deserialized 'from' date should not be null!";
        assert to != null : "Deserialized 'to' date should not be null!";

        if (to.isBefore(from)) {
            throw new GabbyException("Event end time cannot be before start time!");
        }

        EventTask task = new EventTask(serialized[2], from, to);

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
                "E | %s | %s | %s | %s",
                super.isDone ? 1 : 0,
                super.description,
                Task.DT_FORMAT.format(this.from),
                Task.DT_FORMAT.format(this.to)
        );
    }

    @Override
    public boolean isDateInRange(TemporalAccessor date) {
        LocalDate queryDate = LocalDate.from(date);
        return !this.from.toLocalDate().isAfter(queryDate) && !this.to.toLocalDate().isBefore(queryDate);
    }

    @Override
    public String toString() {
        return String.format(
                "[E]%s (from: %s -- to: %s)",
                super.toString(),
                Task.DT_DISPLAY.format(this.from),
                Task.DT_DISPLAY.format(this.to)
        );
    }
}
