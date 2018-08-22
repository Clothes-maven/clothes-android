package com.cloth.clothes.clothesdetail.domain.usecase;

import com.cloth.clothes.common.http.ApiService;
import com.cloth.kernel.base.mvpclean.HttpUseCase;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.mvpclean.IHttpRepository;
import com.cloth.kernel.service.http.BaseObserver;
import com.cloth.kernel.service.http.model.BaseResponse;

import io.reactivex.Observable;

/**
 * 修改衣服内容 ：属于老板的操作
 */
public class HttpFixClothesUseCase extends HttpUseCase<HttpFixClothesUseCase.RequestValue, HttpFixClothesUseCase.ResponseValue> {

    private IHttpRepository mIHttpRepository;

    public HttpFixClothesUseCase(IHttpRepository IHttpRepository) {
        mIHttpRepository = IHttpRepository;
    }

    @Override
    protected Observable<BaseResponse<ResponseValue>> params(RequestValue requestValue) {
        return mIHttpRepository.exec(ApiService.class).addOrFix(requestValue.clothesBean);
    }

    @Override
    protected void exec(Observable<BaseResponse<ResponseValue>> observable) {
        observable.subscribe(new BaseObserver<BaseResponse<ResponseValue>>() {
            @Override
            public void success(BaseResponse<ResponseValue> responseValueBaseResponse) {
                if (responseValueBaseResponse.getCode() == SUCCESS) {
                    getUseCaseCallback().onSuccess(responseValueBaseResponse.getContent());
                } else {
                    getUseCaseCallback().onError(responseValueBaseResponse.getCode(), responseValueBaseResponse.getMsg());
                }
            }

            @Override
            public void error(int code, String msg) {
                getUseCaseCallback().onError(code, msg);
            }
        });
    }

    public static final class RequestValue extends HttpUseCase.RequestValues {
        private final ClothesBean clothesBean;


        public RequestValue(ClothesBean clothesBean) {
            this.clothesBean = clothesBean;
        }
    }

    public static final class ResponseValue extends HttpUseCase.ResponseValue {

    }
}
