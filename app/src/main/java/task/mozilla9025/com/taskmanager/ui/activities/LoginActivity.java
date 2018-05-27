package task.mozilla9025.com.taskmanager.ui.activities;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import task.mozilla9025.com.taskmanager.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        GlobalBus.getBus().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalBus.getBus().unregister(this);
    }


}
