package main;

import java.util.Scanner;
import java.util.ArrayList;
import tasks.Task;
import tasks.Todo;
import tasks.Deadline;
import tasks.Event;

public class Duke {

    public static boolean removeElement(ArrayList<Task> list, String[] splitLine, int counter) {
        try {
            Task t = list.get(Integer.parseInt(splitLine[1]) - 1);
            list.remove(Integer.parseInt(splitLine[1]) - 1);
            System.out.println("____________________________________________________________");
            System.out.println("Noted. I've removed this task:");
            System.out.println(t);
            System.out.println("Now you have " + (counter - 1) + " tasks in the list.");

        } catch(IndexOutOfBoundsException e) {
            System.out.println("Invalid index, please try again!");
            return false;
        }
        return true;

    }

    public enum Commands {
        Todo, Deadline, Event
    }
    public static boolean addTask(ArrayList<Task> list, String line, String[] splitLine, Commands typeOfTask) {
        Task t;
        boolean success = true;

        switch (typeOfTask) {
            case Todo:
                if (checkMinimumArguments(splitLine, 2)) {
                    success = false;
                    break;
                }
                try {
                    t = new Todo(line);
                    list.add(t);
                } catch (RuntimeException e) {
                    System.out.println("Invalid Syntax, please try again!");
                    success = false;

                }
                break;

            case Deadline:
                if (checkMinimumArguments(splitLine, 4)) {
                    success = false;
                    break;
                }
                try {
                    t = new Deadline(line);
                    list.add(t);
                } catch (DukeException e) {
                    System.out.println("Invalid Syntax, please try again!");
                    success = false;
                }
                break;

            case Event:
                if (checkMinimumArguments(splitLine, 8)) {
                    success = false;
                    break;
                }
                try {
                    t = new Event(line);
                    list.add(t);
                } catch (RuntimeException e) {
                    System.out.println("Invalid Syntax, please try again!");
                    success = false;
                }
                break;

        }
        return success;
    }


    // Function to mark or unmark tasks
    public static void userMarkOrUnmark(String command, String line, ArrayList<Task> list) {

        // User enters Mark, proceed to check if index is valid. If valid, then mark the task number
        int index;
        Task t;
        if (command.equals("mark")) {
            try {
                index = Integer.parseInt(line.substring(5));
            } catch (NumberFormatException e) {
                System.out.println("Task number is not a valid number");
                return;
            }
            t = list.get(index - 1);
            t.isDone =  true;
            System.out.println("Nice! I've marked this task as done:");
        }

        // User enters unmark, proceed to check if index is valid. If valid, then unmark the task number
        else {
            try {
                index = Integer.parseInt(line.substring(7));
            } catch (NumberFormatException e) {
                System.out.println("Task number is not a valid number");
                return;
            }
            t = list.get(index - 1);
            t.isDone =  false;
            System.out.println("OK, I've marked this task as not done yet:");
        }

        System.out.println(t);
        System.out.println("____________________________________________________________");
    }

    // Function to print the list of tasks
    public static void userList(ArrayList<Task> list) {
            for (Task task : list) {
                if (task == null) {
                    break;
                }
                System.out.println(list.indexOf(task) + 1 + ". " + task);
            }
            System.out.println("____________________________________________________________");
    }

    // Function to say bye
    public static void userBye() {
        System.out.println("Bye human. Come back soon !");
    }

    // Function to print an unknown command from user
    public static void userWrongCommand() {
        System.out.println("No suitable command found. Please try again!");
    }

    // Function to check the minimum arguments supplied for each task
    public static boolean checkMinimumArguments(String[] splitLine, int number) {
        try {
            if (splitLine.length < number) {
                throw new DukeException("Invalid Syntax! Please try again!");
            }
        } catch (DukeException e) {
            System.out.println("Minimally " + number + " arguments, please try again!");
            return true;
        }
        return false;
    }

    // Start of user input
    public static void userInput() {
        Scanner scanner = new Scanner(System.in);
        String line;
        ArrayList<Task> list = new ArrayList<>();
        int counter = 0;

        // Start of user input
        while (true) {
            line = scanner.nextLine().toLowerCase(); // Takes in user input
            String[] splitLine = line.split("\\s+"); // split by whitespaces
            String command = splitLine[0]; //obtain the main command from user, which is the first command

            switch (command) {
                // User wants to exit
                case "bye":
                    userBye();
                    break;

                // User wants to display the list of tasks
                case "list":
                    if (splitLine.length != 1) {
                        userWrongCommand();
                        break;
                    }
                    userList(list);
                    continue;

                // User wants to mark or unmark tasks
                case "mark":
                case "unmark":
                    userMarkOrUnmark(splitLine[0], line, list);
                    continue;

                // User wants a todo task
                case "todo":
                    if (addTask(list, line, splitLine, Commands.Todo)) {
                        counter += 1;
                        System.out.println("Now you have " + counter + " tasks in the list.");
                    }
                    continue;

                // User wants a deadline task
                case "deadline":
                    if (addTask(list, line, splitLine, Commands.Deadline)) {
                        counter += 1;
                        System.out.println("Now you have " + counter + " tasks in the list.");
                    }
                    continue;

                // User wants an event task
                case "event":
                    if (addTask(list, line, splitLine, Commands.Event)) {
                        counter += 1;
                        System.out.println("Now you have " + counter + " tasks in the list.");
                    }
                    continue;

                // User wants to delete task
                case "delete":
                    if (removeElement(list, splitLine, counter)) {
                        counter -= 1;
                    }
                    continue;

                default:
                    System.out.println("No suitable command found. Please try again!");
                    continue;
            }
            scanner.close(); // Close scanner after usage
            return;
        }
    }

    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Bob");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        userInput();
    }
}