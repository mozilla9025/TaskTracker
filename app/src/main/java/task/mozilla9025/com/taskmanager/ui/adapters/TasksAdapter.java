package task.mozilla9025.com.taskmanager.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

public class TasksAdapter extends RealmRecyclerViewAdapter<Task, TasksAdapter.TaskVH> {

    private Context context;
    private RealmResults<Task> tasks;
    private TaskClickListener clickListener;

    public TasksAdapter(Fragment fragment, RealmResults<Task> tasks) {
        super(tasks, true, true);
        this.tasks = tasks;
        this.context = fragment.getContext();
        this.clickListener = (TaskClickListener) fragment;
    }

    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskVH(LayoutInflater.from(context)
                .inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH h, int position) {
        int pos = h.getAdapterPosition();
        Task task = tasks.get(pos);

        h.itemView.setOnClickListener(v -> clickListener.onTaskClick(pos));
        h.btnEdit.setOnClickListener(v -> clickListener.onEditClick(pos));
        h.btnDelete.setOnClickListener(v -> clickListener.onDeleteClick(pos));

        h.tvTaskName.setText(task.getTitle());

        if (task.getAssigneeId() != null) {
            try (Realm realm = Realm.getDefaultInstance()) {
                Profile p = RealmManager.getProfileById(realm, task.getAssigneeId());
                if (p != null) {
                    h.tvAssigneeName.setText("Assigned to: \n" + p.getName() + " " + p.getSurname());
                    h.tvAssigneeName.setVisibility(View.VISIBLE);
                } else {
                    h.tvAssigneeName.setVisibility(View.GONE);
                }
            }
        } else {
            h.tvAssigneeName.setVisibility(View.GONE);
        }

        if (task.getDueDate() == null) {
            h.tvDeadline.setVisibility(View.GONE);
        } else {
            h.tvDeadline.setVisibility(View.VISIBLE);
            h.tvDeadline.setText("Deadline: " + DateUtils.formatDate(task.getDueDate()));
        }

        if (task.getColor() != null)
            h.viewColor.getBackground().setColorFilter(Color.parseColor(task.getColor()), PorterDuff.Mode.SRC);
    }

    @Override
    public int getItemCount() {
        return tasks == null ? 0 : tasks.size();
    }

    class TaskVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_task_name)
        TextView tvTaskName;
        @BindView(R.id.tv_task_deadline)
        TextView tvDeadline;
        @BindView(R.id.tv_assignee_name)
        TextView tvAssigneeName;
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
