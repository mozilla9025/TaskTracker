package task.mozilla9025.com.taskmanager.app;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    public static volatile Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        Realm.init(applicationContext);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("task_manager.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .compactOnLaunch()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
