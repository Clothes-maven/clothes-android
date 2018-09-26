package com.cloth.clothes.common.http;

import com.cloth.clothes.BuildConfig;
import com.cloth.clothes.addclothes.additem.domian.usecase.HttpAddItemClothesUseCase;
import com.cloth.clothes.addclothes.additem.domian.usecase.HttpGetStoreListUseCase;
import com.cloth.clothes.clothesdetail.domain.usecase.HttpFixClothesUseCase;
import com.cloth.clothes.clothesdetail.domain.usecase.HttpSellClothesUseCase;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.home.homefragment.domain.usecase.HttpDeleteClothesUseCase;
import com.cloth.clothes.home.homefragment.domain.usecase.HttpGetClothesUseCase;
import com.cloth.clothes.home.salelist.domain.usecase.HttpSaleListUseCase;
import com.cloth.clothes.login.usecase.HttpLoginUseCase;
import com.cloth.clothes.clothessecondlist.domain.usecase.HttpDeleteItemUseCase;
import com.cloth.clothes.clothessecondlist.domain.usecase.HttpStoreListUseCase;
import com.cloth.clothes.clothessecondlist.domain.usecase.HttpSwitchStateUseCase;
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

    @GET("/store/getClothDetails")
    Observable<BaseResponse<HttpStoreListUseCase.ResponseValue>> getClothesList(@Query("cid") String  cid , @Query("sid") String  sid);

    //批次停止出售
    @GET("/store/haltSale")
    Observable<BaseResponse<HttpDeleteClothesUseCase.ResponseValue>> haltSale(@Query("cid") String  cid);

    //获取门店列表
    @GET("/store/getStores")
    Observable<BaseResponse<HttpGetStoreListUseCase.ResponseValue>> getStoreList();

    //修改衣服状态
    @GET("/store/stopOrStartSell")
    Observable<BaseResponse<HttpSwitchStateUseCase.ResponseValue>> switchState(@Query("cid") String  cid , @Query("isStopSell") String  isStopSell);

    //删除某个item 批次
    @GET("store/deleteClothDetail")
    Observable<BaseResponse<HttpDeleteItemUseCase.ResponseValue>> deleteClothesItem(@Query("cid") String  cid);

    //添加某个item 批次
    @POST("/store/addClothDetail")
    Observable<BaseResponse<HttpAddItemClothesUseCase.ResponseValue>> addClothesItem(@Body HttpAddItemClothesUseCase.RequestValue body);
}
