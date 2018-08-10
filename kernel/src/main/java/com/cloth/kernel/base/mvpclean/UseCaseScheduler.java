package com.cloth.kernel.base.mvpclean;

/**
 * Interface for schedulers, see {@link UseCaseThreadPoolScheduler}.
 */
public interface UseCaseScheduler {

    void execute(Runnable runnable);

    <V extends UseCase.ResponseValue> void notifyResponse(final V response,
                                                          final UseCase.UseCaseCallback<V> useCaseCallback);

    <V extends UseCase.ResponseValue> void onError(@UseCase.CASE_TYPE int type, String  msg,
                                                   final UseCase.UseCaseCallback<V> useCaseCallback);
}
