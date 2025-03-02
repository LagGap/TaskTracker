package tasktracker.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tasktracker.model.Status;
import tasktracker.model.Task;
import tasktracker.repository.TasktrackerRepo;

public class TasktrackerService implements ITasktracker {

    private final TasktrackerRepo repo;
    private Map<Integer, Task> tasksMapToManipulate;

    public TasktrackerService(TasktrackerRepo repo) {
        this.repo = repo;
        this.tasksMapToManipulate = repo.getMapFromJson();
    }

    @Override
    public void saveTaskInRepo() {
        this.repo.saveMapToJson(this.tasksMapToManipulate);
    }

    @Override
    public void addTask(Task newTask) {
        this.tasksMapToManipulate.put(newTask.getId(), newTask);
        saveTaskInRepo();
    }

    @Override
    public void updateTask(int id, String description) {
        Task taskToModify = this.tasksMapToManipulate.get(id);
        Task modifiedTask = new Task(taskToModify.getId(), description, taskToModify.getStatus(),
                taskToModify.getCreatedAt(), LocalDateTime.now());

        deleteTask(taskToModify.getId());
        addTask(modifiedTask);

    }

    @Override
    public void deleteTask(int id) {
        this.tasksMapToManipulate.remove(id);
        saveTaskInRepo();
    }

    @Override
    public void changeTaskStatus(int id, Status newStatus) {
        Task taskToModify = this.tasksMapToManipulate.get(id);
        Task modifiedTask = new Task(taskToModify.getId(), taskToModify.getDescription(), newStatus,
                taskToModify.getCreatedAt(), LocalDateTime.now());

        deleteTask(taskToModify.getId());
        addTask(modifiedTask);

    }

    @Override
    public List<Task> getAllTask() {
        List<Task> listToReturn = new ArrayList<>(this.tasksMapToManipulate.values());
        return listToReturn;
    }

    @Override
    public List<Task> getAllTaskByStatus(Status status) {
        List<Task> listToReturn = new ArrayList<>();

        this.tasksMapToManipulate.forEach((key, value) -> {
            if (value.getStatus() == status) {
                listToReturn.add(value);
            }
        });

        return listToReturn;
    }

}
