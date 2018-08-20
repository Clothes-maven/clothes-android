package com.cloth.clothes.home.homefragment.domain.usecase;

import android.support.annotation.NonNull;

import com.cloth.clothes.common.http.ApiService;
import com.cloth.clothes.common.http.HttpUseCase;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.mvpclean.IDataRepository;
import com.cloth.kernel.base.mvpclean.IHttpRepository;
import com.cloth.kernel.service.http.BaseObserver;
import com.cloth.kernel.service.http.model.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

public class HttpGetClothesUseCase extends HttpUseCase<HttpGetClothesUseCase.RequestValue,HttpGetClothesUseCase.ResponseValue>{

    private final IHttpRepository mIHttpRepository;
    private final IDataRepository mIDataRepository;


    public HttpGetClothesUseCase(@NonNull IHttpRepository httpRepository, @NonNull IDataRepository dataRepository) {
        this.mIDataRepository = dataRepository;
        this.mIHttpRepository = httpRepository;
    }

    @Override
    protected Observable<BaseResponse<ResponseValue>> params(RequestValue requestValue) {
        return mIHttpRepository.exec(ApiService.class).getClothes(requestValue.uid,requestValue.number,requestValue.pager);
    }

    @Override
    protected void exec(Observable<BaseResponse<ResponseValue>> observable) {
        observable.subscribe(new BaseObserver<BaseResponse<ResponseValue>>() {
            @Override
            public void success(BaseResponse<ResponseValue> clothes) {
                if (clothes.getCode() == SUCCESS) {
                    getUseCaseCallback().onSuccess(clothes.getContent());
                } else {
                    getUseCaseCallback().onError(clothes.getCode(),clothes.getMsg());
                }
            }

            @Override
            public void error(int code, String msg) {
                getUseCaseCallback().onError(code,msg);
            }
        });
    }


    public static final class RequestValue extends HttpUseCase.RequestValues{
        private final long uid;
        private final int number;
        private final int pager;

        public RequestValue(long uid, int number, int pager) {
            this.uid = uid;
            this.number = number;
            this.pager = pager;
        }
    }

    public static final class ResponseValue extends HttpUseCase.ResponseValue {
        public List<ClothesBean> clothes;
    }
}
