package task.mozilla9025.com.taskmanager.ui.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.ui.fragments.InboxFragment;
import task.mozilla9025.com.taskmanager.ui.fragments.ProjectsFragment;
import task.mozilla9025.com.taskmanager.ui.fragments.WorkflowFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.fragment_root)
    FrameLayout fragmentRootView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_main_title)
    TextView tvTitle;

    private LinearLayout llNavHeader;
    private String currentFragment;
    private InboxFragment inboxFragment;
    private WorkflowFragment workflowFragment;
    private ProjectsFragment projectsFragment;
    private AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (getIntent().getAction().equals(Intent.ACTION_VIEW)) {
            Log.d("INTENT", "onCreate: " + getIntent());
            Log.d("INTENT", "data: " + getIntent().getData());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navigationView.setNavigationItemSelectedListener(this);

        llNavHeader = headerLayout.findViewById(R.id.ll_nav_header);

        anim = (AnimationDrawable) llNavHeader.getBackground();
        anim.setEnterFadeDuration(6000);
        anim.setExitFadeDuration(6000);


        if (inboxFragment == null) {
            inboxFragment = new InboxFragment();
        }
        commitFragmentTransaction(inboxFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inbox) {
            commitFragmentTransaction(new InboxFragment());
        } else if (id == R.id.nav_workflow) {
            commitFragmentTransaction(new WorkflowFragment());
        } else if (id == R.id.nav_projects) {
            commitFragmentTransaction(new ProjectsFragment());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void commitFragmentTransaction(Fragment fragment) {
        if (fragment == null || fragmentRootView == null) {
            return;
        }
        String title = getString(R.string.app_name);
        if (fragment instanceof InboxFragment) {
            title = "Inbox";
        } else if (fragment instanceof ProjectsFragment) {
            title = "Projects";
        } else if (fragment instanceof WorkflowFragment) {
            title = "Workflow";
        }
        tvTitle.setText(title);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(fragmentRootView.getId(), fragment);
        ft.commit();
    }

}
