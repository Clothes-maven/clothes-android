package com.cloth.clothes.storelist.domain.usecase;

import android.support.annotation.NonNull;

import com.cloth.clothes.common.http.ApiService;
import com.cloth.clothes.storelist.domain.model.NumberModel;
import com.cloth.kernel.base.mvpclean.HttpUseCase;
import com.cloth.kernel.base.mvpclean.IHttpRepository;
import com.cloth.kernel.service.http.BaseObserver;
import com.cloth.kernel.service.http.model.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * 商品数量列表
 */
public class HttpStoreListUseCase extends HttpUseCase<HttpStoreListUseCase.RequestValue, HttpStoreListUseCase.ResponseValue> {


    private IHttpRepository mIHttpRepository;

    public HttpStoreListUseCase(@NonNull IHttpRepository iHttpRepository) {
        this.mIHttpRepository = iHttpRepository;
    }

    @Override
    protected Observable<BaseResponse<ResponseValue>> params(RequestValue requestValue) {
        return mIHttpRepository.exec(ApiService.class).getSotreList(requestValue);
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
        String color;
        String size;
        String store;

        public RequestValue(String color, String size, String store) {
            this.color = color;
            this.size = size;
            this.store = store;
        }
    }

    public static final class ResponseValue extends HttpUseCase.ResponseValue {
        List<NumberModel> mList;
    }
}
