// Superclass of 3 subclasses: Todo, Deadline and Event
public class Task {
    // 3 class attributes
    protected String description;
    protected boolean isDone;
    protected int index;

    boolean errorCheck;

    // constructor
    public Task(String description, int index) {
        setDescription(description);
        setDone();
        setCounter(index);
        printHeaders();

    }

    // Set for description attribute
    public void setDescription(String description) {
        this.description = description;
    }

    // Set if task attribute is done/undone
    public void setDone() {
        this.isDone = false;
    }

    // Set index attribute of the task
    public void setCounter(int index) {
        this.index = index;
    }

    // Get status icon
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    // Standard 2 lines that will always get printed out
    public void printHeaders() {
/*        return ("""
                ____________________________________________________________
                Got it. I've added this task:
                """);*/
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
    }

/*
    public void updateList(int index, Task[] list) {
        list[counter] = t;
    }
*/


    public String toString() {
        String[] original = description.split("/"); // split if there is 1 or more whitespace
        String value = String.valueOf(original[0]);

        String[] splitLine = value.split("\\s+"); // split if there is 1 or more whitespace
        StringBuilder output = new StringBuilder();
/*
        if (splitLine.length < 2) {
            try {
                throw new DukeException();
            } catch (DukeException e) {
                System.out.println("Minimally 2 arguments");
                return null;
            }
        }*/


        for (int i = 1; i < splitLine.length; i += 1) {
            output.append(splitLine[i]).append(" ");
        }

        return "[" + this.getStatusIcon() + "] " + output;
    }

}