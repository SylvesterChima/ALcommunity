package com.andela.sylvester.alcommunity.data.remote;

import com.andela.sylvester.alcommunity.data.model.Post;
import com.andela.sylvester.alcommunity.data.model.student;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Sylvester on 02/05/2017.
 */
public interface APIService {
    @GET("/api/Students")
    Call<List<student>> getStudent();

    @POST("/api/Students")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Post> SaveStudent(@Body Post post);

    @Multipart
    @POST("/api/Values/{id}")
    Call<ResponseBody> UploadUserPic(@Part MultipartBody.Part file, @Path("id") String di);


    @GET("/api/Values/{id}")
    Call<ResponseBody> GetUserPic(@Path("id") String di);
}
