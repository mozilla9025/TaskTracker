package task.mozilla9025.com.taskmanager.models;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class Task extends RealmObject {

    @PrimaryKey
    private int id;
    @Index
    private String title;
    private String color;
    private String description;
    private long scheduledTo;
    private long dueDate;
    private int projectId;
    private long created;

    public Task() {
    }

    public static Task createTaskInInbox(String title) {
        Task t = new Task();
        t.title = title;
        return t;
    }

    public static Task createTaskInProject(String title, int projectId) {
        Task t = createTaskInInbox(title);
        t.projectId = projectId;
        return t;
    }

    public static Task createCompleteTask(int id, String title, String color,
                                          String description, long scheduledTo,
                                          long dueDate, long created, int projectId) {
        Task t = new Task();
        t.id = id;
        t.title = title;
        t.color = color;
        t.description = description;
        t.scheduledTo = scheduledTo;
        t.dueDate = dueDate;
        t.created = created;
        t.projectId = projectId;
        return t;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getScheduledTo() {
        return scheduledTo;
    }

    public void setScheduledTo(long scheduledTo) {
        this.scheduledTo = scheduledTo;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
