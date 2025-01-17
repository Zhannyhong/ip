public class DeadlineTask extends Task {
    protected String by;

    public DeadlineTask(String description, String by) {
        super(description);
        this.by = by;
    }

    public static DeadlineTask parseArgs(String args) {
        String[] parsed = args.split(" /by ");
        return new DeadlineTask(parsed[0], parsed[1]);
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.by);
    }
}
