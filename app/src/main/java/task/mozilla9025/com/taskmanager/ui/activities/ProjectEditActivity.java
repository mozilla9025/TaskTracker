package task.mozilla9025.com.taskmanager.ui.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.api.ProjectApiController;
import task.mozilla9025.com.taskmanager.colorpicker.ColorPickerDialog;
import task.mozilla9025.com.taskmanager.models.Project;
import task.mozilla9025.com.taskmanager.preferences.PreferencesHelper;

public class ProjectEditActivity extends AppCompatActivity implements ColorPickerDialog.ColorSelectCallback {

    @BindView(R.id.view_project_color)
    View viewColor;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.toolbar_project_edit)
    Toolbar toolbar;
    @BindView(R.id.btn_project_edit_back)
    ImageButton btnBack;
    @BindView(R.id.btn_project_edit_done)
    ImageButton btnSave;
    @BindView(R.id.et_description)
    EditText etDescription;

    private Project intentProject;
    private String name;
    private String description;
    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        ButterKnife.bind(this);

        intentProject = getIntent().getParcelableExtra("project");
        if (intentProject.getColor() != null) {
            try {
                int color = Color.parseColor(intentProject.getColor());
                toolbar.setBackgroundColor(color);
                viewColor.setBackgroundColor(color);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            }
        } else {
            viewColor.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        etName.setText(intentProject.getName());
        etDescription.setText(intentProject.getDescription() != null ? intentProject.getDescription() : "");
    }

    @OnClick(R.id.view_project_color)
    void selectColor() {
        new ColorPickerDialog(this).show();
    }

    @OnClick(R.id.btn_project_edit_back)
    void back() {
        onBackPressed();
    }

    @OnClick(R.id.btn_project_edit_done)
    void save() {
        etName.setError(null);
        if (TextUtils.isEmpty(etName.getText())) {
            etName.setError("Field can not be empty");
            return;
        }
        name = String.valueOf(etName.getText());
        description = String.valueOf(etDescription.getText());
        Project projectToUpdate = Project.create();
        projectToUpdate.setId(intentProject.getId());
        projectToUpdate.setName(name);
        projectToUpdate.setDescription(description);
        projectToUpdate.setColor(color);
        String accessToken = new PreferencesHelper(this).getAccessToken();
        new ProjectApiController(accessToken).update(projectToUpdate);
        finish();
    }

    @Override
    public void onColorSelected(String hexColor) {
        toolbar.setBackgroundColor(Color.parseColor(hexColor));
        viewColor.setBackgroundColor(Color.parseColor(hexColor));
        color = hexColor;
    }

    @Override
    public void onClearColor() {
        color = null;
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        viewColor.setBackgroundColor(Color.parseColor("#ffffff"));
    }
}
