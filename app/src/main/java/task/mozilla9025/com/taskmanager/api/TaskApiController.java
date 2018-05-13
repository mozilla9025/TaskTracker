package task.mozilla9025.com.taskmanager.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class TaskApiController {

    private TasksApi tasksApi;
    private String accessToken;

    private static TasksApi createApi() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .client(client)
                .build();

        return retrofit.create(TasksApi.class);
    }

    public TaskApiController(String accessToken) {
        this.accessToken = accessToken;
        this.tasksApi = createApi();
    }

    public void getTasksInInbox(int limit, int offset) {
        tasksApi.tasksInInbox(accessToken, limit, offset).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    return;
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
