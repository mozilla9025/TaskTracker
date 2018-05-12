package task.mozilla9025.com.taskmanager.ui.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.realm.RealmManager;
import task.mozilla9025.com.taskmanager.ui.adapters.TasksAdapter;

public class InboxFragment extends Fragment implements TasksAdapter.TaskClickListener {

    private static InboxFragment instance;
    private RealmResults<Task> inboxTasks;

    public InboxFragment() {
    }

    public static InboxFragment getInstance() {
        if (instance == null) {
            instance = new InboxFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(inboxTasks==null || !inboxTasks.isLoaded()){
            inboxTasks = new RealmManager().getInboxTasks();
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
