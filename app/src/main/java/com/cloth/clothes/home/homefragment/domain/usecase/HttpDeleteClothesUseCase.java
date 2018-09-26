package com.cloth.clothes.home.homefragment.domain.usecase;

import android.support.annotation.NonNull;

import com.cloth.clothes.common.http.ApiService;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.mvpclean.HttpUseCase;
import com.cloth.kernel.base.mvpclean.IHttpRepository;
import com.cloth.kernel.service.http.BaseObserver;
import com.cloth.kernel.service.http.model.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

public class HttpDeleteClothesUseCase extends HttpUseCase<HttpDeleteClothesUseCase.RequestValue, HttpDeleteClothesUseCase.ResponseValue> {

    private final IHttpRepository mIHttpRepository;


    public HttpDeleteClothesUseCase(@NonNull IHttpRepository httpRepository) {
        this.mIHttpRepository = httpRepository;
    }

    @Override
    protected Observable<BaseResponse<ResponseValue>> params(RequestValue requestValue) {
        return mIHttpRepository.exec(ApiService.class).haltSale(requestValue.cid);
    }

    @Override
    protected void exec(Observable<BaseResponse<ResponseValue>> observable) {
        observable.subscribe(new BaseObserver<BaseResponse<ResponseValue>>() {
            @Override
            public void success(BaseResponse<ResponseValue> clothes) {
                if (clothes.getCode() == SUCCESS) {
                    getUseCaseCallback().onSuccess(clothes.getContent());
                } else {
                    getUseCaseCallback().onError(clothes.getCode(), clothes.getMsg());
                }
            }

            @Override
            public void error(int code, String msg) {
                getUseCaseCallback().onError(code, msg);
            }
        });
    }


    public static final class RequestValue extends HttpUseCase.RequestValues {
        public final String cid;

        public RequestValue(String cid) {
            this.cid = cid;
        }
    }

    public static final class ResponseValue extends HttpUseCase.ResponseValue {
    }
}
