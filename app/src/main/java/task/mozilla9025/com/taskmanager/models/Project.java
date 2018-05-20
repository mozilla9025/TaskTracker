package task.mozilla9025.com.taskmanager.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Project extends RealmObject {

    @PrimaryKey
    private Integer id;
    private String name;
    private String description;
    private String color;
    private Long created;
    private Integer taskCount;

    public Project() {
    }

    public static Project createInbox() {
        Project p = new Project();
        p.name = "Inbox";
        p.id = null;
        p.created = System.currentTimeMillis() / 1000;
        return p;
    }

    public static Project createDefaultProject(String name) {
        Project p = new Project();
        p.name = name;
        return p;
    }

    public static Project createCompleteProject(Integer id, String name, String description,
                                                String color, Long created, Integer taskCount) {
        Project p = new Project();
        p.id = id;
        p.name = name;
        p.description = description;
        p.color = color;
        p.created = created;
        p.taskCount = taskCount;
        return p;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }
}
