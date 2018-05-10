package task.mozilla9025.com.taskmanager.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Priority extends RealmObject {
    @PrimaryKey
    private int id;

    public Priority() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
