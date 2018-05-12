package task.mozilla9025.com.taskmanager.colorpicker;

public class ColorEntry {

    private int color;
    private boolean checked;

    public ColorEntry(int color) {
        this.color = color;
        this.checked = false;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
