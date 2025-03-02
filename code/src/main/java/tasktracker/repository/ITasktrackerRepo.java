package tasktracker.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import tasktracker.model.Task;

public interface ITasktrackerRepo {
    public Map<Integer, Task> getMapFromJson();
    public void saveMapToJson(Map<Integer, Task> map) throws FileNotFoundException, IOException;
}
