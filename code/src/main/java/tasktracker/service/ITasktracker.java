package tasktracker.service;
import java.util.List;

import tasktracker.model.Status;
import tasktracker.model.Task;

public interface ITasktracker {
    public void saveTaskInRepo();
    
    public void addTask(Task newTask);
    public void updateTask(int id, String description);
    public void deleteTask(int id);
    public void changeTaskStatus(int id, Status newStatus);

    public List<Task> getAllTask();    

    public List<Task> getAllTaskByStatus(Status status);
}
