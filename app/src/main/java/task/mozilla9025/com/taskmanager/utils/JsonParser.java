package task.mozilla9025.com.taskmanager.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.annotations.Index;
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
                int id = temp.getInt("id");
                String title = temp.getString("title");
                String color = temp.getString("color");
                String description = temp.getString("description");
                long scheduledTo = temp.getLong("scheduled_to");
                long dueDate = temp.getLong("due_date");
                long created = temp.getLong("created_at");
                int projectId = temp.getInt("project_id");
                tasks.add(Task.createCompleteTask(id, title, color, description,
                        scheduledTo, dueDate, created, projectId));
            }
        }
        return tasks;
    }

    public List<Project> parseProjectList(String s) throws JSONException {
        List<Project> projects = new ArrayList<>();

        return projects;
    }

}
