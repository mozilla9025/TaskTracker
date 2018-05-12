package task.mozilla9025.com.taskmanager.colorpicker;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
    private ColorEntry selectedEntry;

    public ColorPickAdapter(Context context, List<ColorEntry> colorEntryList) {
        this.context = context;
        this.colorEntryList = colorEntryList;
    }

    @NonNull
    @Override
    public ColorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ColorVH(LayoutInflater.from(context)
                .inflate(R.layout.item_color, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ColorVH h, int position) {
        int pos = h.getAdapterPosition();
        Log.d("COLOR", "onBindViewHolder: "+pos);
        h.colorBg.getBackground().setColorFilter(colorEntryList.get(pos).color, PorterDuff.Mode.SRC);
        h.ivColorPickStatus.setVisibility(colorEntryList.get(pos).checked ? View.VISIBLE : View.INVISIBLE);

        h.itemView.setOnClickListener(v -> toggleSelected(pos));
    }

    @Override
    public int getItemCount() {
        return colorEntryList == null ? 0 : colorEntryList.size();
    }

    public void toggleSelected(int pos) {
        for (int i = 0; i < colorEntryList.size(); i++) {
            if (i == pos)
                colorEntryList.get(i).checked = !colorEntryList.get(i).checked;
            else
                colorEntryList.get(i).checked = false;
        }
        selectedEntry = colorEntryList.get(pos).checked ? colorEntryList.get(pos) : null;
        notifyDataSetChanged();
    }

    public ColorEntry getSelectedColor() {
        return selectedEntry;
    }

    class ColorVH extends RecyclerView.ViewHolder {

        @BindView(R.id.color_bg)
        View colorBg;
        @BindView(R.id.iv_color_pick_status)
        ImageView ivColorPickStatus;

        public ColorVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
