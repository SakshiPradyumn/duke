import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Specifications for Task list.
 */
public class TaskList {

    private List<Task> tasks;

    /**
     * Creates an instance of TaskList with empty new arrayList of tasks.
     *
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Creates an instance of TaskList with input arrayList of tasks.
     *
     * @param tasks list of tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return size of task list.
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Returns ith Task element from the task list.
     *
     * @param i index of Task in the list of tasks.
     * @return Task at index i of the list.
     */
    public Task getElement(int i) {
        return this.tasks.get(i);
    }

    /**
     * Converts time to particular format.
     *
     * @param n number as integers.
     * @return String representing ordinal number nth.
     */
    private static String getTimeFormat(int n) {
        if (n >= 11 && n <= 13) {
            return n + "th";
        }
        switch (n % 10) {
            case 1:
                return n + "st of";
            // Fallthrough
            case 2:
                return n + "nd of";
            // Fallthrough
            case 3:
                return n + "rd of";
            // Fallthrough
            default:
                return n + "th of";
            // Fallthrough
        }
    }

    /**
     * Converts command of users into Task objects with relevant details.
     *
     * @param s String used by user for creating a new task.
     * @return a Task object with input details
     * @throws DukeException Exception thrown when input is of invalid format.
     */
    public Task add(String s) throws DukeException {
        String[] arr = s.split(" ");
        String tasksType = arr[0];
        String tasksDescr = "";
        String tasksTime = "";

        // Get task description
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].length() >= 1 && arr[i].charAt(0) == '/') {
                // Get task time
                for (int j = i + 1; j < arr.length; j++) {
                    tasksTime += " " + arr[j];
                }
                break;
            } else {
                tasksDescr += " " + arr[i];
            }
        }
        tasksDescr = tasksDescr.trim();
        tasksTime = tasksTime.trim();

        // Handle exceptions
        if (!(tasksType.equals("todo") || tasksType.equals("deadline") || tasksType.equals("event"))) {
            throw new DukeException("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        if (tasksDescr.equals("")) throw new DukeException(
                "\u2639 OOPS!!! The description of a " + tasksType + " cannot be empty.");
        if ((tasksType.equals("deadline") || tasksType.equals("event")) && tasksTime.equals(""))
            throw new DukeException(
                    "\u2639 OOPS!!! The time of a " + tasksType + " cannot be empty.");

        // Converts into required date format.
        try {
            Date date = new SimpleDateFormat("d/MM/yyyy HHmm").parse(tasksTime);
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy, hh:mm a");
            tasksTime = formatter.format(date);
            String[] array = tasksTime.split(" ");
            array[0] = getTimeFormat(Integer.valueOf(array[0]));
            array[array.length - 1] = array[array.length - 1].toLowerCase();
            tasksTime = "";
            for (int i = 0; i < array.length; i++) {
                tasksTime += " " + array[i];
            }
            tasksTime = tasksTime.trim();
        } catch (ParseException e) {

        }

        // Creates new task.
        Task task;
        if (tasksType.equals("todo")) {
            task = new ToDo(tasksDescr);
            tasks.add(task);
            return task;
        } else if (tasksType.equals("deadline")) {
            task = new Deadline(tasksDescr, tasksTime);
            tasks.add(task);
            return task;
        } else if (tasksType.equals("event")) {
            task = new Event(tasksDescr, tasksTime);
            tasks.add(task);
            return task;
        } else {
            return null;
        }
    }


    /**
     * Marks the nth task in the task list as done.
     *
     * @param n the position of task in the list of tasks.
     * @return the task marked as done.
     */
    public Task done(int n) {
        Task task = this.tasks.get(n - 1);

        task.mark();
        return task;
    }

    /**
     * Deletes nth task from the list of tasks.
     *
     * @param n the position of task in the list of tasks.
     * @return the task deleted from the lists.
     */
    public Task delete(int n) {
        Task task = this.tasks.get(n - 1);

        this.tasks.remove(task);
        return task;
    }

    /**
     * Converts task into a String format to be stored in the data file.
     *
     * @return a String representation to be stored in the task list file.
     */
    public String generateInfo() {

        String taskFile = "";
        taskFile = getTimeRefact(taskFile);
        return taskFile;
    }

    private String getTimeRefact(String taskFile) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String current;
            if (task.getTimeLabel().equals("")) {
                current = task.getLabel() + " | " + task.getInfo() + " | " + task.getDescription();
            } else {
                current = task.getLabel() + " | " + task.getInfo() + " | " + task.getDescription()
                        + " | " + task.getTimeLabel();
            }
            if (i != tasks.size() - 1) {
                taskFile += current + System.lineSeparator();
            } else {
                taskFile += current;
            }
        }

        return taskFile;
    }
}