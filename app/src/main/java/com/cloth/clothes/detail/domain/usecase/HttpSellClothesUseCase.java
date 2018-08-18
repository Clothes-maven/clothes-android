package com.cloth.clothes.detail.domain.usecase;

import com.cloth.clothes.common.http.HttpUseCase;
import com.cloth.kernel.service.http.model.BaseResponse;

import io.reactivex.Observable;

/**
 * 卖出一件衣服的case
 */
public class HttpSellClothesUseCase extends HttpUseCase<HttpSellClothesUseCase.RequestValue,HttpSellClothesUseCase.ResponseValue>{


    @Override
    protected Observable<BaseResponse<ResponseValue>> params(RequestValue requestValue) {

        return null;
    }

    @Override
    protected void exec(Observable<BaseResponse<ResponseValue>> observable) {

    }

    public static final class RequestValue extends HttpUseCase.RequestValues{
        private final String name;
        private final String userName;

        public RequestValue(String name, String userName) {
            this.name = name;
            this.userName = userName;
        }
    }

    public static final class ResponseValue extends HttpUseCase.ResponseValue{

    }
}
