package task.mozilla9025.com.taskmanager.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProjectApi {

    @FormUrlEncoded
    @POST("projects")
    Call<ResponseBody> getProjects(@Field("access_token") String accessToken);

    @FormUrlEncoded
    @POST("project/create")
    Call<ResponseBody> create(@Field("access_token") String accessToken,
                              @Field("name") String name);

    @FormUrlEncoded
    @POST("project/edit")
    Call<ResponseBody> edit(@Field("access_token") String accessToken,
                            @Field("project_id") Integer projectId,
                            @Field("prop") String property,
                            @Field("value") String value);

    @FormUrlEncoded
    @POST("project/delete")
    Call<ResponseBody> delete(@Field("access_token") String accessToken,
                              @Field("project_id") Integer projectId);

}
