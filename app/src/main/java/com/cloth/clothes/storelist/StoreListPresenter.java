package com.cloth.clothes.storelist;

import com.cloth.clothes.storelist.domain.usecase.HttpStoreListUseCase;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;

public class StoreListPresenter implements StoreListContract.IPresenter{

    private final UseCaseHandler mUseCaseHandler;
    private final HttpStoreListUseCase mHttpStoreListUseCase;

    public StoreListPresenter(UseCaseHandler useCaseHandler, HttpStoreListUseCase httpStoreListUseCase) {
        mUseCaseHandler = useCaseHandler;
        mHttpStoreListUseCase = httpStoreListUseCase;
    }

    @Override
    public void getNumberList(String color, String size, String store, boolean is) {
         mUseCaseHandler.execute(mHttpStoreListUseCase, new HttpStoreListUseCase.RequestValue(color, size, store), new UseCase.UseCaseCallback<HttpStoreListUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpStoreListUseCase.ResponseValue response) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }
}
