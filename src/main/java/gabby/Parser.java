package gabby;

import gabby.command.ByeCommand;
import gabby.command.Command;
import gabby.command.DeadlineCommand;
import gabby.command.DeleteCommand;
import gabby.command.EventCommand;
import gabby.command.FindCommand;
import gabby.command.HelpCommand;
import gabby.command.ListCommand;
import gabby.command.MarkCommand;
import gabby.command.TodoCommand;
import gabby.command.UnmarkCommand;

/**
 * Represents a parser that parses user input into commands.
 */
public class Parser {

    /**
     * Parses the user input into a command.
     *
     * @param input The user input.
     * @return The command
     * @throws GabbyException If the input is invalid.
     */
    @SuppressWarnings("checkstyle:Indentation:caseIndent")
    public static Command parse(String input) throws GabbyException {
        String[] splitInput = input.split(" ", 2);
        assert splitInput.length >= 1 : "Input not split correctly";
        assert splitInput.length <= 2 : "Input not split correctly";

        String command = splitInput[0].toUpperCase();
        String args = splitInput.length > 1 ? splitInput[1] : "";

        return switch (command) {
            case "BYE" -> new ByeCommand();
            case "HELP" -> new HelpCommand();
            case "LIST" -> new ListCommand(args);
            case "MARK" -> new MarkCommand(parseTaskID(args));
            case "UNMARK" -> new UnmarkCommand(parseTaskID(args));
            case "DELETE" -> new DeleteCommand(parseTaskID(args));
            case "TODO" -> new TodoCommand(args);
            case "DEADLINE" -> new DeadlineCommand(args);
            case "EVENT" -> new EventCommand(args);
            case "FIND" -> new FindCommand(args);
            default -> throw new GabbyException("Sorry! I don't understand what you just said =(");
        };
    }

    /**
     * Parses the task ID from the user input.
     *
     * @param args The user input.
     * @return The task ID.
     * @throws GabbyException If the task ID is invalid.
     */
    private static int parseTaskID(String args) throws GabbyException {
        if (args.isEmpty()) {
            throw new GabbyException("I need to know the ID of the task!");
        }

        try {
            return Integer.parseInt(args);
        } catch (NumberFormatException err) {
            throw new GabbyException("'" + args + "' is not a valid integer!");
        }
    }
}
