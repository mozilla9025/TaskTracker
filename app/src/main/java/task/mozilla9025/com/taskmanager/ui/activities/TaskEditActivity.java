package task.mozilla9025.com.taskmanager.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.models.Project;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.realm.RealmManager;
import task.mozilla9025.com.taskmanager.ui.adapters.ProjectsDropDownAdapter;

public class TaskEditActivity extends AppCompatActivity {

    @BindView(R.id.spinner_projects)
    Spinner spinnerProjects;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        Task task = getIntent().getParcelableExtra("task");

        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        RealmResults<Project> projects = new RealmManager().getProjects(realm);
        spinnerProjects.setAdapter(new ProjectsDropDownAdapter(projects));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}