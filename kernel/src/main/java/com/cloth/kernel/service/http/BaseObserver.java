package com.cloth.kernel.service.http;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private Disposable mDisposable;

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T t) {
        success(t);
    }

    @Override
    public void onError(Throwable e) {
        error(-1,e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    public abstract void success(T t);
    public abstract void error(int code ,String msg);
}