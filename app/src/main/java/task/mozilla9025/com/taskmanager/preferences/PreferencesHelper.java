package task.mozilla9025.com.taskmanager.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {

    private static final String TOKEN = "access_token";
    private SharedPreferences sp;

    public PreferencesHelper(Context context) {
        sp = context.getSharedPreferences("task.mozilla9025.com.preferences.PreferencesHelper",Context.MODE_PRIVATE);
    }

    public String getAccessToken() {
        return sp.getString(TOKEN, null);
    }

    public void setAccessToken(String accessToken) {
        sp.edit().putString(TOKEN, accessToken)
                .apply();
    }

}
