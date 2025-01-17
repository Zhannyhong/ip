public class TodoTask extends Task {
    public TodoTask(String description) {
        super(description);
    }

    public static TodoTask parseArgs(String args) {
        return new TodoTask(args);
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
