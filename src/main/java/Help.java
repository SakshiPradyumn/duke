public class Help {
    public Help() {

    }

    static void helpFile() {
        System.out.println("/n _______________________________________________" +
                "Use following functions:");
        System.out.println("/n _______________________________________________" +
                "todo <task> => for an unscheduled event");
        System.out.println("/n _______________________________________________" +
                "deadline <task> <time> => for an event to be done by particular time");
        System.out.println("/n _______________________________________________" +
                "event <task> <time> => for a scheduled event");
        System.out.println("/n _______________________________________________" +
                "find <keyword> => finds a keyword in the list");
    }
}
