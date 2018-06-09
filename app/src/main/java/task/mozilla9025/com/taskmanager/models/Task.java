package task.mozilla9025.com.taskmanager.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class Task extends RealmObject implements Parcelable {

    @PrimaryKey
    private Integer id;
    @Index
    private String title;
    private String color;
    @Nullable
    private String description;
    @Nullable
    private Integer scheduledTo;
    @Nullable
    private Integer dueDate;
    @Nullable
    private Integer projectId;
    private Integer created;
    @Nullable
    private Integer assigneeId;

    public Task() {
    }

    public static Task create() {
        return new Task();
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
                                          String description, Integer scheduledTo,
                                          Integer dueDate, Integer created, Integer projectId) {
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
    public Integer getScheduledTo() {
        return scheduledTo;
    }

    public void setScheduledTo(@Nullable Integer scheduledTo) {
        this.scheduledTo = scheduledTo;
    }

    @Nullable
    public Integer getDueDate() {
        return dueDate;
    }

    public void setDueDate(@Nullable Integer dueDate) {
        this.dueDate = dueDate;
    }

    @Nullable
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(@Nullable Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    protected Task(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        title = in.readString();
        color = in.readString();
        description = in.readString();
        scheduledTo = in.readByte() == 0x00 ? null : in.readInt();
        dueDate = in.readByte() == 0x00 ? null : in.readInt();
        projectId = in.readByte() == 0x00 ? null : in.readInt();
        created = in.readByte() == 0x00 ? null : in.readInt();
        assigneeId = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(title);
        dest.writeString(color);
        dest.writeString(description);
        if (scheduledTo == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(scheduledTo);
        }
        if (dueDate == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(dueDate);
        }
        if (projectId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(projectId);
        }
        if (created == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(created);
        }
        if (assigneeId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(assigneeId);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}