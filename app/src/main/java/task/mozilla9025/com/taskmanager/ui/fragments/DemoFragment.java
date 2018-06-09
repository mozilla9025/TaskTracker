package task.mozilla9025.com.taskmanager.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.utils.DateUtils;
import task.mozilla9025.com.taskmanager.utils.eventbus.BusMessage;
import task.mozilla9025.com.taskmanager.utils.eventbus.GlobalBus;

public class DemoFragment extends Fragment {

    @BindView(R.id.tv_task_name)
    TextView tvTaskName;
    @BindView(R.id.tv_task_deadline)
    TextView tvDeadline;
    @BindView(R.id.tv_task_description)
    TextView tvDescription;
    @BindView(R.id.tv_task_scheduled_to)
    TextView tvScheduled;
    @BindView(R.id.tv_task_created)
    TextView tvCreated;
    @BindView(R.id.task_color)
    View viewColor;

    private Task task;

    public DemoFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_task_in_project, container, false);
        ButterKnife.bind(this, rootView);

        Bundle args = getArguments();
        task = args.getParcelable("task");
        if (task != null) {
            tvTaskName.setText(task.getTitle());
            if (task.getDueDate() != null) {
                tvDeadline.setVisibility(View.VISIBLE);
                tvDeadline.setText("Deadline: " + DateUtils.formatDate(task.getDueDate()));
            } else {
                tvDeadline.setVisibility(View.GONE);
            }

            if (task.getDescription() != null) {
                tvDescription.setVisibility(View.VISIBLE);
                tvDescription.setText(task.getDescription());
            } else {
                tvDescription.setVisibility(View.GONE);
            }

            if (task.getScheduledTo() != null) {
                tvScheduled.setVisibility(View.VISIBLE);
                tvScheduled.setText("Scheduled to:" + DateUtils.formatDate(task.getScheduledTo()));
            } else {
                tvScheduled.setVisibility(View.GONE);
            }

            tvCreated.setText("Created: " + DateUtils.formatDate(task.getCreated()));

            if (task.getColor() != null) {
                viewColor.setBackgroundColor(Color.parseColor(task.getColor()));
            } else {
                viewColor.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }
        return rootView;
    }

    @OnClick(R.id.btn_edit_task)
    void editTask() {
        GlobalBus.getBus().post(new BusMessage().onTaskEdit(task));
    }

    @OnClick(R.id.card_task)
    void clickTask() {
        GlobalBus.getBus().post(new BusMessage().onTaskClick(task));
    }

    @OnClick(R.id.btn_delete_task)
    void deleteTask() {
        GlobalBus.getBus().post(new BusMessage().onTaskDelete(task));
    }

}
