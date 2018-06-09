package task.mozilla9025.com.taskmanager.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.api.TaskApiController;
import task.mozilla9025.com.taskmanager.models.Project;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.preferences.PreferencesHelper;
import task.mozilla9025.com.taskmanager.realm.RealmManager;
import task.mozilla9025.com.taskmanager.ui.adapters.TasksInProjectAdapter;

public class ProjectActivity extends AppCompatActivity implements TasksInProjectAdapter.TaskClickListener {

    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.toolbar_project)
    Toolbar toolbar;
    @BindView(R.id.rv_tasks)
    RecyclerView rvTasks;
    @BindView(R.id.et_task_name)
    EditText etTaskName;

    private Project project;
    private Realm realm;
    private TasksInProjectAdapter adapter;
    private TaskApiController taskApiController;
    private RealmResults<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        project = getIntent().getParcelableExtra("project");
        tvProjectName.setText(project.getName());
        String accessToken = new PreferencesHelper(this).getAccessToken();
        taskApiController = new TaskApiController(accessToken);
        if (project.getColor() != null) {
            try {
                int color = Color.parseColor(project.getColor());
                toolbar.setBackgroundColor(color);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            }
        }
        tasks = RealmManager.getProjectTasks(realm, project.getId());
        adapter = new TasksInProjectAdapter(this, tasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvTasks.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    @OnClick(R.id.btn_project_back)
    void back() {
        onBackPressed();
    }

    @OnClick(R.id.btn_add_task)
    void addTask() {
        if (TextUtils.isEmpty(String.valueOf(etTaskName.getText()))) {
            return;
        }

        Task task = Task.createTaskInProject(String.valueOf(etTaskName.getText()), project.getId());
        taskApiController.createTaskInProject(task.getTitle(), project.getId());
        etTaskName.setText("");
    }


    @Override
    public void onTaskClick(int pos) {

    }

    @Override
    public void onEditClick(int pos) {
        startActivity(new Intent(this, TaskEditActivity.class)
                .putExtra("task", adapter.getItem(pos)));
    }

    @Override
    public void onDeleteClick(int pos) {
        showAlertAndDelete(pos);
    }

    private void showAlertAndDelete(int pos) {
        AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.setTitle("Confirm");
        builder.setMessage("Delete task \"" + adapter.getItem(pos).getTitle() + "\"?");
        builder.setButton(AlertDialog.BUTTON_NEGATIVE, "No", (dialog, which) -> dialog.dismiss());
        builder.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
            Integer id = adapter.getItem(pos).getId();
            taskApiController.deleteTask(id);
            RealmManager.deleteTask(realm, id);
            tasks = RealmManager.getProjectTasks(realm, project.getId());
            adapter.updateData(tasks);
        });
        builder.show();
    }
}
