package task.mozilla9025.com.taskmanager.ui.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.api.TaskApiController;
import task.mozilla9025.com.taskmanager.models.Profile;
import task.mozilla9025.com.taskmanager.models.Project;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.preferences.PreferencesHelper;
import task.mozilla9025.com.taskmanager.realm.RealmManager;
import task.mozilla9025.com.taskmanager.ui.adapters.ProfileDropDownAdapter;
import task.mozilla9025.com.taskmanager.ui.adapters.ProjectsDropDownAdapter;
import task.mozilla9025.com.taskmanager.utils.DateUtils;

public class TaskEditActivity extends AppCompatActivity {

    @BindView(R.id.spinner_projects)
    Spinner spinnerProjects;
    @BindView(R.id.spinner_profiles)
    Spinner spinnerProfiles;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.et_scheduled_to)
    EditText etScheduledTo;
    @BindView(R.id.et_due_date)
    EditText etDueDate;
    @BindView(R.id.btn_scheduled_to)
    ImageButton btnScheduledTo;
    @BindView(R.id.btn_due_date)
    ImageButton btnDueDate;
    @BindView(R.id.btn_task_edit_back)
    ImageButton btnBack;
    @BindView(R.id.btn_task_edit_done)
    ImageButton btnDone;
    @BindView(R.id.toolbar_task_edit)
    Toolbar toolbar;

    private Task intentTask;
    private Realm realm;
    private String title;
    private String description;
    private Integer scheduledTo;
    private Integer dueDate;
    private Integer projectId;
    private Integer assigneeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        intentTask = getIntent().getParcelableExtra("task");

        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        realm.executeTransaction(tr -> {
            tr.insertOrUpdate(Project.createInbox());
            tr.insertOrUpdate(Profile.createEmptyAssignee());
        });

        if (intentTask.getColor() != null) {
            try {
                int color = Color.parseColor(intentTask.getColor());
                toolbar.setBackgroundColor(color);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            }
        }

        RealmResults<Project> projects = RealmManager.getProjects(realm, true);
        ProjectsDropDownAdapter adapter = new ProjectsDropDownAdapter(projects);
        spinnerProjects.setAdapter(adapter);
        spinnerProjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectId = adapter.getItem(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        RealmResults<Profile> profiles = RealmManager.getProfiles(realm, true);
        ProfileDropDownAdapter profileAdapter = new ProfileDropDownAdapter(profiles);
        spinnerProfiles.setAdapter(profileAdapter);
        spinnerProfiles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                assigneeId = profileAdapter.getItem(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etDueDate.setText(intentTask.getDueDate() != null ? DateUtils.formatDate(intentTask.getDueDate()) : "Not selected");
        etScheduledTo.setText(intentTask.getScheduledTo() != null ? DateUtils.formatDate(intentTask.getScheduledTo()) : "Not selected");

        etTitle.setText(intentTask.getTitle());
        etDescription.setText(intentTask.getDescription() != null ? intentTask.getDescription() : "");
        if (intentTask.getProjectId() != null) {
            int selection = adapter.getPositionById(intentTask.getProjectId());
            spinnerProjects.setSelection(selection);
        }
        if (intentTask.getAssigneeId() != null) {
            int selection = profileAdapter.getPositionById(intentTask.getAssigneeId());
            spinnerProfiles.setSelection(selection);
        }


    }

    @OnClick(R.id.btn_task_edit_done)
    void saveEditedTask() {
        if (TextUtils.isEmpty(etTitle.getText())) {
            etTitle.setError("Field can not be empty");
            return;
        }
        title = String.valueOf(etTitle.getText());
        description = String.valueOf(etDescription.getText());

        String accessToken = new PreferencesHelper(this).getAccessToken();
        Task taskToUpdate = Task.create();
        taskToUpdate.setId(intentTask.getId());
        taskToUpdate.setTitle(title);
        taskToUpdate.setDescription(description);
        taskToUpdate.setProjectId(projectId);
        taskToUpdate.setDueDate(dueDate);
        taskToUpdate.setScheduledTo(scheduledTo);
        taskToUpdate.setAssigneeId(assigneeId);
        RealmManager.storeTask(realm,taskToUpdate);
        new TaskApiController(accessToken).updateTask(taskToUpdate);
        finish();
    }

    @OnClick(R.id.btn_task_edit_back)
    void back() {
        onBackPressed();
    }

    @OnClick(R.id.btn_scheduled_to)
    void setScheduledTo() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, y, m, d) -> {
                    Calendar calendar = new GregorianCalendar(y, m, d);
                    scheduledTo = (int) TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis());
                    etScheduledTo.setText(DateUtils.formatDate(scheduledTo));
                }, year, month, day);
        datePickerDialog.show();
    }

    @OnClick(R.id.btn_due_date)
    void setDueDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, y, m, d) -> {
                    Calendar calendar = new GregorianCalendar(y, m, d);
                    dueDate = (int) TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis());
                    etDueDate.setText(DateUtils.formatDate(dueDate));
                }, year, month, day);
        datePickerDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}