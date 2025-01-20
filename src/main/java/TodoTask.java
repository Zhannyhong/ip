public class TodoTask extends Task {
    public TodoTask(String description) {
        super(description);
    }

    public static TodoTask parseArgs(String args) throws GabbyException {
        if (args.isEmpty()) {
            throw new GabbyException("Oh no! The description of a todo cannot be empty!");
        }

        return new TodoTask(args);
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
