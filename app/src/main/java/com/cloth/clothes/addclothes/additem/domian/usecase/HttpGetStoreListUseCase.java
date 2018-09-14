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
public class HttpGetStoreListUseCase extends HttpUseCase<HttpGetStoreListUseCase.RequestValue, HttpGetStoreListUseCase.ResponseValue> {


    private IHttpRepository mIHttpRepository;

    public HttpGetStoreListUseCase(@NonNull IHttpRepository iHttpRepository) {
        this.mIHttpRepository = iHttpRepository;
    }

    @Override
    protected Observable<BaseResponse<ResponseValue>> params(RequestValue requestValue) {
        return mIHttpRepository.exec(ApiService.class).getStoreList(requestValue);
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
        private final String uid;

        public RequestValue(String uid) {
            this.uid = uid;
        }
    }

    public static final class ResponseValue extends HttpUseCase.ResponseValue {
        public List<StoreBean> stores;
    }
}
