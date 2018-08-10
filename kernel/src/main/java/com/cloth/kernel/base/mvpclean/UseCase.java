package com.cloth.kernel.base.mvpclean;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Use cases are the entry points to the domain layer.
 * 所有case的基类需继承自此类
 *
 * @param <Q> the request type
 * @param <P> the response type
 */
public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValue> {

    public static final int HTTP = 0x01;
    public static final int DATA_BASE = 0x02;

    @IntDef({HTTP, DATA_BASE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CASE_TYPE {

    }

    protected Q mRequestValues;

    private UseCaseCallback<P> mUseCaseCallback;

    public void setRequestValues(Q requestValues) {
        mRequestValues = requestValues;
    }

    public Q getRequestValues() {
        return mRequestValues;
    }

    public UseCaseCallback<P> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<P> useCaseCallback) {
        mUseCaseCallback = useCaseCallback;
    }


    void run() {
        executeUseCase(mRequestValues);
    }

    protected abstract void executeUseCase(@Nullable Q requestValues);

    /**
     * 请求的参数
     */
    public interface RequestValues extends Serializable {
    }

    /**
     * 返回对象
     */
    public interface ResponseValue extends Serializable {
    }

    public interface UseCaseCallback<R> {
        void onSuccess(R response);

        void onError(int code, String msg);
    }
}
