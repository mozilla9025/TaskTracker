package task.mozilla9025.com.taskmanager.api;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.utils.JsonParser;
import task.mozilla9025.com.taskmanager.utils.eventbus.BusMessage;
import task.mozilla9025.com.taskmanager.utils.eventbus.GlobalBus;

public final class TaskApiController {

    private TasksApi tasksApi;
    private String accessToken;
    private JsonParser parser;

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
        this.parser = new JsonParser();
    }

    public void getTasksInInbox(int limit, int offset) {
        tasksApi.tasksInInbox(accessToken, limit, offset).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    GlobalBus.getBus().post(new BusMessage().error());
                    return;
                }
                String responseStr = null;
                try {
                    responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    List<Task> tasks = parser.parseTaskList(responseStr);
                    try (Realm realm = Realm.getDefaultInstance()) {
                        realm.executeTransaction(tr -> {
                            tr.insertOrUpdate(tasks);
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });
    }

    public void getTasks(Integer projectId, int limit, int offset) {
        tasksApi.tasksInProject(accessToken, projectId, limit, offset).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    GlobalBus.getBus().post(new BusMessage().error());
                    return;
                }
                String responseStr = null;
                try {
                    responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    List<Task> tasks = parser.parseTaskList(responseStr);
                    try (Realm realm = Realm.getDefaultInstance()) {
                        realm.executeTransaction(tr -> {
                            tr.insertOrUpdate(tasks);
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });
    }

    public void createTaskInInbox(String title) {
        tasksApi.createTaskInInbox(accessToken, title).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    GlobalBus.getBus().post(new BusMessage().error());
                    return;
                }
                String responseStr = null;
                try {
                    responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    Task task = parser.parseTaskByField(responseStr, "result");
                    try (Realm realm = Realm.getDefaultInstance()) {
                        realm.executeTransaction(tr -> {
                            tr.insertOrUpdate(task);
                        });
                    }
                    GlobalBus.getBus().post(new BusMessage().taskCreated());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });
    }

    public void createTaskInProject(String title, Integer projectId) {
        tasksApi.createTaskInProject(accessToken, projectId, title).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    GlobalBus.getBus().post(new BusMessage().error());
                    return;
                }
                String responseStr = null;
                try {
                    responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    Task task = parser.parseTaskByField(responseStr,"result");
                    try (Realm realm = Realm.getDefaultInstance()) {
                        realm.executeTransaction(tr -> {
                            tr.insertOrUpdate(task);
                        });
                    }
                    GlobalBus.getBus().post(new BusMessage().taskCreated());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });
    }

    public void updateTask(Task taskToUpdate) {
        Integer taskId = taskToUpdate.getId();
        String title = taskToUpdate.getTitle();
        String description = taskToUpdate.getDescription();
        Integer projectId = taskToUpdate.getProjectId();
        Integer scheduledTo = taskToUpdate.getScheduledTo();
        Integer dueDate = taskToUpdate.getDueDate();
        tasksApi.update(accessToken, taskId, title, description,
                projectId, scheduledTo, dueDate).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    GlobalBus.getBus().post(new BusMessage().error());
                    return;
                }
                String responseStr = null;
                try {
                    responseStr = response.body().string();
                    Log.i("TASK UPDATE", "onResponse: " + responseStr);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    Task task = parser.parseTask(responseStr);
                    try (Realm realm = Realm.getDefaultInstance()) {
                        realm.executeTransaction(tr -> {
                            tr.insertOrUpdate(task);
                        });
                    }
                    GlobalBus.getBus().post(new BusMessage().taskEdited());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });
    }

    public void deleteTask(Integer taskId) {
        tasksApi.deleteTask(accessToken, taskId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    GlobalBus.getBus().post(new BusMessage().error());
                    return;
                }
                String responseStr = null;
                try {
                    responseStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    if (parser.parseStatus(responseStr)) {
                        GlobalBus.getBus().post(new BusMessage().taskDeleted());
                    } else {
                        GlobalBus.getBus().post(new BusMessage().error());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.clone().enqueue(this);
            }
        });
    }

}
