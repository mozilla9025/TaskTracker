package task.mozilla9025.com.taskmanager.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.utils.DateUtils;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    private RealmResults<Task> tasks;

    public CustomPagerAdapter(FragmentManager fm, RealmResults<Task> tasks) {
        super(fm);
        this.tasks = tasks;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new DemoFragment();

        Bundle args = new Bundle();
        args.putParcelable("task", tasks.get(position));

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return DateUtils.formatDate(tasks.get(position).getDueDate());
    }

}
