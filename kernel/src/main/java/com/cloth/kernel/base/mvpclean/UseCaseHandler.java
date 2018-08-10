package com.cloth.kernel.base.mvpclean;

/**
 * Runs {@link UseCase}s using a {@link UseCaseScheduler}.
 * 执行useCase的类
 */
public class UseCaseHandler {

    private final UseCaseScheduler mUseCaseScheduler;

    public UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        mUseCaseScheduler = useCaseScheduler;
    }

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValue> void execute(
            final UseCase<T, R> useCase, T values, UseCase.UseCaseCallback<R> callback) {
        useCase.setRequestValues(values);
        useCase.setUseCaseCallback(new UiCallbackWrapper(callback, this));

        mUseCaseScheduler.execute(new Runnable() {
            @Override
            public void run() {
                useCase.run();
            }
        });
    }

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValue> void execute(
            final UseCase<T, R> useCase, T values) {
        useCase.setRequestValues(values);

        mUseCaseScheduler.execute(new Runnable() {
            @Override
            public void run() {
                useCase.run();
            }
        });
    }


    public <V extends UseCase.ResponseValue> void notifyResponse(final V response,
            final UseCase.UseCaseCallback<V> useCaseCallback) {
        mUseCaseScheduler.notifyResponse(response, useCaseCallback);
    }

    private <V extends UseCase.ResponseValue> void notifyError(@UseCase.CASE_TYPE int type ,String  msg,
            final UseCase.UseCaseCallback<V> useCaseCallback) {
        mUseCaseScheduler.onError(type,msg,useCaseCallback);
    }

    private static final class UiCallbackWrapper<V extends UseCase.ResponseValue> implements
            UseCase.UseCaseCallback<V> {
        private final UseCase.UseCaseCallback<V> mCallback;
        private final UseCaseHandler mUseCaseHandler;

        public UiCallbackWrapper(UseCase.UseCaseCallback<V> callback,
                UseCaseHandler useCaseHandler) {
            mCallback = callback;
            mUseCaseHandler = useCaseHandler;
        }

        @Override
        public void onSuccess(V response) {
            mUseCaseHandler.notifyResponse(response, mCallback);
        }

        @Override
        public void onError(@UseCase.CASE_TYPE int type ,String  msg) {
            mUseCaseHandler.notifyError(type,msg,mCallback);
        }
    }

    private static class Inner {
        private static UseCaseHandler INSTANCE = new UseCaseHandler(new UseCaseThreadPoolScheduler());
    }
    public static UseCaseHandler getInstance() {
        return Inner.INSTANCE;
    }
}
