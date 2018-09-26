package com.cloth.clothes.clothesdetail.domain.usecase;

import com.cloth.clothes.common.http.ApiService;
import com.cloth.kernel.base.mvpclean.HttpUseCase;
import com.cloth.kernel.base.mvpclean.IHttpRepository;
import com.cloth.kernel.service.http.BaseObserver;
import com.cloth.kernel.service.http.model.BaseResponse;

import io.reactivex.Observable;

/**
 * 卖出一件衣服的case
 */
public class HttpSellClothesUseCase extends HttpUseCase<HttpSellClothesUseCase.RequestValue, HttpSellClothesUseCase.ResponseValue> {


    private IHttpRepository mIHttpRepository;

    public HttpSellClothesUseCase(IHttpRepository IHttpRepository) {
        mIHttpRepository = IHttpRepository;
    }

    @Override
    protected Observable<BaseResponse<ResponseValue>> params(RequestValue requestValue) {
        return mIHttpRepository.exec(ApiService.class).sellClothes(requestValue);
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
        public final String  clothesId;//服装品类id
        public final String  sell;//卖出价格
        public final String  number;//卖出数量
        public final String  userId;//卖出人id

//        String clothesId;//服装品类id
//        double sell;//卖出价格
//        long number;//卖出数量
//        String userId;//卖出人id


        public RequestValue(String clothesId, String sell, String number, String userId) {
            this.clothesId = clothesId;
            this.sell = sell;
            this.number = number;
            this.userId = userId;
        }
    }

    public static final class ResponseValue extends HttpUseCase.ResponseValue {

    }
}
