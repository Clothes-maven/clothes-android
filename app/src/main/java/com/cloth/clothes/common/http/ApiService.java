package com.cloth.clothes.common.http;

import com.cloth.clothes.BuildConfig;
import com.cloth.clothes.clothesdetail.domain.usecase.HttpFixClothesUseCase;
import com.cloth.clothes.clothesdetail.domain.usecase.HttpSellClothesUseCase;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.home.homefragment.domain.usecase.HttpGetClothesUseCase;
import com.cloth.clothes.home.salelist.domain.usecase.HttpSaleListUseCase;
import com.cloth.clothes.login.usecase.HttpLoginUseCase;
import com.cloth.clothes.storelist.domain.usecase.HttpStoreListUseCase;
import com.cloth.kernel.service.http.model.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = BuildConfig.BASE_URL;

    @POST("/user/login")
    Observable<BaseResponse<HttpLoginUseCase.ResponseValue>> login(@Body HttpLoginUseCase.RequestValue body);

    @GET("/store/getClothes")
    Observable<BaseResponse<HttpGetClothesUseCase.ResponseValue>> getClothes(@Query("uid") long uid, @Query("number") int number, @Query("pager") int pager);

    @POST("/store/goInStore")
    Observable<BaseResponse<HttpFixClothesUseCase.ResponseValue>> addOrFix(@Body ClothesBean body);

    @GET("/sellout/findByDate")
    Observable<BaseResponse<HttpSaleListUseCase.ResponseValue>> saleList(@Query("time") String time);

    @POST("/sellout/sell")
    Observable<BaseResponse<HttpSellClothesUseCase.ResponseValue>> sellClothes(@Body HttpSellClothesUseCase.RequestValue body);
    @POST("/sellout/sell")
    Observable<BaseResponse<HttpStoreListUseCase.ResponseValue>> getSotreList(@Body HttpStoreListUseCase.RequestValue body);
}
