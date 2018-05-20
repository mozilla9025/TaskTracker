package task.mozilla9025.com.taskmanager.ui.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.api.TaskApiController;
import task.mozilla9025.com.taskmanager.colorpicker.ColorPickerDialog;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.preferences.PreferencesHelper;
import task.mozilla9025.com.taskmanager.realm.RealmManager;
import task.mozilla9025.com.taskmanager.ui.activities.TaskEditActivity;
import task.mozilla9025.com.taskmanager.ui.adapters.TasksAdapter;
import task.mozilla9025.com.taskmanager.utils.eventbus.BusMessage;

public class InboxFragment extends Fragment implements TasksAdapter.TaskClickListener,
        ColorPickerDialog.ColorSelectCallback {

    private static InboxFragment instance;

    @BindView(R.id.btn_add_task)
    ImageButton btnAdd;
    @BindView(R.id.et_task_name)
    EditText etTaskName;
    @BindView(R.id.rv_inbox)
    RecyclerView rvInbox;

    private Realm realm;
    private RealmManager realmManager;
    private TasksAdapter adapter;
    private RealmResults<Task> inboxTasks;
    private TaskApiController taskApiController;
    private String accessToken;

    public InboxFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        ButterKnife.bind(this, view);
        view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new PreferencesHelper(getContext()).setAccessToken("Ac2QaxlCgC6oLS7QDNVHAL33nGFvhHoZvRCuX8nIaXuCr4MJzs5j6zpFzpiwEpEG");
        accessToken = new PreferencesHelper(getContext()).getAccessToken();
        taskApiController = new TaskApiController(accessToken);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager();
        taskApiController.getTasksInInbox(20, 0);
        if (inboxTasks == null) {
            inboxTasks = realmManager.getInboxTasks(realm);
        }
        adapter = new TasksAdapter(this, inboxTasks);
        rvInbox.setAdapter(adapter);
        rvInbox.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) {
            realm.close();
            realm = null;
        }
    }

    @OnClick(R.id.btn_add_task)
    void pickColor() {
        if (TextUtils.isEmpty(String.valueOf(etTaskName.getText()))) {
            return;
        }
        Task task = Task.createTaskInInbox(String.valueOf(etTaskName.getText()));
        taskApiController.createTaskInInbox(task.getTitle());
        etTaskName.setText("");
    }

    @Override
    public void onTaskClick(int pos) {

    }

    @Override
    public void onEditClick(int pos) {
        startActivity(new Intent(getContext(), TaskEditActivity.class)
                .putExtra("task", inboxTasks.get(pos)));
    }

    @Override
    public void onDeleteClick(int pos) {
        showAlertAndDelete(pos);
    }

    @Override
    public void onColorSelected(String hexColor) {
        Log.d("COLOR", "onColorSelected: " + hexColor);
    }

    @Override
    public void onCancel() {
        Log.d("COLOR", "onCancel: ");
    }

    @Subscribe
    public void onBusMessage(BusMessage msg) {
        int eventId = msg.getEventId();
        if (eventId == BusMessage.CREATE_TASK_ID) {
            inboxTasks = realmManager.getInboxTasks(realm);
        }
    }

    private void showAlertAndDelete(int pos) {
        AlertDialog builder = new AlertDialog.Builder(getContext()).create();
        builder.setTitle("Confirm");
        builder.setMessage("Delete task \"" + adapter.getItem(pos).getTitle() + "\"?");
        builder.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer id = adapter.getItem(pos).getId();
                taskApiController.deleteTask(id);
                realmManager.deleteTask(realm, id);
                inboxTasks = realmManager.getInboxTasks(realm);
            }
        });
        builder.show();
    }

}
