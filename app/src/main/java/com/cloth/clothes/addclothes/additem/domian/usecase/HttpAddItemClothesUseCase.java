package com.cloth.clothes.addclothes.additem.domian.usecase;

import android.support.annotation.NonNull;

import com.cloth.clothes.common.http.ApiService;
import com.cloth.clothes.home.domain.model.StoreBean;
import com.cloth.kernel.base.mvpclean.HttpUseCase;
import com.cloth.kernel.base.mvpclean.IHttpRepository;
import com.cloth.kernel.service.http.BaseObserver;
import com.cloth.kernel.service.http.model.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * 商品数量列表
 */
public class HttpAddItemClothesUseCase extends HttpUseCase<HttpAddItemClothesUseCase.RequestValue, HttpAddItemClothesUseCase.ResponseValue> {


    private IHttpRepository mIHttpRepository;

    public HttpAddItemClothesUseCase(@NonNull IHttpRepository iHttpRepository) {
        this.mIHttpRepository = iHttpRepository;
    }

    @Override
    protected Observable<BaseResponse<ResponseValue>> params(RequestValue requestValue) {
        return mIHttpRepository.exec(ApiService.class).addClothesItem(requestValue);
    }

    @Override
    protected void exec(Observable<BaseResponse<ResponseValue>> observable) {
        observable.subscribe(new BaseObserver<BaseResponse<ResponseValue>>() {

            @Override
            public void success(BaseResponse<ResponseValue> response) {
                if (response.getCode() == SUCCESS) {
                    getUseCaseCallback().onSuccess(response.getContent());
                } else {
                    getUseCaseCallback().onError(response.getCode(), response.getMsg());
                }
            }

            @Override
            public void error(int code, String msg) {
                getUseCaseCallback().onError(code, msg);
            }
        });
    }

    public static final class RequestValue extends HttpUseCase.RequestValues {
        public  String size;
        public  String color;
        public  String number;
        public  String clotheId;
        public  String storeId;

        public RequestValue(String size, String color, String number, String clotheId, String storeId) {
            this.size = size;
            this.color = color;
            this.number = number;
            this.clotheId = clotheId;
            this.storeId = storeId;
        }
    }
    public static final class ResponseValue extends HttpUseCase.ResponseValue {
        public List<StoreBean> stores;
    }
}
