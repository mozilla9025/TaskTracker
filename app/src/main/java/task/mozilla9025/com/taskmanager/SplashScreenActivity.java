package task.mozilla9025.com.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import task.mozilla9025.com.taskmanager.preferences.PreferencesHelper;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        PreferencesHelper preferencesHelper = new PreferencesHelper(this);
        preferencesHelper.setAccessToken("pkvpdfkv");
        if (preferencesHelper.getAccessToken() == null) {
            startActivity(new Intent(this, StartActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
