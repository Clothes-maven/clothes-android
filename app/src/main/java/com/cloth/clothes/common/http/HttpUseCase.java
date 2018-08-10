package com.cloth.clothes.common.http;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.service.http.model.BaseApiRequest;
import com.cloth.kernel.service.http.model.BaseResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;


public abstract class HttpUseCase<Q extends HttpUseCase.RequestValues, P extends HttpUseCase.ResponseValue> extends UseCase<Q,P>{

    public static final int SUCCESS = 1;
    public static final int LOCAL_FAIL = -1001;
    public static final int UN_LOGIN = -1002;
    public static final int FAIL = -1003;
    @IntDef({SUCCESS,LOCAL_FAIL,UN_LOGIN,FAIL})
    @Retention(RetentionPolicy.SOURCE)
    protected  @interface HTTP_CODE{}

    protected abstract Observable<BaseResponse<P>> params(Q q);

    protected abstract void exec(Observable<BaseResponse<P>> observable);

    @Override
    protected void executeUseCase(@Nullable Q requestValues) {
        Observable<BaseResponse<P>> pObservable = params(mRequestValues)
                .map(new Function<BaseResponse<P>, BaseResponse<P>>() {
                    @Override
                    public BaseResponse<P> apply(BaseResponse<P> pBaseResponse) throws Exception {
                        return pBaseResponse;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
        exec(pObservable);
    }

    public static class RequestValues extends BaseApiRequest implements UseCase.RequestValues {

    }

    public static class ResponseValue implements UseCase.ResponseValue {

    }
}
