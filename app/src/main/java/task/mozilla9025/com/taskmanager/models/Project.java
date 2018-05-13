package task.mozilla9025.com.taskmanager.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Project extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String description;
    private String color;
    private long created;
    private int taskCount;

    public Project() {
    }

    public static Project createDefaultProject(String name) {
        Project p = new Project();
        p.name = name;
        return p;
    }

    public static Project createCompleteProject(int id, String name, String description,
                                            String color, long created, int taskCount) {
        Project p = new Project();
        p.id = id;
        p.name = name;
        p.description = description;
        p.color = color;
        p.created = created;
        p.taskCount = taskCount;
        return p;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
}
