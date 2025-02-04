package gabby;

import gabby.task.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String taskStorePath = "./data/tasks.txt";

    public Storage() {
    }

    public Storage(String filePath) {
        this.taskStorePath = filePath;
    }

    public ArrayList<Task> load() {
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
                        case "T":
                            taskList.add(TodoTask.deserialize(serialized));
                            break;
                        case "D":
                            taskList.add(DeadlineTask.deserialize(serialized));
                            break;
                        case "E":
                            taskList.add(EventTask.deserialize(serialized));
                            break;
                        default:
                            // Silently ignore tasks that are not in the correct format
                            break;
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

    public void save(TaskList taskList) throws GabbyException {
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
