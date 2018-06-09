package task.mozilla9025.com.taskmanager.ui.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.models.Profile;
import task.mozilla9025.com.taskmanager.realm.RealmManager;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.cl_start_activity_bg)
    ConstraintLayout clBg;

    private AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        anim = (AnimationDrawable) clBg.getBackground();
        anim.setEnterFadeDuration(4000);
        anim.setExitFadeDuration(4000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @OnClick(R.id.btn_sign_in)
    void startSignIn() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @OnClick(R.id.btn_sign_up)
    void startSignUp() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
