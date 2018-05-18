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

    protected Task(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        title = in.readString();
        color = in.readString();
        description = in.readString();
        scheduledTo = in.readByte() == 0x00 ? null : in.readLong();
        dueDate = in.readByte() == 0x00 ? null : in.readLong();
        projectId = in.readByte() == 0x00 ? null : in.readInt();
        created = in.readByte() == 0x00 ? null : in.readLong();
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
            dest.writeLong(scheduledTo);
        }
        if (dueDate == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(dueDate);
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
            dest.writeLong(created);
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