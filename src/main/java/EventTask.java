import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventTask extends Task {
    private static final Pattern eventPattern = Pattern.compile("(.+) /from (.+) /to (.+)");
    protected LocalDateTime from;
    protected LocalDateTime to;

    public EventTask(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public static EventTask parseArgs(String args) throws GabbyException {
        if (args.isEmpty()) {
            throw new GabbyException("Oh no! The description of an event cannot be empty!");
        }

        Matcher parsed = eventPattern.matcher(args);
        if (!parsed.matches()) {
            throw new GabbyException("Events have to be in the format: event <description> /from <yyyy-mm-dd hhmm> /to <yyyy-mm-dd hhmm>");
        }

        LocalDateTime from;
        LocalDateTime to;
        try {
            from = LocalDateTime.parse(parsed.group(2), Task.dtFormat);
            to = LocalDateTime.parse(parsed.group(3), Task.dtFormat);
        } catch (DateTimeParseException err) {
            throw new GabbyException("Datetime provided is in the wrong format. Expected: yyyy-mm-dd hhmm (e.g. 2001-11-23 2025)");
        }

        if (to.isBefore(from)) {
            throw new GabbyException("Event end time cannot be before start time!");
        }

        return new EventTask(parsed.group(1), from, to);
    }

    public static EventTask deserialize(String[] serialized) throws GabbyException {
        if (serialized.length != 5) {
            throw new GabbyException("Saved task does not have the required number of arguments!");
        }

        LocalDateTime from;
        LocalDateTime to;
        try {
            from = LocalDateTime.parse(serialized[3], Task.dtFormat);
            to = LocalDateTime.parse(serialized[4], Task.dtFormat);
        } catch (DateTimeParseException err) {
            throw new GabbyException("Datetime provided is in the wrong format. Expected: yyyy-mm-dd hhmm (e.g. 2001-11-23 2025)");
        }

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
        return String.format("E | %s | %s | %s | %s", super.isDone ? 1 : 0, super.description, Task.dtFormat.format(this.from), Task.dtFormat.format(this.to));
    }

    @Override
    public boolean isDateInRange(TemporalAccessor date) {
        LocalDate queryDate = LocalDate.from(date);
        return !this.from.toLocalDate().isAfter(queryDate) && !this.to.toLocalDate().isBefore(queryDate);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s -- to: %s)", super.toString(), Task.dtDisplay.format(this.from), Task.dtDisplay.format(this.to));
    }
}
