import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventTask extends Task {
    private static final Pattern eventPattern = Pattern.compile("(.+) /from (.+) /to (.+)");
    protected String from;
    protected String to;

    public EventTask(String description, String from, String to) {
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
            throw new GabbyException("Events have to be in the format: event <description> /from <date> /to <date>");
        }

        return new EventTask(parsed.group(1), parsed.group(2), parsed.group(3));
    }

    public static EventTask deserialize(String[] serialized) throws GabbyException {
        if (serialized.length != 5) {
            throw new GabbyException("Saved task does not have the required number of arguments!");
        }

        EventTask task = new EventTask(serialized[2], serialized[3], serialized[4]);

        if (serialized[1].equals("1")) {
            task.markAsDone();
        } else if (!serialized[1].equals("0")) {
            throw new GabbyException("Saved task contains invalid symbol for isDone!");
        }

        return task;
    }

    @Override
    public String serialize() {
        return String.format("E | %s | %s | %s | %s", super.isDone ? 1 : 0, super.description, this.from, this.to);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.from, this.to);
    }
}
