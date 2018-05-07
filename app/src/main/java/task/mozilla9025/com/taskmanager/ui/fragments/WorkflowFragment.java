package task.mozilla9025.com.taskmanager.ui.fragments;

import android.support.v4.app.Fragment;

public class WorkflowFragment extends Fragment {

    private static WorkflowFragment instance;

    public static WorkflowFragment getInstance() {
        if (instance == null) {
            instance = new WorkflowFragment();
        }
        return instance;
    }

    public WorkflowFragment() {
    }

}
