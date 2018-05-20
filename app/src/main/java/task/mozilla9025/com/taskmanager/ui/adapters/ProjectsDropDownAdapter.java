package task.mozilla9025.com.taskmanager.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.models.Project;

public class ProjectsDropDownAdapter extends RealmBaseAdapter<Project> {

    public ProjectsDropDownAdapter(RealmResults<Project> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        Context context = parent.getContext();
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_project_dropdown, parent, false);
            h = new ViewHolder();
            h.tvName = convertView.findViewById(R.id.tv_project_name);
            h.tvTaskCount = convertView.findViewById(R.id.tv_task_count);
            h.viewColor = convertView.findViewById(R.id.project_color);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        final Project project = getItem(position);
        h.tvName.setText(project.getName());
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
        return convertView;
    }

    public int getPositionById(Integer projectId) {
        for (int i = 0; i < adapterData.size(); i++) {
            if (projectId.equals(getItem(i).getId())) {
                return i;
            }
        }
        return 0;
    }

    private class ViewHolder {
        View viewColor;
        TextView tvName;
        TextView tvTaskCount;
    }

}
