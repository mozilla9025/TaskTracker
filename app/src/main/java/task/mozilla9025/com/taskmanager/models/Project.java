package task.mozilla9025.com.taskmanager.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Project extends RealmObject implements Parcelable {

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

    protected Project(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        description = in.readString();
        color = in.readString();
        created = in.readByte() == 0x00 ? null : in.readLong();
        taskCount = in.readByte() == 0x00 ? null : in.readInt();
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
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(color);
        if (created == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(created);
        }
        if (taskCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(taskCount);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
}