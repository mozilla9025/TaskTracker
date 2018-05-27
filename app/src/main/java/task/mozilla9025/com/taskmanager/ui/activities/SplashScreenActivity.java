package task.mozilla9025.com.taskmanager.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.otto.Subscribe;

import task.mozilla9025.com.taskmanager.R;
import task.mozilla9025.com.taskmanager.api.UserApiController;
import task.mozilla9025.com.taskmanager.preferences.PreferencesHelper;
import task.mozilla9025.com.taskmanager.utils.eventbus.BusMessage;
import task.mozilla9025.com.taskmanager.utils.eventbus.GlobalBus;

public class SplashScreenActivity extends AppCompatActivity {

    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        GlobalBus.getBus().register(this);
        preferencesHelper = new PreferencesHelper(this);
        if (getIntent() != null
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_VIEW)) {
            String hash = getIntent().getDataString().replace("https://utarel.com/account-confirm/", "");
            new UserApiController(this).confirmRegistration(hash);
        } else {
            if (preferencesHelper.getAccessToken() == null) {
                startActivity(new Intent(this, StartActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            } else {
                startActivity(new Intent(this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe
    public void onMessge(BusMessage msg) {
        int eventId = msg.getEventId();
        if (eventId == BusMessage.ACCOUNT_CONFIRMED_ID) {
            preferencesHelper.clearData();
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        } else if (eventId == BusMessage.ERROR_ID) {
            preferencesHelper.clearData();
            startActivity(new Intent(SplashScreenActivity.this, StartActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
    }
}

