package tasktracker.cli;

import java.util.List;
import java.util.Scanner;

import tasktracker.model.Status;
import tasktracker.model.Task;
import tasktracker.repository.TasktrackerRepo;
import tasktracker.service.TasktrackerService;

public class TasktrackerCLI {

    private final Scanner scanner;
    private TasktrackerService service;

    public TasktrackerCLI() {
        this.scanner = new Scanner(System.in);
        this.service = new TasktrackerService(new TasktrackerRepo(TasktrackerRepo.dirPathString));
    }

    public void initializeApp() {
        printHelper();

        String command;
        System.out.print("enter command: ");

        while (!(command = scanner.next().toLowerCase()).equals("exit")) {
            switch (command) {
                case "add":
                    String line = scanner.nextLine().trim();
                    addNewTask(line);
                    break;
                case "update":
                    updateTask(scanner.next(), scanner.nextLine().trim());
                    break;
                case "delete":
                    deleteTask(scanner.nextLine().trim());
                    break;
                case "list":
                    printListOfAllTasks();
                    break;
                case "list-status":
                    printListOfTasksByStatus(scanner.next());
                    break;
                case "change-status":
                    changeTaskStatusWithId(scanner.next(), scanner.next());
                    break;
                case "help":
                    printHelper();
                    break;
                default:
                    System.out.println("unknown command type help to see the options");
                    break;
            }
            System.out.print("enter command: ");
        }
    }

    private void addNewTask(String desc) {
        if (!desc.isEmpty()) {
            Task newTask = new Task(desc); 
            service.addTask(newTask);
            System.out.println("A new task as been added at -> " + newTask.getCreatedAt());
        }else
        {
            System.out.println("the description cannot be empty or null try again ");
        }    
    }

    private void updateTask(String idString, String newDesc) {
        if (idString != null && !idString.trim().isEmpty() || newDesc != null && !newDesc.trim().isEmpty()) {
            if (isStringNumeric(idString)) { // information taken here:
                // https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
                service.updateTask(Integer.parseInt(idString), newDesc);
            } else {
                System.out.println("id is not a number");
            }
        } else {
            System.out.println("description or id cant be null or empty");
        }
    }

    private boolean isStringNumeric(String idString) {
        return idString.chars().allMatch(Character::isDigit);
    }

    private void deleteTask(String idString) {
        if (isStringNumeric(idString)) {
            service.deleteTask(Integer.parseInt(idString));
        }
    }

    private void printListOfAllTasks() {
        listPrinter(service.getAllTask());
    }

    private void printListOfTasksByStatus(String statusString) {
        if (isStringNumeric(statusString)) {
            Status status = Status.fromInt(Integer.parseInt(statusString));
            listPrinter(service.getAllTaskByStatus(status));
        } else {
            System.out.println("status must be a number");
        }
    }

    private void changeTaskStatusWithId(String idString, String newStatus) {
        if (isStringNumeric(idString) && isStringNumeric(newStatus)) {
            Status status = Status.fromInt(Integer.parseInt(newStatus));
            service.changeTaskStatus(Integer.parseInt(idString), status);
        }
    }

    private void printHelper() {
        String helper =
                "- add [description] : Add a new task\r\n" + //
                "- update [id] [description] : Modify a task description for specify id\r\n" + //
                "- delete [id] : Delete a specific task with a given [id]\r\n" + //
                "- list : returns all tasks\r\n" + // "
                "- list-status [status] : returns a list of tasks with a given [status] -> 0: TO_DO, 1: IN_PROGRESS, 2: DONE\r\n"
                + //
                "- change-status [id] [status] changes the status of given task by [id] [status] -> 0: TO_DO, 1: IN_PROGRESS, 2: DONE\r\n";
        System.out.println(helper);
    }

    private void listPrinter(List<Task> taskList) {
        System.out.println("List of tasks");
        System.out.println("-----------------------------");
        for (Task task : taskList) {
            System.out.println("task id -> " + task.getId());
            System.out.println("task description -> " + task.getDescription());
            System.out.println("task Status -> " + task.getStatus());
            System.out.println("task creation -> " + task.getCreatedAt());
            System.out.println("task last update -> " + task.getUpdatedAt());
            System.out.println("-----------------------------");
        }
    }
}
