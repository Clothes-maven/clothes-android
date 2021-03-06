package com.cloth.clothes.home.salelist.domain.usecase;

import com.cloth.clothes.common.http.ApiService;
import com.cloth.clothes.utils.StringUtils;
import com.cloth.kernel.base.mvpclean.HttpUseCase;
import com.cloth.clothes.home.salelist.domain.model.SaleBean;
import com.cloth.kernel.base.mvpclean.IHttpRepository;
import com.cloth.kernel.service.http.BaseObserver;
import com.cloth.kernel.service.http.model.BaseResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class HttpSaleListUseCase extends HttpUseCase<HttpSaleListUseCase.RequestValue, HttpSaleListUseCase.ResponseValue> {


    private IHttpRepository mIHttpRepository;

    public HttpSaleListUseCase(IHttpRepository IHttpRepository) {
        mIHttpRepository = IHttpRepository;
    }

    @Override
    protected Observable<BaseResponse<ResponseValue>> params(RequestValue request) {
        return mIHttpRepository.exec(ApiService.class).saleList(request.time);
    }

    @Override
    protected void exec(Observable<BaseResponse<ResponseValue>> observable) {
        observable.subscribe(new BaseObserver<BaseResponse<ResponseValue>>() {
            @Override
            public void success(BaseResponse<ResponseValue> response) {
                if (response.getCode() == SUCCESS) {
                    if (!StringUtils.isEmpty(getRequestValues().name)) {
                        for (int i = response.getContent().sellOuts.size() - 1; i >= 0; i--) {
                            if (!getRequestValues().name.equals(response.getContent().sellOuts.get(i).user.name)) {
                                response.getContent().sellOuts.remove(i);
                            }
                        }
                    }
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
        private final String time;
        private final String name;

        public RequestValue(String time, String name) {
            this.time = time;
            this.name = name;
        }
    }

    public static final class ResponseValue extends HttpUseCase.ResponseValue {
        public List<SaleBean> sellOuts;
    }
}
