import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Deals with loading tasksList from the file and saving tasksList in the file after change.
 */
public class Storage {
    private List<Task> tasksList = new ArrayList<Task>();
    private String fPath;

    /**
     * Creates a new Storage instance with a file path.
     *
     * @param fPath Stores the path of the file.
     */
    public Storage(String fPath) {
        this.fPath = fPath;
    }

    /**
     * Creates a new FileWriter object for the file path to write on.
     *
     * @param ftext Stores the text to be written on the file.
     * @throws IOException Exception produced if some failure.
     */
    public void writeOnFile(String ftext) throws IOException {
        FileWriter fwrit = new FileWriter(fPath); //Creates a FileWriter object for the given file path
        fwrit.write(ftext);
        fwrit.close();
    }

    /**
     * Loads file from the file path and converts instances into <code>Task</code> objects.
     *
     * @return list of tasksList.
     * @throws FileNotFoundException Exception produced if file not found here.
     * @throws DukeException in case of empty file.
     */

    public List<Task> load() throws FileNotFoundException, DukeException {
        File file = new File(fPath); // create a File for the given file path
        Scanner sc = new Scanner(file); // create a Scanner using the File as the source
        if (!sc.hasNext())
            throw new DukeException("Your task list is currently empty.");

        while (sc.hasNext()) {
            String str = sc.nextLine();
            String arr[] = str.split(" \\| ");
            String tasksType = arr[0];
            int tasksInfo = Integer.valueOf(arr[1]);
            String tasksDescr = arr[2];
            String tasksTime = "";

            Task task;
            checkTaskAndMark(arr, tasksType, tasksInfo, tasksDescr);
        }
        return tasksList;
    }

    private void checkTaskAndMark(String[] arr, String tasksType, int tasksInfo, String tasksDescr) {
        Task task;
        String tasksTime;
        if (tasksType.equals("T")) {
            task = new ToDo(tasksDescr);
            tasksList.add(task);
            assert tasksType == "T": "It should be T here";
            if (tasksInfo == 1)
                task.mark();
        } else if (tasksType.equals("D")) {
            tasksTime = arr[3];
            task = new Deadline(tasksDescr, tasksTime);
            tasksList.add(task);
            assert tasksType == "D": "It should be D here";
            if (tasksInfo == 1)
                task.mark();
        } else if (tasksType.equals("E")) {
            tasksTime = arr[3];
            task = new Event(tasksDescr, tasksTime);
            tasksList.add(task);
            assert tasksType == "E": "It should be E here";
            if (tasksInfo == 1)
                task.mark();
        }
    }

}
