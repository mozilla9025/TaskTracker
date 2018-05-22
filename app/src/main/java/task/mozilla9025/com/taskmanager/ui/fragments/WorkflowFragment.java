package task.mozilla9025.com.taskmanager.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.realm.RealmManager;
import task.mozilla9025.com.taskmanager.utils.eventbus.GlobalBus;

public class WorkflowFragment extends Fragment {

    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.pager_title_strip)
    PagerTitleStrip pagerTitleStrip;

    private Realm realm;
    private RealmResults<Task> tasks;
    private CustomPagerAdapter adapter;

    public WorkflowFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_workflow, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
        realm = Realm.getDefaultInstance();

        tasks = new RealmManager().getTasksWithDeadline(realm);
        adapter = new CustomPagerAdapter(getChildFragmentManager(), tasks);
        viewPager.setAdapter(adapter);
    }
}
