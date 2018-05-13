package task.mozilla9025.com.taskmanager.realm;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
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

}
