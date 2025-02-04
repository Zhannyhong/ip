package gabby;

import gabby.command.*;

public class Parser {

    public static Command parse(String input) throws GabbyException {
        String[] args = input.split(" ", 2);
        String command = args[0].toUpperCase();
        String arg = args.length > 1 ? args[1] : "";

        return switch (command) {
            case "BYE" -> new ByeCommand();
            case "LIST" -> new ListCommand(arg);
            case "MARK" -> new MarkCommand(parseTaskID(arg));
            case "UNMARK" -> new UnmarkCommand(parseTaskID(arg));
            case "DELETE" -> new DeleteCommand(parseTaskID(arg));
            case "TODO" -> new TodoCommand(arg);
            case "DEADLINE" -> new DeadlineCommand(arg);
            case "EVENT" -> new EventCommand(arg);
            default -> throw new GabbyException("Sorry! I don't understand what you just said =(");
        };
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
