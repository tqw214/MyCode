package com.viger.mycode.net;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceApi {

    @GET("{userId}/userInfo")
    Observable<BaseResult<UserInfo>> getUserInfo(@Path("userId") int id);

}
