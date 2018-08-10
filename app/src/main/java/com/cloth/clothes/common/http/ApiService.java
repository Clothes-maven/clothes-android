package com.cloth.clothes.common.http;

import com.cloth.clothes.login.usecase.HttpLoginUseCase;
import com.cloth.kernel.service.http.model.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("/login")
    Observable<BaseResponse<HttpLoginUseCase.ResponseValue>> login(@Field("name") String name, @Field("pass") String pass);

}
