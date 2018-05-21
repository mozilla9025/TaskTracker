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

    public boolean parseStatus(String s) throws JSONException {
        JSONObject json = new JSONObject(s);
        return json.getBoolean("status");
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
                String color = temp.isNull("color") ? null : temp.getString("color");
                String description = temp.isNull("description") ? null : temp.getString("description");
                Integer scheduledTo = temp.isNull("scheduled_to") ? null : temp.getInt("scheduled_to");
                Integer dueDate = temp.isNull("due_date") ? null : temp.getInt("due_date");
                Integer created = temp.getInt("created_at");
                Integer projectId = temp.isNull("project_id") ? null : temp.getInt("project_id");
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
        String color = temp.isNull("color") ? null : temp.getString("color");
        String description = temp.isNull("description") ? null : temp.getString("description");
        Integer scheduledTo = temp.isNull("scheduled_to") ? null : temp.getInt("scheduled_to");
        Integer dueDate = temp.isNull("due_date") ? null : temp.getInt("due_date");
        Integer created = temp.getInt("created_at");
        Integer projectId = temp.isNull("project_id") ? null : temp.getInt("project_id");
        return Task.createCompleteTask(id, title, color, description,
                scheduledTo, dueDate, created, projectId);
    }

    public Task parseTaskByField(String s, String field) throws JSONException {
        JSONObject json = new JSONObject(s);
        JSONObject temp = json.getJSONObject(field);
        Integer id = temp.getInt("id");
        String title = temp.getString("title");
        String color = temp.isNull("color") ? null : temp.getString("color");
        String description = temp.isNull("description") ? null : temp.getString("description");
        Integer scheduledTo = temp.isNull("scheduled_to") ? null : temp.getInt("scheduled_to");
        Integer dueDate = temp.isNull("due_date") ? null : temp.getInt("due_date");
        Integer created = temp.getInt("created_at");
        Integer projectId = temp.isNull("project_id") ? null : temp.getInt("project_id");
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
                String description = temp.isNull("description") ? null : temp.getString("description");
                String color = temp.isNull("color") ? null : temp.getString("color");
                Integer created = temp.getInt("created_at");
                Integer taskCount = temp.has("task_count") ? temp.getInt("task_count") : 0;
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
        String description = temp.isNull("description") ? null : temp.getString("description");
        String color = temp.isNull("color") ? null : temp.getString("color");
        Integer created = temp.getInt("created_at");
        Integer taskCount = temp.has("task_count") ? temp.getInt("task_count") : 0;
        return Project.createCompleteProject(id, name, description,
                color, created, taskCount);

    }

}
