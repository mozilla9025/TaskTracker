package task.mozilla9025.com.taskmanager.api;

import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import task.mozilla9025.com.taskmanager.preferences.PreferencesHelper;
import task.mozilla9025.com.taskmanager.utils.JsonParser;
import task.mozilla9025.com.taskmanager.utils.eventbus.BusMessage;
import task.mozilla9025.com.taskmanager.utils.eventbus.GlobalBus;

public final class UserApiController {

    private UserApi userApi;
    private Context context;
    private JsonParser parser;

    private static UserApi createApi() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .client(client)
                .build();

        return retrofit.create(UserApi.class);
    }

    public UserApiController(Context context) {
        this.context = context;
        this.userApi = createApi();
        this.parser = new JsonParser();
    }

    public void login(String email, String password) {
        userApi.login(email, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    GlobalBus.getBus().post(new BusMessage().error());
                    return;
                }
                String responseStr;
                try {
                    responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    if (!parser.parseStatus(responseStr)) {
                        GlobalBus.getBus().post(new BusMessage().login(false));
                    }
                    String accessToken = parser.parseAccessToken(responseStr);
                    if (accessToken == null) {
                        return;
                    }
                    new PreferencesHelper(context).setAccessToken(accessToken);
                    GlobalBus.getBus().post(new BusMessage().login(true));
                } catch (JSONException e) {
                    e.printStackTrace();
                    GlobalBus.getBus().post(new BusMessage().error());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });
    }

    public void register(String name, String surname, String email, String password) {
        userApi.register(name, surname, email, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    GlobalBus.getBus().post(new BusMessage().error());
                    return;
                }
                String responseStr;
                try {
                    responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    if (parser.parseStatus(responseStr)) {
                        GlobalBus.getBus().post(new BusMessage().registered(true));
                    } else {
                        GlobalBus.getBus().post(new BusMessage().registered(false));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    GlobalBus.getBus().post(new BusMessage().registered(false));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });
    }

    public void confirmRegistration(String hash) {
        userApi.confirmAccount(hash).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    GlobalBus.getBus().post(new BusMessage().error());
                    return;
                }
                String responseStr;
                try {
                    responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                GlobalBus.getBus().post(new BusMessage().confirmed());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });
    }

}