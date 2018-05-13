package task.mozilla9025.com.taskmanager.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import task.mozilla9025.com.taskmanager.models.Project;
import task.mozilla9025.com.taskmanager.models.Task;

public final class JsonParser {

    public JsonParser() {
    }

    public List<Task> parseTaskList(String s) throws JSONException {
        List<Task> tasks = new ArrayList<>();
        JSONObject json = new JSONObject(s);
        JSONArray result = json.getJSONArray("result");
        if (result.length() > 0) {
            for (int i = 0; i < result.length(); i++) {
                JSONObject temp = result.getJSONObject(i);
                Integer id = temp.getInt("id");
                String title = temp.getString("title");
                String color = temp.getString("color");
                String description = temp.getString("description");
                Long scheduledTo = temp.getLong("scheduled_to");
                Long dueDate = temp.getLong("due_date");
                Long created = temp.getLong("created_at");
                Integer projectId = temp.getInt("project_id");
                tasks.add(Task.createCompleteTask(id, title, color, description,
                        scheduledTo, dueDate, created, projectId));
            }
        }
        return tasks;
    }

    public Task parseTask(String s) throws JSONException {
        JSONObject json = new JSONObject(s);
        JSONObject temp = json.getJSONObject("task");
        Integer id = temp.getInt("id");
        String title = temp.getString("title");
        String color = temp.getString("color");
        String description = temp.getString("description");
        Long scheduledTo = temp.getLong("scheduled_to");
        Long dueDate = temp.getLong("due_date");
        Long created = temp.getLong("created_at");
        Integer projectId = temp.getInt("project_id");
        return Task.createCompleteTask(id, title, color, description,
                scheduledTo, dueDate, created, projectId);
    }

    public List<Project> parseProjectList(String s) throws JSONException {
        List<Project> projects = new ArrayList<>();
        JSONObject json = new JSONObject(s);
        JSONArray result = json.getJSONArray("projects");
        if (result.length() > 0) {
            for (int i = 0; i < result.length(); i++) {
                JSONObject temp = result.getJSONObject(i);
                Integer id = temp.getInt("id");
                String name = temp.getString("name");
                String description = temp.getString("description");
                String color = temp.getString("color");
                Long created = temp.getLong("created");
                Integer taskCount = temp.getInt("task_count");
                projects.add(Project.createCompleteProject(id, name, description,
                        color, created, taskCount));
            }
        }
        return projects;
    }

    public Project parseProject(String s) throws JSONException {
        JSONObject json = new JSONObject(s);
        JSONObject temp = json.getJSONObject("project");
        Integer id = temp.getInt("id");
        String name = temp.getString("name");
        String description = temp.getString("description");
        String color = temp.getString("color");
        Long created = temp.getLong("created");
        Integer taskCount = temp.has("task_count") ? temp.getInt("task_count") : 0;
        return Project.createCompleteProject(id, name, description,
                color, created, taskCount);

    }

}
