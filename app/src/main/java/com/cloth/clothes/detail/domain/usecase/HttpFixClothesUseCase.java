package com.cloth.clothes.detail.domain.usecase;

import com.cloth.clothes.common.http.HttpUseCase;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.service.http.model.BaseResponse;

import io.reactivex.Observable;

/**
 * 修改衣服内容 ：属于老板的操作
 */
public class HttpFixClothesUseCase extends HttpUseCase<HttpFixClothesUseCase.RequestValue,HttpFixClothesUseCase.ResponseValue>{


    @Override
    protected Observable<BaseResponse<ResponseValue>> params(RequestValue requestValue) {
        return null;
    }

    @Override
    protected void exec(Observable<BaseResponse<ResponseValue>> observable) {

    }

    public static final class RequestValue extends HttpUseCase.RequestValues{
        private final ClothesBean clothesBean;


        public RequestValue(ClothesBean clothesBean) {
            this.clothesBean = clothesBean;
        }
    }

    public static final class ResponseValue extends HttpUseCase.ResponseValue{

    }
}
