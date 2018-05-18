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
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.models.Project;

public class ProjectsAdapter extends RealmRecyclerViewAdapter<Project, ProjectsAdapter.TaskVH> {

    private Context context;
    private RealmResults<Project> projects;
    private ProjectClickListener clickListener;

    public ProjectsAdapter(Fragment fragment, RealmResults<Project> projects) {
        super(projects, true, true);
        this.projects = projects;
        this.context = fragment.getContext();
        this.clickListener = (ProjectClickListener) fragment;
    }

    @NonNull
    @Override
    public TaskVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskVH(LayoutInflater.from(context)
                .inflate(R.layout.item_project, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVH h, int position) {
        int pos = h.getAdapterPosition();
        Project project = projects.get(pos);

        h.itemView.setOnClickListener(v -> clickListener.onTaskClick(pos));
        h.btnEdit.setOnClickListener(v -> clickListener.onEditClick(pos));
        h.btnDelete.setOnClickListener(v -> clickListener.onDeleteClick(pos));

        h.tvProjectName.setText(project.getName());
        if (project.getTaskCount() == null) {
            h.tvTaskCount.setVisibility(View.GONE);
        } else {
            h.tvTaskCount.setVisibility(View.VISIBLE);
            h.tvTaskCount.setText("Tasks: " + project.getTaskCount());
        }
        if (project.getColor() != null) {
            try {
                h.viewColor.getBackground().setColorFilter(Color.parseColor(project.getColor()), PorterDuff.Mode.SRC);
            } catch (IllegalArgumentException e) {
                h.viewColor.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.grey), PorterDuff.Mode.SRC);
            }
        } else {
            h.viewColor.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.grey), PorterDuff.Mode.SRC);
        }
    }

    @Override
    public int getItemCount() {
        return projects == null ? 0 : projects.size();
    }

    class TaskVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_project_name)
        TextView tvProjectName;
        @BindView(R.id.tv_task_count)
        TextView tvTaskCount;
        @BindView(R.id.btn_edit_project)
        ImageButton btnEdit;
        @BindView(R.id.btn_delete_project)
        ImageButton btnDelete;
        @BindView(R.id.project_color)
        View viewColor;

        public TaskVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ProjectClickListener {
        void onTaskClick(int pos);

        void onEditClick(int pos);

        void onDeleteClick(int pos);
    }

}
