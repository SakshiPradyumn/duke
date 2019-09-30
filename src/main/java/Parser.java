import java.io.IOException;

/**
 * Deals with reading and making sense of the user commands.
 */
public class Parser {

    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    /**
     * Creates a new parser instance.
     *
     * @param storage Storage instance to be used.
     * @param taskList TaskList containing task list.
     * @param ui Ui instance to be used.
     */
    public Parser(Storage storage, TaskList taskList, Ui ui) {
        this.storage = storage;
        this.taskList = taskList;
        this.ui = ui;
    }

    /**
     * Reads user input as commands, deciphers commands and carries out the commands correspondingly.
     * Instructs Ui to return different messages to interact with users.
     *
     * @param command user input.
     * @return String to be displayed as reply to the user.
     * @throws DukeException self-defined exceptions caused by illegal input.
     */
    public String parse(String command) throws DukeException {
        // Read user input
        try {
            if (command.equals("bye")) {
                return ui.exit();
            } else if (command.equals("list")) {
                return ui.list(taskList);
            } else if (command.length() > 5 && command.substring(0, 4).equals("done")) {
                if (command.charAt(4) != ' ') {
                    Task task = taskList.add(command);
                    return ui.add(task, taskList);

                } else {
                    Task task = taskList.done(Integer.parseInt(command.substring(5)));
                    String response = ui.done(task);
                    storage.writeOnFile(taskList.generateInfo());
                    return response;
                }
            } else if (command.length() > 7 && command.substring(0, 6).equals("delete")) {
                return getString(command);
            } else if (command.length() > 5 && command.substring(0, 4).equals("find")) {
                if (command.charAt(4) != ' ') {
                    Task task = taskList.add(command);
                    return ui.add(task, taskList);
                } else {
                    String response = ui.find(taskList, command.substring(5));
                    storage.writeOnFile(taskList.generateInfo());
                    return response;
                }
            } else if (command.substring(0, 4).equals("help")) {
                Help.helpFile();
            } else {
                Task task = taskList.add(command);
                String response = ui.add(task, taskList);
                storage.writeOnFile(taskList.generateInfo());
                return response;
            }
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
        return null;
    }

    private String getString(String command) throws DukeException, IOException {
        if (command.charAt(6) != ' ') {
            Task task = taskList.add(command);
            return ui.add(task, taskList);
        } else {
            Task task = taskList.delete(Integer.parseInt(command.substring(7)));
            String response = ui.delete(task, taskList);
            storage.writeOnFile(taskList.generateInfo());
            return response;
        }
    }
}