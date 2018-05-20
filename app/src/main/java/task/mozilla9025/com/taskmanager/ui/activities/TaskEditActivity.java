package task.mozilla9025.com.taskmanager.ui.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.models.Project;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.realm.RealmManager;
import task.mozilla9025.com.taskmanager.ui.adapters.ProjectsDropDownAdapter;
import task.mozilla9025.com.taskmanager.utils.DateUtils;

public class TaskEditActivity extends AppCompatActivity {

    @BindView(R.id.spinner_projects)
    Spinner spinnerProjects;
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

    private Realm realm;
    private Integer scheduledTo;
    private Integer dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        Task task = getIntent().getParcelableExtra("task");

        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        RealmResults<Project> projects = new RealmManager().getProjects(realm);
        spinnerProjects.setAdapter(new ProjectsDropDownAdapter(projects));

        etDueDate.setText(task.getDueDate() != null ? DateUtils.formatDate(task.getDueDate()) : "Not selected");
        etScheduledTo.setText(task.getScheduledTo() != null ? DateUtils.formatDate(task.getScheduledTo()) : "Not selected");

        etTitle.setText(task.getTitle());
        etDescription.setText(task.getDescription() != null ? task.getDescription() : "");
    }

    @OnClick(R.id.btn_task_edit_done)
    void saveEditedTask() {

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