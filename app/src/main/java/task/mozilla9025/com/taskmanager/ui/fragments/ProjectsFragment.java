package task.mozilla9025.com.taskmanager.ui.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.api.ProjectApiController;
import task.mozilla9025.com.taskmanager.api.TaskApiController;
import task.mozilla9025.com.taskmanager.models.Project;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.preferences.PreferencesHelper;
import task.mozilla9025.com.taskmanager.realm.RealmManager;
import task.mozilla9025.com.taskmanager.ui.adapters.ProjectsAdapter;
import task.mozilla9025.com.taskmanager.ui.adapters.TasksAdapter;

public class ProjectsFragment extends Fragment implements ProjectsAdapter.ProjectClickListener {
    private static ProjectsFragment instance;

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
        new PreferencesHelper(getContext()).setAccessToken("Ac2QaxlCgC6oLS7QDNVHAL33nGFvhHoZvRCuX8nIaXuCr4MJzs5j6zpFzpiwEpEG");
        accessToken = new PreferencesHelper(getContext()).getAccessToken();
        projectApiController = new ProjectApiController(accessToken);
        projectApiController.getProjects();
        realm = Realm.getDefaultInstance();
        if (projects == null) {
            projects = new RealmManager().getProjects(realm);
        }
        adapter = new ProjectsAdapter(this, projects);
        rvProjects.setAdapter(adapter);
        rvProjects.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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
    public void onTaskClick(int pos) {

    }

    @Override
    public void onEditClick(int pos) {

    }

    @Override
    public void onDeleteClick(int pos) {

    }
}
