import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private List<Task> tasks = new ArrayList<Task>();
    private String fPath;

    public Storage(String fPath) {
        this.fPath = fPath;
    }

    public void writeOnFile(String ftext) throws IOException {
        FileWriter fwrit = new FileWriter(fPath); //Creates a FileWriter object for the given file path
        fwrit.write(ftext);
        fwrit.close();
    }

    /*
    public static void getFileText(ChatLike cl) throws FileNotFoundException, DukeException {
        File f = new File(fPath); // create a File for the given file path
        Scanner S = new Scanner(f); // create a Scanner using the File as the source
        while (S.hasNext()) {
            String temp = S.nextLine();
            cl.readFromFile(temp);
        }
    }
*/

    public List<Task> load() throws FileNotFoundException, DukeException {
        File f = new File(fPath); // create a File for the given file path
        Scanner s = new Scanner(f); // create a Scanner using the File as the source
        if (!s.hasNext())
            throw new DukeException("Your task list is currently empty.");

        while (s.hasNext()) {
            String str = s.nextLine();
            String arr[] = str.split(" \\| ");
            String tasksType = arr[0];
            int tasksInfo = Integer.valueOf(arr[1]);
            String tasksDescr = arr[2];
            String tasksTime = "";

            Task task;
            if (tasksType.equals("T")) {
                task = new ToDo(tasksDescr);
                tasks.add(task);
                if (tasksInfo == 1)
                    task.mark();
            } else if (tasksType.equals("D")) {
                tasksTime = arr[3];
                task = new Deadline(tasksDescr, tasksTime);
                tasks.add(task);
                if (tasksInfo == 1)
                    task.mark();
            } else if (tasksType.equals("E")) {
                tasksTime = arr[3];
                task = new Event(tasksDescr, tasksTime);
                tasks.add(task);
                if (tasksInfo == 1)
                    task.mark();
            }
        }

        return tasks;
    }

}