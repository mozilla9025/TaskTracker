package task.mozilla9025.com.taskmanager.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.models.Profile;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.realm.RealmManager;
import task.mozilla9025.com.taskmanager.utils.DateUtils;

public class TasksInProjectAdapter extends RealmRecyclerViewAdapter<Task, TasksInProjectAdapter.TaskVH> {

    private Context context;
    private TaskClickListener clickListener;

    public TasksInProjectAdapter(Context context, RealmResults<Task> tasks) {
        super(tasks, true, true);
        this.context = context;
        this.clickListener = (TaskClickListener) context;
    }

    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskVH(LayoutInflater.from(context)
                .inflate(R.layout.item_task_in_project, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH h, int position) {
        int pos = h.getAdapterPosition();
        Task task = getItem(pos);

        h.itemView.setOnClickListener(v -> clickListener.onTaskClick(pos));
        h.btnEdit.setOnClickListener(v -> clickListener.onEditClick(pos));
        h.btnDelete.setOnClickListener(v -> clickListener.onDeleteClick(pos));

        h.tvTaskName.setText(task.getTitle());
        if (task.getDueDate() != null) {
            h.tvDeadline.setVisibility(View.VISIBLE);
            h.tvDeadline.setText("Deadline: " + DateUtils.formatDate(task.getDueDate()));
        } else {
            h.tvDeadline.setVisibility(View.GONE);
        }

        if (task.getDescription() != null) {
            h.tvDescription.setVisibility(View.VISIBLE);
            h.tvDescription.setText(task.getDescription());
        } else {
            h.tvDescription.setVisibility(View.GONE);
        }

        if (task.getScheduledTo() != null) {
            h.tvScheduled.setVisibility(View.VISIBLE);
            h.tvScheduled.setText("Scheduled to:" + DateUtils.formatDate(task.getScheduledTo()));
        } else {
            h.tvScheduled.setVisibility(View.GONE);
        }

        h.tvCreated.setText("Created: " + DateUtils.formatDate(task.getCreated()));

        if (task.getColor() != null) {
            h.viewColor.setBackgroundColor(Color.parseColor(task.getColor()));
        } else {
            h.viewColor.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        if (task.getAssigneeId() != null) {
            final Profile profile;
            try (Realm realm = Realm.getDefaultInstance()) {
                profile = RealmManager.getProfileById(realm, task.getAssigneeId());
            }
            if (profile != null) {
                h.tvAssignee.setText("Assigned to:\n" + profile.getName() + " " + profile.getSurname());
                h.tvAssignee.setVisibility(View.VISIBLE);
            } else {
                h.tvAssignee.setVisibility(View.GONE);
            }
        } else {
            h.tvAssignee.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return getData() == null ? 0 : getData().size();
    }

    class TaskVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_task_name)
        TextView tvTaskName;
        @BindView(R.id.tv_task_assignee)
        TextView tvAssignee;
        @BindView(R.id.tv_task_deadline)
        TextView tvDeadline;
        @BindView(R.id.tv_task_description)
        TextView tvDescription;
        @BindView(R.id.tv_task_scheduled_to)
        TextView tvScheduled;
        @BindView(R.id.tv_task_created)
        TextView tvCreated;
        @BindView(R.id.btn_edit_task)
        ImageButton btnEdit;
        @BindView(R.id.btn_delete_task)
        ImageButton btnDelete;
        @BindView(R.id.task_color)
        View viewColor;

        public TaskVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface TaskClickListener {
        void onTaskClick(int pos);

        void onEditClick(int pos);

        void onDeleteClick(int pos);
    }

}
