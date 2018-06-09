package task.mozilla9025.com.taskmanager.ui.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.api.TaskApiController;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.preferences.PreferencesHelper;
import task.mozilla9025.com.taskmanager.realm.RealmManager;
import task.mozilla9025.com.taskmanager.ui.activities.TaskEditActivity;
import task.mozilla9025.com.taskmanager.utils.eventbus.BusMessage;
import task.mozilla9025.com.taskmanager.utils.eventbus.GlobalBus;

public class WorkflowFragment extends Fragment {

    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.pager_title_strip)
    PagerTitleStrip pagerTitleStrip;

    private Realm realm;
    private RealmResults<Task> tasks;
    private CustomPagerAdapter adapter;

    public WorkflowFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_workflow, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
        realm = Realm.getDefaultInstance();
        tasks = RealmManager.getTasksWithDeadline(realm);
        adapter = new CustomPagerAdapter(getChildFragmentManager(), tasks);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe
    public void onBusMessage(BusMessage msg) {
        int eventId = msg.getEventId();
        if (eventId == BusMessage.EDIT_TASK_ID) {
            startActivity(new Intent(getContext(), TaskEditActivity.class)
                    .putExtra("task", msg.getTask()));
        } else if (eventId == BusMessage.DELETE_TASK_ID) {
            if (msg.getTask() != null) showAlertAndDelete(msg.getTask());
        } else if (eventId == BusMessage.CLICK_TASK_ID) {

        }
    }

    private void showAlertAndDelete(Task task) {
        AlertDialog builder = new AlertDialog.Builder(getContext()).create();
        builder.setTitle("Confirm");
        builder.setMessage("Delete task \"" + task.getTitle() + "\"?");
        builder.setButton(AlertDialog.BUTTON_NEGATIVE, "No", (dialog, which) -> dialog.dismiss());
        builder.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
            Integer id = task.getId();
            new TaskApiController(new PreferencesHelper(getContext()).getAccessToken())
                    .deleteTask(id);
            try (Realm realm = Realm.getDefaultInstance()) {
                RealmManager.deleteTask(realm, id);
            }

            adapter.destroyFragment();
        });
        builder.show();
    }
}
