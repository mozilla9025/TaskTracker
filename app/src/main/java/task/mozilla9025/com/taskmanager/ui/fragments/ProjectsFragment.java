package task.mozilla9025.com.taskmanager.ui.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.api.ProjectApiController;
import task.mozilla9025.com.taskmanager.models.Project;
import task.mozilla9025.com.taskmanager.preferences.PreferencesHelper;
import task.mozilla9025.com.taskmanager.realm.RealmManager;
import task.mozilla9025.com.taskmanager.ui.activities.ProjectActivity;
import task.mozilla9025.com.taskmanager.ui.activities.ProjectEditActivity;
import task.mozilla9025.com.taskmanager.ui.adapters.ProjectsAdapter;
import task.mozilla9025.com.taskmanager.utils.eventbus.BusMessage;
import task.mozilla9025.com.taskmanager.utils.eventbus.GlobalBus;

public class ProjectsFragment extends Fragment implements ProjectsAdapter.ProjectClickListener {

    @BindView(R.id.btn_add_project)
    ImageButton btnAdd;
    @BindView(R.id.et_project_name)
    EditText etProjectName;
    @BindView(R.id.rv_projects)
    RecyclerView rvProjects;

    private Realm realm;
    private ProjectsAdapter adapter;
    private RealmResults<Project> projects;
    private ProjectApiController projectApiController;
    private String accessToken;

    public ProjectsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        ButterKnife.bind(this, view);
        view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
        accessToken = new PreferencesHelper(getContext()).getAccessToken();
        projectApiController = new ProjectApiController(accessToken);
        projectApiController.getProjects();
        realm = Realm.getDefaultInstance();
        if (projects == null) {
            projects = RealmManager.getProjects(realm, false);
        }
        adapter = new ProjectsAdapter(this, projects);
        rvProjects.setAdapter(adapter);
        rvProjects.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) {
            realm.close();
            realm = null;
        }
    }

    @Override
    public void onProjectClick(int pos) {
        startActivity(new Intent(getContext(), ProjectActivity.class)
                .putExtra("project", adapter.getItem(pos)));
    }

    @Override
    public void onEditClick(int pos) {
        startActivity(new Intent(getContext(), ProjectEditActivity.class)
                .putExtra("project", adapter.getItem(pos)));
    }

    @Override
    public void onDeleteClick(int pos) {
        showAlertAndDelete(pos);
    }

    @OnClick(R.id.btn_add_project)
    void addProject() {
        if (TextUtils.isEmpty(String.valueOf(etProjectName.getText()))) {
            return;
        }
        Project project = Project.createDefaultProject(String.valueOf(etProjectName.getText()));
        projectApiController.createProject(project.getName());
        etProjectName.setText("");
    }

    @Subscribe
    public void onBusMessage(BusMessage msg) {
        int eventId = msg.getEventId();
        Log.d("EDITED", "onBusMessage: " + eventId);
        if (eventId == BusMessage.PROJECT_EDITED_ID) {
            projects = RealmManager.getProjects(realm, false);
            adapter.updateData(projects);
            Log.d("PROJECTS", "onBusMessage: "+projects);
        }
    }

    private void showAlertAndDelete(int pos) {
        AlertDialog builder = new AlertDialog.Builder(getContext()).create();
        builder.setTitle("Confirm");
        builder.setMessage("Delete project \"" + adapter.getItem(pos).getName() + "\"?");
        builder.setButton(AlertDialog.BUTTON_NEGATIVE, "No", (dialog, which) -> dialog.dismiss());
        builder.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
            Integer id = adapter.getItem(pos).getId();
            projectApiController.deleteProject(id);
            RealmManager.deleteProject(realm, id);
            adapter.updateData(projects);
        });
        builder.show();
    }
}
