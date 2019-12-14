package com.viger.mycode.retrofit;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("user/{name}/info")
    Single<List<String>> getResponse(@Path("name") String username);

}
