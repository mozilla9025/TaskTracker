package task.mozilla9025.com.taskmanager.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TasksApi {

    @FormUrlEncoded
    @POST("tasks")
    Call<ResponseBody> tasksInInbox(@Field("access_token") String accessToken,
                                    @Field("limit") int limit,
                                    @Field("offset") int offset);

    @FormUrlEncoded
    @POST("tasks")
    Call<ResponseBody> tasksInProject(@Field("access_token") String accessToken,
                                      @Field("project_id") Integer projectId,
                                      @Field("limit") int limit,
                                      @Field("offset") int offset);

    @FormUrlEncoded
    @POST("task/create")
    Call<ResponseBody> createTaskInInbox(@Field("access_token") String accessToken,
                                         @Field("name") String title);

    @FormUrlEncoded
    @POST("task/create")
    Call<ResponseBody> createTaskInProject(@Field("access_token") String accessToken,
                                           @Field("project_id") int projectId,
                                           @Field("name") String title);

    @FormUrlEncoded
    @POST("task/update")
    Call<ResponseBody> update(@Field("access_token") String accessToken,
                              @Field("task_id") Integer taskId,
                              @Field("title") String title,
                              @Field("description") String description,
                              @Field("project_id") Integer projectId,
                              @Field("scheduled_to") Integer scheduledTo,
                              @Field("due_date") Integer dueDate);

    @FormUrlEncoded
    @POST("task/delete")
    Call<ResponseBody> deleteTask(@Field("access_token") String accessToken,
                                  @Field("task_id") Integer taskId);


}
