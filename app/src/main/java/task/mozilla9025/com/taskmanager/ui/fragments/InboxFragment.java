package task.mozilla9025.com.taskmanager.ui.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

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
import task.mozilla9025.com.taskmanager.ui.adapters.TasksAdapter;

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
    private TasksAdapter adapter;
    private RealmResults<Task> inboxTasks;
    private TaskApiController taskApiController;
    private String accessToken;

    public InboxFragment() {
    }

    public static InboxFragment getInstance() {
        if (instance == null) {
            instance = new InboxFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new PreferencesHelper(getContext()).setAccessToken("Ac2QaxlCgC6oLS7QDNVHAL33nGFvhHoZvRCuX8nIaXuCr4MJzs5j6zpFzpiwEpEG");
        accessToken = new PreferencesHelper(getContext()).getAccessToken();
        taskApiController = new TaskApiController(accessToken);
        taskApiController.getTasksInInbox(20, 0);
        if (inboxTasks == null || !inboxTasks.isLoaded()) {
            inboxTasks = new RealmManager().getInboxTasks(realm);
        }
        adapter = new TasksAdapter(this, inboxTasks);
        rvInbox.setAdapter(adapter);
        rvInbox.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    @OnClick(R.id.btn_add_task)
    void pickColor() {
        new ColorPickerDialog(this).show();
    }

    @Override
    public void onTaskClick(int pos) {

    }

    @Override
    public void onEditClick(int pos) {

    }

    @Override
    public void onDeleteClick(int pos) {

    }

    @Override
    public void onColorSelected(String hexColor) {
        Log.d("COLOR", "onColorSelected: " + hexColor);
    }

    @Override
    public void onCancel() {
        Log.d("COLOR", "onCancel: ");
    }
}
