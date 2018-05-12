package task.mozilla9025.com.taskmanager.colorpicker;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import task.mozilla9025.com.taskmanager.R;

public class ColorPickerDialog {

    private Context context;
    private ColorSelectCallback selectCallback;
    private List<ColorEntry> colorEntryList = new ArrayList<>();

    public ColorPickerDialog(Context context) {
        this.context = context;
        this.selectCallback = (ColorSelectCallback) context;
        this.colorEntryList.addAll(Arrays.asList(new ColorEntry(ContextCompat.getColor(context, R.color.red)),
                new ColorEntry(ContextCompat.getColor(context, R.color.pink)),
                new ColorEntry(ContextCompat.getColor(context, R.color.purple)),
                new ColorEntry(ContextCompat.getColor(context, R.color.deepPurple)),
                new ColorEntry(ContextCompat.getColor(context, R.color.indigo)),
                new ColorEntry(ContextCompat.getColor(context, R.color.blue)),
                new ColorEntry(ContextCompat.getColor(context, R.color.lightBlue)),
                new ColorEntry(ContextCompat.getColor(context, R.color.cyan)),
                new ColorEntry(ContextCompat.getColor(context, R.color.teal)),
                new ColorEntry(ContextCompat.getColor(context, R.color.green)),
                new ColorEntry(ContextCompat.getColor(context, R.color.lightGreen)),
                new ColorEntry(ContextCompat.getColor(context, R.color.lime)),
                new ColorEntry(ContextCompat.getColor(context, R.color.yellow)),
                new ColorEntry(ContextCompat.getColor(context, R.color.amber)),
                new ColorEntry(ContextCompat.getColor(context, R.color.orange)),
                new ColorEntry(ContextCompat.getColor(context, R.color.deepOrange)),
                new ColorEntry(ContextCompat.getColor(context, R.color.brown)),
                new ColorEntry(ContextCompat.getColor(context, R.color.grey)),
                new ColorEntry(ContextCompat.getColor(context, R.color.blueGrey))));
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_color_pick, null, false);
        RecyclerView colorsRv = dialogView.findViewById(R.id.rv_colors);
        colorsRv.setAdapter(new ColorPickAdapter(context, colorEntryList));
        builder.setView(dialogView);
        builder.setTitle("Color");
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            selectCallback.onCancel();
            dialog.dismiss();
        });
    }

    private String intToHex(int colorValue) {
        return String.format("#%06X", (0xFFFFFF & colorValue));
    }

    public interface ColorSelectCallback {
        void onColorSelected(String hexColor);

        void onCancel();
    }

}
