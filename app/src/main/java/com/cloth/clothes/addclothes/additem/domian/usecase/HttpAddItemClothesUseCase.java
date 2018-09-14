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
        return mIHttpRepository.exec(ApiService.class).addItem(requestValue.sid,requestValue.color,requestValue.number,requestValue.size);
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
        private final String sid;
        private final String number;
        private final String size;
        private final String color;

        public RequestValue(String sid, String number, String size, String color) {
            this.sid = sid;
            this.number = number;
            this.size = size;
            this.color = color;
        }
    }

    public static final class ResponseValue extends HttpUseCase.ResponseValue {
        public List<StoreBean> stores;
    }
}
