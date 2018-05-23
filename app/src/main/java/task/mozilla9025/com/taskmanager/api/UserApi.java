package task.mozilla9025.com.taskmanager.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(@Field("email") String email,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("registration")
    Call<ResponseBody> register(@Field("name") String name,
                                @Field("surname") String surname,
                                @Field("email") String email,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("account-confirm")
    Call<ResponseBody> confirmAccount(@Field("hash") String hash);

}
