package task.mozilla9025.com.taskmanager.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.utils.DateUtils;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_task_in_project, container, false);
        Bundle args = getArguments();
        Task task = args.getParcelable("task");
        ButterKnife.bind(this, rootView);

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
        return rootView;
    }

    @OnClick(R.id.btn_edit_task)
    void editTask(){

    }

    @OnClick(R.id.card_task)
    void clickTask(){

    }

    @OnClick(R.id.btn_delete_task)
    void deleteTask(){

    }
}
