public class EventTask extends Task{
    protected String from;
    protected String to;

    public EventTask(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s", super.toString(), this.from, this.to);
    }
}
