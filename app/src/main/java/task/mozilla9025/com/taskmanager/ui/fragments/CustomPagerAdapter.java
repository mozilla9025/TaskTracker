package task.mozilla9025.com.taskmanager.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import io.realm.RealmResults;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.utils.DateUtils;

public class CustomPagerAdapter extends FragmentStatePagerAdapter {

    private RealmResults<Task> tasks;
    private List<Fragment> fragments;
    private int position = 0;

    public CustomPagerAdapter(FragmentManager fm, RealmResults<Task> tasks) {
        super(fm);
        this.tasks = tasks;
        this.fragments = new LinkedList<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new DemoFragment();
        this.position = position;
        Bundle args = new Bundle();
        args.putParcelable("task", tasks.get(position));
        fragment.setArguments(args);
        fragments.add(fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position >= getCount()) {
            FragmentManager manager = ((Fragment) object).getChildFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove((Fragment) object);
            trans.commit();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return DateUtils.formatDate(tasks.get(position).getDueDate());
    }

    public void destroyFragment() {
        if (this.position >= getCount()) {
            FragmentManager manager = fragments.get(this.position).getChildFragmentManager();
            FragmentTransaction trans = manager.beginTransaction();
            trans.remove(fragments.get(this.position));
            trans.commit();
            fragments.remove(this.position);
            notifyDataSetChanged();
        }
    }

}