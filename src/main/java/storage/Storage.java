package storage;

import main.DukeException;
import tasks.Deadline;
import tasks.Event;
import tasks.TaskList;
import tasks.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    static File f = new File("data.txt");

    /**
     * Returns a list of tasks in type Task for usage in code.
     * @param listString list of tasks in type String.
     * @return list of tasks in type Task.
     */
    public static ArrayList<TaskList> changePresentationFormat(ArrayList<String> listString) throws DukeException {
        ArrayList<TaskList> listTask = new ArrayList<>();
        String[] splitEntireLine = new String[0], splitInput;
        String mark = null, command = null;
        TaskList t;
        for (String task: listString) {
            try {
                splitEntireLine = task.split(":", 2); // split by whitespaces
                mark = splitEntireLine[0];
                splitInput = splitEntireLine[1].split(" ");
                command = splitInput[0];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Unable to read the tasks in file successfully, Bob will stop running now.");
                System.exit(1);
            }
            
            switch (command) {
                case "todo":
                    t = new Todo(splitEntireLine[1], false);
                    break;
                case "deadline":
                    t = new Deadline(splitEntireLine[1], false);
                    break;
                case "event":
                    t = new Event(splitEntireLine[1], false);
                    break;
                default:
                    t = null;
                    break;
            }

            if (mark.equals("Marked")) {
                assert t != null;
                t.setIsDone(true);
            }
            else if (mark.equals("notMarked")) {
                assert t != null;
                t.setIsDone(false);
            }
            else {
                System.out.println("Unable to read the tasks in file successfully, Bob will stop running now.");
                System.exit(1);
            }
            listTask.add(t);
        }
        return listTask;
    }


    /**
     * Load data from file in string data type.
     */
     public static ArrayList<String> loadData() {
        ArrayList<String> listString = new ArrayList<>();

        // Try to create a file if it does not exist
        try {
            if (!f.exists()) {
                boolean created = f.createNewFile();
                if (created) {
                    System.out.println("File created successfully.");
                } else {
                    System.out.println("Failed to create the file.");
                }
            }

            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                listString.add(s.nextLine());
            }
            return listString;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Save the list of tasks in our file as string Data type.
     * @param listString list of tasks in type String.
     */
    public static void saveData(ArrayList<String> listString) {
        try (FileWriter fw = new FileWriter(f)) {
            for (String task : listString) {
                fw.write(task + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }



}
