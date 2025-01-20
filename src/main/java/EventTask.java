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

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.from, this.to);
    }
}
