package task.mozilla9025.com.taskmanager.ui.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.api.UserApiController;
import task.mozilla9025.com.taskmanager.utils.eventbus.BusMessage;
import task.mozilla9025.com.taskmanager.utils.eventbus.GlobalBus;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    TextInputEditText etEmail;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.cl_login_activity_bg)
    ConstraintLayout clBg;

    private AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        GlobalBus.getBus().register(this);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalBus.getBus().unregister(this);
    }

    @OnClick(R.id.btn_back)
    void back() {
        onBackPressed();
    }

    @OnClick(R.id.btn_sign_in)
    void signIn() {
        tilEmail.setError(null);
        tilPassword.setError(null);
        if (TextUtils.isEmpty(etEmail.getText())) {
            tilEmail.setError("Field is empty");
            return;
        } else if (TextUtils.isEmpty(etPassword.getText())) {
            tilPassword.setError("Field is empty");
            return;
        }

        String email = String.valueOf(etEmail.getText());
        String password = String.valueOf(etPassword.getText());

        new UserApiController(this).login(email, password);
    }

    @Subscribe
    public void onMessage(BusMessage msg) {
        int eventId = msg.getEventId();
        if (eventId == BusMessage.LOG_IN_ID) {
            if (msg.isError()) {
                runOnUiThread(() -> Snackbar.make(clBg, "Email or password is incorrect",
                        Snackbar.LENGTH_LONG).show());
            } else {
                startActivity(new Intent(LoginActivity.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        } else if (eventId == BusMessage.ERROR_ID) {
            runOnUiThread(() -> Snackbar.make(clBg, "Connection error",
                    Snackbar.LENGTH_LONG).show());
        }
    }
}
