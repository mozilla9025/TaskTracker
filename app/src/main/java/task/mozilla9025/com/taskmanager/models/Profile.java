package task.mozilla9025.com.taskmanager.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Profile extends RealmObject {

    @PrimaryKey
    private Integer id;
    private String name;
    private String surname;

    public Profile() {
    }

    public Profile(Integer id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public static Profile createEmptyAssignee() {
        Profile p = new Profile();
        p.id = null;
        p.name = "Not";
        p.surname = "selected";
        return p;
    }
}