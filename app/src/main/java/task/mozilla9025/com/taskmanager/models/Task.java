package task.mozilla9025.com.taskmanager.models;

import android.support.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class Task extends RealmObject {

    @PrimaryKey
    private Integer id;
    @Index
    private String title;
    private String color;
    @Nullable
    private String description;
    @Nullable
    private Long scheduledTo;
    @Nullable
    private Long dueDate;
    @Nullable
    private Integer projectId;
    private Long created;

    public Task() {
    }

    public static Task createTaskInInbox(String title) {
        Task t = new Task();
        t.title = title;
        return t;
    }

    public static Task createTaskInProject(String title, Integer projectId) {
        Task t = createTaskInInbox(title);
        t.projectId = projectId;
        return t;
    }

    public static Task createCompleteTask(Integer id, String title, String color,
                                          String description, Long scheduledTo,
                                          Long dueDate, Long created, Integer projectId) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public Long getScheduledTo() {
        return scheduledTo;
    }

    public void setScheduledTo(@Nullable Long scheduledTo) {
        this.scheduledTo = scheduledTo;
    }

    @Nullable
    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(@Nullable Long dueDate) {
        this.dueDate = dueDate;
    }

    @Nullable
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(@Nullable Integer projectId) {
        this.projectId = projectId;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}
