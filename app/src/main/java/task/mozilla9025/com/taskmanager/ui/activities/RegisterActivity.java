package task.mozilla9025.com.taskmanager.ui.activities;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
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

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    TextInputEditText etName;
    @BindView(R.id.til_name)
    TextInputLayout tilName;
    @BindView(R.id.et_surname)
    TextInputEditText etSurname;
    @BindView(R.id.til_surname)
    TextInputLayout tilSurname;
    @BindView(R.id.et_email)
    TextInputEditText etEmail;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.cl_register_bg)
    ConstraintLayout clBg;

    private AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

    @OnClick(R.id.btn_sign_up)
    void signUp() {
        boolean isError = false;
        tilName.setError(null);
        tilSurname.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);

        if (TextUtils.isEmpty(etName.getText())) {
            tilName.setError("Field is required");
            isError = true;
        }
        if (TextUtils.isEmpty(etName.getText())) {
            tilName.setError("Field is required");
            isError = true;
        }
        if (TextUtils.isEmpty(etName.getText())) {
            tilName.setError("Field is required");
            isError = true;
        }
        if (TextUtils.isEmpty(etName.getText())) {
            tilName.setError("Field is required");
            isError = true;
        }

        if (isError)
            return;

        String name = String.valueOf(etName.getText());
        String surname = String.valueOf(etSurname.getText());
        String email = String.valueOf(etEmail.getText());
        String password = String.valueOf(etPassword.getText());

        new UserApiController(RegisterActivity.this).register(name, surname, email, password);
    }

    @Subscribe
    public void onMessage(BusMessage msg) {
        final int eventId = msg.getEventId();

        if (eventId == BusMessage.REGISTERED_ID) {
            if (msg.isError()) {
                runOnUiThread(() -> Snackbar.make(clBg, "Registration error",
                        BaseTransientBottomBar.LENGTH_LONG).show());
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Confirm your account")
                        .setMessage("Check your e-mail to confirm registration")
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                            try {
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                                startActivity(emailIntent);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                        });
                builder.create()
                        .show();
            }
        } else if (eventId == BusMessage.ERROR_ID) {
            runOnUiThread(() -> Snackbar.make(clBg, "Registration error",
                    BaseTransientBottomBar.LENGTH_LONG).show());
        }
    }
}
