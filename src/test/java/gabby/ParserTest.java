package gabby;

import gabby.command.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test
    public void parse_byeCommand_success() {
        assertDoesNotThrow(() -> assertInstanceOf(ByeCommand.class, Parser.parse("bye")));
    }

    @Test
    public void parse_listCommand_success() {
        assertDoesNotThrow(() -> assertInstanceOf(ListCommand.class, Parser.parse("list")));
    }

    @Test
    public void parse_listCommandWithDate_success() {
        assertDoesNotThrow(() -> assertInstanceOf(ListCommand.class, Parser.parse("list 2025-02-05")));
    }

    @Test
    public void parse_listCommandWithDate_failure() {
        assertThrows(GabbyException.class, () -> assertInstanceOf(ListCommand.class, Parser.parse("list 2025-2-5")));
    }

    @Test
    public void parse_markCommand_success() {
        assertDoesNotThrow(() -> assertInstanceOf(MarkCommand.class, Parser.parse("mark 420")));
    }

    @Test
    public void parse_unmarkCommand_success() {
        assertDoesNotThrow(() -> assertInstanceOf(UnmarkCommand.class, Parser.parse("unmark 69")));
    }

    @Test
    public void parse_deleteCommand_success() {
        assertDoesNotThrow(() -> assertInstanceOf(DeleteCommand.class, Parser.parse("delete 42")));
    }

    @Test
    public void parse_todoCommand_success() {
        assertDoesNotThrow(() -> assertInstanceOf(TodoCommand.class, Parser.parse("todo test task")));
    }

    @Test
    public void parse_deadlineCommand_success() {
        assertDoesNotThrow(() -> assertInstanceOf(DeadlineCommand.class,
                Parser.parse("deadline test task /by 2025-02-06 2359")));
    }

    @Test
    public void parse_deadlineCommandNoDesc_failure() {
        assertThrows(GabbyException.class, () -> Parser.parse("deadline /by 2025-02-06 2359"));
    }

    @Test
    public void parse_deadlineCommandNoBy_failure() {
        assertThrows(GabbyException.class, () -> Parser.parse("deadline test task 2025-02-06 2359"));
    }

    @Test
    public void parse_deadlineCommandNoTime_failure() {
        assertThrows(GabbyException.class, () -> Parser.parse("deadline test task /by 2025-02-06"));
    }

    @Test
    public void parse_deadlineCommandWrongDateFormat_failure() {
        assertThrows(GabbyException.class, () -> Parser.parse("deadline test task /by 2025-2-06 2359"));
    }

    @Test
    public void parse_eventCommand_success() {
        assertDoesNotThrow(() -> assertInstanceOf(EventCommand.class,
                Parser.parse("event test task /from 2025-02-04 2345 /to 2025-02-06 2359")));
    }

    @Test
    public void parse_eventCommandNoDesc_failure() {
        assertThrows(GabbyException.class, () -> Parser.parse("event /from 2025-02-04 2345 /to 2025-02-06 2359"));
    }

    @Test
    public void parse_eventCommandNoFrom_failure() {
        assertThrows(GabbyException.class, () -> Parser.parse("event test task 2025-02-04 2345 /to 2025-02-06 2359"));
    }

    @Test
    public void parse_eventCommandNoTo_failure() {
        assertThrows(GabbyException.class, () -> Parser.parse("event test task /from 2025-02-04 2345 2025-02-06 2359"));
    }

    @Test
    public void parse_eventCommandWrongFromDateTimeFormat_failure() {
        assertThrows(GabbyException.class, () -> Parser.parse("event test task /from 2025-02-04 /to 2025-02-06 2359"));
    }

    @Test
    public void parse_eventCommandWrongToDateTimeFormat_failure() {
        assertThrows(GabbyException.class, () -> Parser.parse("event test task /from 2025-02-04 2345 /to 2025-02-06"));
    }

    @Test
    public void parse_unknownCommand_failure() {
        assertThrows(GabbyException.class, () -> Parser.parse("test"));
    }
}
