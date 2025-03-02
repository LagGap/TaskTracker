package tasktracker;
import tasktracker.cli.TasktrackerCLI;
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        TasktrackerCLI cli = new TasktrackerCLI();
        cli.initializeApp();
    }
}
