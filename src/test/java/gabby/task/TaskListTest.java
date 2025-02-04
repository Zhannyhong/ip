package gabby.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    @Test
    public void addTask_todoTask_success() {
        TaskList tasks = new TaskList();
        Task task = new TodoTask("test task");
        tasks.addTask(task);

        assertEquals(1, tasks.size());
        assertDoesNotThrow(() -> assertEquals(task, tasks.deleteTask(0)));
    }

    @Test
    public void deleteTask_validTaskId_success() {
        TaskList tasks = new TaskList();
        Task task = new TodoTask("test task");
        tasks.addTask(task);

        assertDoesNotThrow(() -> assertEquals(task, tasks.deleteTask(0)));
        assertEquals(0, tasks.size());
    }

    @Test
    public void markTask_validTaskId_success() {
        TaskList tasks = new TaskList();
        Task task = new TodoTask("test task");
        tasks.addTask(task);

        assertDoesNotThrow(() -> assertEquals(task, tasks.markTask(0)));
        assertTrue(task.isDone);
    }

    @Test
    public void unmarkTask_validTaskId_success() {
        TaskList tasks = new TaskList();
        Task task = new TodoTask("test task");
        task.markAsDone();
        tasks.addTask(task);

        assertDoesNotThrow(() -> assertEquals(task, tasks.unmarkTask(0)));
        assertFalse(task.isDone);
    }
}
