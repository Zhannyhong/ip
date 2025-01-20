import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadlineTask extends Task {
    private static final Pattern deadlinePattern = Pattern.compile("(.+) /by (.+)");
    protected String by;

    public DeadlineTask(String description, String by) {
        super(description);
        this.by = by;
    }

    public static DeadlineTask parseArgs(String args) throws GabbyException {
        if (args.isEmpty()) {
            throw new GabbyException("Oh no! The description of a deadline cannot be empty!");
        }

        Matcher parsed = deadlinePattern.matcher(args);
        if (!parsed.matches()) {
            throw new GabbyException("Deadlines have to be in the format: deadline <description> /by <date>");
        }

        return new DeadlineTask(parsed.group(1), parsed.group(2));
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.by);
    }
}
