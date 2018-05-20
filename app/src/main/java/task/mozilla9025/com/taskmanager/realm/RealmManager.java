package task.mozilla9025.com.taskmanager.realm;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import task.mozilla9025.com.taskmanager.models.Project;
import task.mozilla9025.com.taskmanager.models.Task;

public class RealmManager {

    public RealmManager() {
    }

    public RealmResults<Task> getInboxTasks(Realm realm) {
        return realm.where(Task.class)
                .isNull("projectId")
                .sort("created", Sort.ASCENDING)
                .findAll();
    }

    public RealmResults<Project> getProjects(Realm realm) {
        return realm.where(Project.class)
                .sort("created", Sort.ASCENDING)
                .findAll();
    }

    public void deleteTask(Realm realm, Integer taskId) {
        realm.executeTransaction(tr -> {
            tr.where(Task.class)
                    .equalTo("id", taskId)
                    .findAll()
                    .createSnapshot()
                    .deleteAllFromRealm();
        });
    }

    public void storeTask(Realm realm, Task task) {
        realm.executeTransaction(tr -> {
            tr.insertOrUpdate(task);
        });
    }
}
