/**
 * Deals with interactions with the user in the window.
 */
public class Ui {
    private String response;

    /**
     *Prints out the response to be displayed to the user.
     */
    private String getResponseDirect() {
        return this.response;
    }


    /**
     * Prints out the response when user adds a new task.
     *
     * @param task task added by user.
     * @param tasks the list of tasks.
     * @return String representing the response.
     */
    public String add(Task task, TaskList tasks) {
        //update response
        this.response = "Got it. I've added this task:\n       " + task
                + "\n     Now you have " + tasks.getSize() + " tasks in the list.";
        return getResponseDirect();
    }

    /**
     * Prints out the response showing a list of tasks.
     *
     * @param tasks the list of tasks.
     * @return String representing the response.
     */
    public String list(TaskList tasks) {

        // Get the list of tasks in the Arraylist
        String listOfTasks = "Here are the tasks in your list:\n     ";
        assert listOfTasks == "Here are the tasks in your list:\n     " : "Oops";
        for (int i = 0; i < tasks.getSize(); i++) {
            listOfTasks += (i + 1) + "." + tasks.getElement(i);
            if (i != tasks.getSize() - 1) listOfTasks += "\n     ";
        }
        // Update response
        this.response = listOfTasks;
        return getResponseDirect();
    }

    /**
     * Prints out the response when user exits the application.
     *
     * @return String representing the response.
     */
    public String exit() {
        //update response
        this.response = "Bye. Hope to see you again soon!";
        return getResponseDirect();
    }

    /**
     * Prints out the response when the user marks a task in the list as done.
     *
     * @param task task done by user.
     * @return String representing the response.
     */
    public String done(Task task) {
        // Update response
        this.response = "Nice! I've marked this task as done:\n       " + task;
        return getResponseDirect();
    }

    /**
     * Displays the response when the user deletes a task from the list.
     *
     * @param task task to be deleted.
     * @param taskList the list of tasks.
     * @return String representing the response.
     */
    public String delete(Task task, TaskList taskList) {
        // Update response
        this.response = "Noted. I've removed this task: \n       " + task
                + "\n     Now you have " + taskList.getSize() + " tasks in the list.";
        return getResponseDirect();
    }

    /**
     * Finds a task in the list of tasks which contains a keyword as part of its description.
     *
     * @param taskList a <code>TaskList</code> instance containing a list of tasks.
     * @param key   the keyword to be compared against each task description.
     * @throws DukeException Exceptions thrown when there are more than/ less than one keyword specified.
     * @return String representing the response.
     */
    public String find(TaskList taskList, String key) throws DukeException {
        if (key.split(" ").length > 1 || key.equals(" ")) {
            throw new DukeException("\u2639 OOPS!!! Please include at most/ at least one keyword.");
        }
        // Get the list of tasks in the tasks list.
        String listOfTasks = "Here are the matching tasks in your list:\n     ";
        boolean isFound = false;
        for (int i = 0; i < taskList.getSize(); i++) {
            String[] words = taskList.getElement(i).getDescription().split(" ");
            boolean containsKey = false;
            for (int j = 0; j < words.length; j++) {
                if (key.equalsIgnoreCase(words[j])) {
                    containsKey = true;
                    break;
                }
            }
            if (containsKey) {
                isFound = true;
                listOfTasks += (i + 1) + "." + taskList.getElement(i);
                listOfTasks += "\n     ";
            }
        }
        // Update response
        updateResponse(listOfTasks, isFound);
        return getResponseDirect();
    }

    private void updateResponse(String listOfTasks, boolean isFound) {
        if (isFound) {
            int index = listOfTasks.lastIndexOf("\n     ");
            if (index > 0) {
                listOfTasks = listOfTasks.substring(0, index);
            }
            this.response = listOfTasks;
        } else {
            this.response = "\u2639 OOPS!!! There is no matching tasks in your list.";
        }
    }
}