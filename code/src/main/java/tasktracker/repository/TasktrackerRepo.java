package tasktracker.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tasktracker.model.Status;
import tasktracker.model.Task;

public class TasktrackerRepo implements ITasktrackerRepo {

    public static String dirPathString = "code/src/main/java/tasktracker/db.json";
    private String path;

    public TasktrackerRepo(String pathString) {
        this.path = pathString;
        Status.values();
    }

    @Override
    public Map<Integer, Task> getMapFromJson() {
        Map<Integer, Task> map = new HashMap<>();
        try {
            File jsonFile = new File(this.path);
            if (jsonFile.createNewFile()) {
                System.out.println("file does not exists... creating a new json file");
            }
            String dbString = getStringFromJsonFile(jsonFile);

            List<String> jsonStringList = ExtractStringListOfTasksFromJsonFileString(dbString);

            for (String element : jsonStringList) {
                Task taskFromJson = new Task(); // creating an empty task to feed with information
                element = element.substring(1, element.length() - 1); // removing "{" and "}"
                String[] keyValuePairs = element.split(","); // Separation of each attribute of the class
                for (String pair : keyValuePairs) {
                    String[] entry = pair.split(":", 2); // Separation of the key and value
                    String key = entry[0].substring(1, entry[0].length() - 1); // removing last ' "" ' from string
                    String value = entry[1];
                    switch (key) {
                        case "id":
                            int id = Integer.parseInt(value);
                            taskFromJson.setId(id);
                            break;
                        case "description":
                            value = value.substring(1, entry[1].length() - 1);
                            String description = value;
                            taskFromJson.setDescription(description);
                            break;
                        case "status":
                            int statusint = Integer.parseInt(value);
                            Status status = Status.fromInt(statusint);
                            taskFromJson.setStatus(status);
                            break;
                        case "createdAt":
                            LocalDateTime createdAt = getDateFromString(entry, value);
                            taskFromJson.setCreatedAt(createdAt);
                            break;
                        case "updatedAt":
                            LocalDateTime updatedAt = getDateFromString(entry, value);
                            taskFromJson.setUpdatedAt(updatedAt);
                            break;
                        default:
                            break;
                    }
                }
                map.put(taskFromJson.getId(), taskFromJson);
            }

            Task.setLastGeneratedId(map);
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return map;
    }

    private LocalDateTime getDateFromString(String[] entry, String value) {
        value = value.substring(1, entry[1].length() - 1);
        LocalDateTime createdAt = LocalDateTime.parse(value);
        return createdAt;
    }

    private List<String> ExtractStringListOfTasksFromJsonFileString(String data) {
        List<String> listOfString = new ArrayList<String>();
        Pattern pattern = Pattern.compile("\\{.*?\\}");
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            String objString = matcher.group();
            listOfString.add(objString);
        }
        return listOfString;
    }

    private String getStringFromJsonFile(File jsonFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(jsonFile);
        String data = new String();
        while (scanner.hasNextLine()) {
            data = scanner.nextLine();
            data = data.substring(1, data.length() - 1);
        }
        scanner.close();
        return data;
    }

    @Override
    public void saveMapToJson(Map<Integer, Task> map) {
        List<String> list = getStringListFromTaskMap(map);

        String jsonString = "[";
        for (int i = 0; i < list.size(); i++) {
            jsonString += list.get(i);
            if (i == list.size() - 1) {
                jsonString +="]";
            } else {
                jsonString +=",";
            }
        }

        try {
            FileWriter writer = new FileWriter(new File(this.path));
            writer.write(jsonString);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private List<String> getStringListFromTaskMap(Map<Integer, Task> map) {
        List<String> list = new ArrayList<String>();
        for (Task task : map.values()) {
            String toJsonTask = task.toJsonString();
            list.add(toJsonTask);
        }
        return list;
    }
}
