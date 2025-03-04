package gabby;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import gabby.task.DeadlineTask;
import gabby.task.EventTask;
import gabby.task.Task;
import gabby.task.TaskList;
import gabby.task.TodoTask;

/**
 * Represents a storage class that handles loading and saving of tasks.
 */
public class Storage {
    private String taskStorePath = "./data/tasks.txt";

    public Storage() {
    }

    /**
     * Creates a new storage class with the specified file path.
     *
     * @param filePath The file path to save tasks to.
     */
    public Storage(String filePath) {
        this.taskStorePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return The list of tasks loaded from the file.
     */
    public ArrayList<Task> load() {
        assert !this.taskStorePath.isEmpty() : "Filepath of storage cannot be empty!";

        File file = new File(this.taskStorePath);
        ArrayList<Task> taskList = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] serialized = scanner.nextLine().split(" \\| ");

                if (serialized.length < 3) {
                    // Silently ignore tasks that are not in the correct format
                    continue;
                }

                try {
                    switch (serialized[0]) {
                        case "T" -> taskList.add(TodoTask.deserialize(serialized));
                        case "D" -> taskList.add(DeadlineTask.deserialize(serialized));
                        case "E" -> taskList.add(EventTask.deserialize(serialized));
                        default -> { } // Silently ignore tasks that are not in the correct format
                    }
                } catch (GabbyException err) {
                    // Silently ignore tasks that are not in the correct format
                }
            }
        } catch (FileNotFoundException err) {
            // Do nothing as chatbot might be run for the first time and there are no saved tasks to load
        }

        return taskList;
    }

    /**
     * Saves tasks to the storage file.
     *
     * @param taskList The list of tasks to save.
     * @throws GabbyException If there is an error writing to the file.
     */
    public void save(TaskList taskList) throws GabbyException {
        assert !this.taskStorePath.isEmpty() : "Filepath of storage cannot be empty!";

        File file = new File(this.taskStorePath);

        try {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
        } catch (SecurityException err) {
            throw new GabbyException("Unable to create directory to save tasks!");
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(taskList.serialize());
        } catch (IOException err) {
            throw new GabbyException("Error writing to file while saving tasks!");
        }
    }
}
