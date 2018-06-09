package task.mozilla9025.com.taskmanager.realm;

import java.util.List;

import io.realm.OrderedRealmCollectionSnapshot;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import task.mozilla9025.com.taskmanager.models.Profile;
import task.mozilla9025.com.taskmanager.models.Project;
import task.mozilla9025.com.taskmanager.models.Task;

public class RealmManager {

    public static RealmResults<Task> getInboxTasks(Realm realm) {
        RealmResults<Task> tasks = realm.where(Task.class)
                .isNull("projectId")
                .sort("created", Sort.DESCENDING)
                .findAll();

        return tasks;
    }

    public static RealmResults<Project> getProjects(Realm realm, boolean showInbox) {
        RealmResults<Project> projects;
        if (showInbox) {
            projects = realm.where(Project.class)
                    .sort("created", Sort.ASCENDING)
                    .findAll();
        } else {
            projects = realm.where(Project.class)
                    .isNotNull("id")
                    .sort("created", Sort.DESCENDING)
                    .findAll();
        }
        return projects;
    }

    public static void deleteTask(Realm realm, Integer taskId) {
        realm.executeTransaction(tr -> {
            tr.where(Task.class)
                    .equalTo("id", taskId)
                    .findAll()
                    .createSnapshot()
                    .deleteAllFromRealm();
        });
    }

    public static void deleteProject(Realm realm, Integer id) {
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

    public static RealmResults<Task> getProjectTasks(Realm realm, Integer projectId) {
        return realm.where(Task.class)
                .equalTo("projectId", projectId)
                .sort("dueDate", Sort.ASCENDING)
                .findAll();
    }

    public static RealmResults<Task> getTasksWithDeadline(Realm realm) {
        return realm.where(Task.class)
                .isNotNull("dueDate")
                .sort("dueDate", Sort.ASCENDING)
                .findAll();
    }

    public static Profile getProfileById(Realm realm, Integer id) {
        return realm.where(Profile.class)
                .equalTo("id", id)
                .findFirst();
    }

    public static void clearData(Realm realm) {
        realm.executeTransaction(tr -> {
            tr.deleteAll();
        });
    }

    public static RealmResults<Profile> getProfiles(Realm realm, boolean showEmptyAssignee) {
        RealmResults<Profile> profiles;
        if (showEmptyAssignee) {
            profiles = realm.where(Profile.class)
                    .sort("name", Sort.ASCENDING)
                    .findAll();
        } else {
            profiles = realm.where(Profile.class)
                    .isNotNull("id")
                    .sort("name", Sort.DESCENDING)
                    .findAll();
        }

        return profiles;
    }

    public static void storeTasks(Realm realm, List<Task> tasks) {
        if (tasks == null) {
            return;
        }
        if (tasks.isEmpty()) {
            realm.where(Task.class)
                    .findAll()
                    .createSnapshot()
                    .deleteAllFromRealm();
            return;
        }

        OrderedRealmCollectionSnapshot<Task> tasksSnapshot = realm.where(Task.class)
                .findAll()
                .createSnapshot();

        for (Task taskSnapshot : tasksSnapshot) {
            for (Task task : tasks) {
                if (task.getId().equals(taskSnapshot.getId())) {
                    task.setAssigneeId(taskSnapshot.getAssigneeId());
                }
            }
        }

        realm.executeTransaction(r -> {
            r.insertOrUpdate(tasks);
        });
    }

    public static void storeTask(Realm realm, Task task) {
        if (task == null) {
            return;
        }

        if (task.getAssigneeId() == null) {
            OrderedRealmCollectionSnapshot<Task> tasksSnapshot = realm.where(Task.class)
                    .findAll()
                    .createSnapshot();

            for (Task taskSnapshot : tasksSnapshot) {
                if (task.getId().equals(taskSnapshot.getId())) {
                    task.setAssigneeId(taskSnapshot.getAssigneeId());
                }
            }
        }

        realm.executeTransaction(r -> r.insertOrUpdate(task));
    }
}
