package task.mozilla9025.com.taskmanager.api;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import task.mozilla9025.com.taskmanager.models.Project;
import task.mozilla9025.com.taskmanager.models.Task;
import task.mozilla9025.com.taskmanager.utils.JsonParser;
import task.mozilla9025.com.taskmanager.utils.eventbus.BusMessage;
import task.mozilla9025.com.taskmanager.utils.eventbus.GlobalBus;

public class ProjectApiController {

    private ProjectApi projectApi;
    private String accessToken;
    private JsonParser parser;

    public ProjectApiController(String accessToken) {
        this.accessToken = accessToken;
        this.projectApi = createApi();
        this.parser = new JsonParser();
    }

    private static ProjectApi createApi() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .client(client)
                .build();

        return retrofit.create(ProjectApi.class);
    }

    public void getProjects() {
        projectApi.getProjects(accessToken).enqueue(new Callback<ResponseBody>() {
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
                    List<Project> projects = parser.parseProjectList(responseStr);
                    try (Realm realm = Realm.getDefaultInstance()) {
                        realm.executeTransaction(tr -> {
                            tr.insertOrUpdate(projects);
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

    public void createProject(String name) {
        projectApi.create(accessToken, name).enqueue(new Callback<ResponseBody>() {
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
                    Project project = parser.parseProject(responseStr);
                    try (Realm realm = Realm.getDefaultInstance()) {
                        realm.executeTransaction(tr -> {
                            tr.insertOrUpdate(project);
                        });
                    }
                    GlobalBus.getBus().post(new BusMessage().projectCreated());
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

    public void update(Project project) {
        Integer id = project.getId();
        String name = project.getName();
        String description = project.getDescription();
        String color = project.getColor();
        projectApi.update(accessToken, id, name, description, color).enqueue(new Callback<ResponseBody>() {
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
                    Project project = parser.parseProject(responseStr);
                    try (Realm realm = Realm.getDefaultInstance()) {
                        realm.executeTransaction(tr -> {
                            String color = project.getColor();
                            RealmResults<Task> tasks = tr.where(Task.class)
                                    .equalTo("projectId", project.getId())
                                    .findAll();

                            for (Task task : tasks) {
                                task.setColor(color);
                            }
                            project.setTaskCount(tasks.size());
                            tr.insertOrUpdate(tasks);
                            tr.insertOrUpdate(project);
                        });
                    }
                    GlobalBus.getBus().post(new BusMessage().projectEdited());
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

    public void deleteProject(Integer projectId) {
        projectApi.delete(accessToken, projectId).enqueue(new Callback<ResponseBody>() {
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
                        GlobalBus.getBus().post(new BusMessage().projectDeleted());
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
