package task.mozilla9025.com.taskmanager.colorpicker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import task.mozilla9025.com.taskmanager.R;

public class ColorPickAdapter extends RecyclerView.Adapter<ColorPickAdapter.ColorVH> {

    private List<ColorEntry> colorEntryList;
    private Context context;

    public ColorPickAdapter(Context context, List<ColorEntry> colorEntryList) {
        this.context = context;
        this.colorEntryList = colorEntryList;
    }

    @NonNull
    @Override
    public ColorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ColorVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ColorVH extends RecyclerView.ViewHolder {

        @BindView(R.id.color_bg)
        View colorBg;
        @BindView(R.id.iv_color_pick_status)
        ImageView ivColorPickStatus;

        public ColorVH(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
