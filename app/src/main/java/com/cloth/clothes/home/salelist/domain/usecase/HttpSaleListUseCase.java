package com.cloth.clothes.home.salelist.domain.usecase;

import com.cloth.clothes.common.http.ApiService;
import com.cloth.clothes.common.http.HttpUseCase;
import com.cloth.kernel.base.mvpclean.IHttpRepository;
import com.cloth.kernel.service.http.BaseObserver;
import com.cloth.kernel.service.http.model.BaseResponse;

import io.reactivex.Observable;

public class HttpSaleListUseCase extends HttpUseCase<HttpSaleListUseCase.RequestValue,HttpSaleListUseCase.ResponseValue>{

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
          public void success(BaseResponse<ResponseValue> responseValueBaseResponse) {

          }

          @Override
          public void error(int code, String msg) {

          }
      });
    }

    public static final class RequestValue extends HttpUseCase.RequestValues {
        private final String time;


        public RequestValue(String  time) {
            this.time = time;
        }
    }

    public  static final class ResponseValue extends HttpUseCase.ResponseValue {

    }
}
