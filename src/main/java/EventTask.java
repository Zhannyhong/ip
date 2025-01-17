public class EventTask extends Task {
    protected String from;
    protected String to;

    public EventTask(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public static EventTask parseArgs(String args) {
        String[] parsed = args.split("( /from )|( /to )");
        return new EventTask(parsed[0], parsed[1], parsed[2]);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.from, this.to);
    }
}
