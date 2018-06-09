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
import task.mozilla9025.com.taskmanager.models.Profile;
import task.mozilla9025.com.taskmanager.models.Project;

public class ProfileDropDownAdapter extends RealmBaseAdapter<Profile> {

    public ProfileDropDownAdapter(RealmResults<Profile> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        Context context = parent.getContext();
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_profile_dropdown, parent, false);
            h = new ViewHolder();
            h.tvName = convertView.findViewById(R.id.tv_profile_name);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        final Profile profile = getItem(position);
        h.tvName.setText(profile.getName()+ " " +profile.getSurname());

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
        TextView tvName;
    }

}
