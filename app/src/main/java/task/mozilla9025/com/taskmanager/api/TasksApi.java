package task.mozilla9025.com.taskmanager.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TasksApi {

    @FormUrlEncoded
    @POST("tasks")
    Call<ResponseBody> tasks(@Field("email") String email,
                             @Field("password") String password);


}
