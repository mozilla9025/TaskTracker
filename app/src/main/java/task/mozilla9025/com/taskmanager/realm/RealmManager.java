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
        RealmResults<Task> tasks = realm.where(Task.class)
                .isNull("projectId")
                .sort("created", Sort.DESCENDING)
                .findAll();

        tasks.createSnapshot();
        return tasks;
    }

    public RealmResults<Project> getProjects(Realm realm) {
        RealmResults<Project> projects = realm.where(Project.class)
                .sort("created", Sort.DESCENDING)
                .findAll();

        projects.createSnapshot();
        return projects;
    }

    public void deleteTask(Realm realm, Integer taskId) {
        realm.executeTransaction(tr -> {
            tr.where(Task.class)
                    .equalTo("id", taskId)
                    .findAll()
                    .deleteAllFromRealm();
        });
    }

    public void deleteProject(Realm realm, Integer id) {
        realm.executeTransaction(tr -> {
            tr.where(Task.class)
                    .equalTo("projectId", id)
                    .findAll()
                    .createSnapshot()
                    .deleteAllFromRealm();

            tr.where(Project.class)
                    .equalTo("id", id)
                    .findAll()
                    .createSnapshot()
                    .deleteAllFromRealm();
        });
    }
}
