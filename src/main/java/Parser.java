public class Parser {

    public static Command parse(String input) throws GabbyException {
        String[] args = input.split(" ", 2);
        String command = args[0].toUpperCase();
        String arg = args.length > 1 ? args[1] : "";

        switch (command) {
            case "BYE":
                return new ByeCommand();
            case "LIST":
                return new ListCommand(arg);
            case "MARK":
                return new MarkCommand(parseTaskID(arg));
            case "UNMARK":
                return new UnmarkCommand(parseTaskID(arg));
            case "DELETE":
                return new DeleteCommand(parseTaskID(arg));
            case "TODO":
                return new TodoCommand(arg);
            case "DEADLINE":
                return new DeadlineCommand(arg);
            case "EVENT":
                return new EventCommand(arg);
            default:
                throw new GabbyException("Sorry! I don't understand what you just said =(");
        }
    }

    private static int parseTaskID(String arg) throws GabbyException {
        if (arg.isEmpty()) {
            throw new GabbyException("I need to know the ID of the task!");
        }

        int taskID;
        try {
            taskID = Integer.parseInt(arg);
        } catch (NumberFormatException err) {
            throw new GabbyException("'" + arg + "' is not a valid integer!");
        }

        return taskID;
    }
}
