package task.mozilla9025.com.taskmanager.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.models.Task;

public class TasksAdapter extends RealmRecyclerViewAdapter<Task, TasksAdapter.TaskVH> {

    private Context context;
    private RealmResults<Task> tasks;
    private TaskClickListener clickListener;

    public TasksAdapter(Context context, RealmResults<Task> tasks) {
        super(tasks, true);
        this.tasks = tasks;
        this.context = context;
        this.clickListener = (TaskClickListener) context;
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
        h.itemView.setOnClickListener(v -> clickListener.onTaskClick(pos));
        h.btnEdit.setOnClickListener(v -> clickListener.onEditClick(pos));
        h.btnDelete.setOnClickListener(v -> clickListener.onDeleteClick(pos));

        h.tvTaskName.setText(tasks.get(pos).getName());
    }

    @Override
    public int getItemCount() {
        return tasks == null ? 0 : tasks.size();
    }

    class TaskVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_task_name)
        TextView tvTaskName;
        @BindView(R.id.btn_edit_task)
        ImageButton btnEdit;
        @BindView(R.id.btn_delete_task)
        ImageButton btnDelete;

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
