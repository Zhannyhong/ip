import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadlineTask extends Task {
    private static final Pattern deadlinePattern = Pattern.compile("(.+) /by (.+)");
    protected LocalDateTime by;

    public DeadlineTask(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public static DeadlineTask parseArgs(String args) throws GabbyException {
        if (args.isEmpty()) {
            throw new GabbyException("Oh no! The description of a deadline cannot be empty!");
        }

        Matcher parsed = deadlinePattern.matcher(args);
        if (!parsed.matches()) {
            throw new GabbyException("Deadlines have to be in the format: deadline <description> /by <yyyy-mm-dd hhmm>");
        }

        LocalDateTime by;
        try {
            by = LocalDateTime.parse(parsed.group(2), Task.dtFormat);
        } catch (DateTimeParseException err) {
            throw new GabbyException("Datetime provided is in the wrong format. Expected: yyyy-mm-dd hhmm (e.g. 2001-11-23 2025)");
        }

        return new DeadlineTask(parsed.group(1), by);
    }

    public static DeadlineTask deserialize(String[] serialized) throws GabbyException {
        if (serialized.length != 4) {
            throw new GabbyException("Saved task does not have the required number of arguments!");
        }

        LocalDateTime by;
        try {
            by = LocalDateTime.parse(serialized[3], Task.dtFormat);
        } catch (DateTimeParseException err) {
            throw new GabbyException("Datetime parsed is in the wrong format. Expected: yyyy-mm-dd hhmm (e.g. 2001-11-23 2025)");
        }

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
        return String.format("D | %s | %s | %s", super.isDone ? 1 : 0, super.description, Task.dtFormat.format(this.by));
    }

    @Override
    public boolean isDateInRange(TemporalAccessor date) {
        return this.by.toLocalDate().isEqual(LocalDate.from(date));
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), Task.dtDisplay.format(this.by));
    }
}
