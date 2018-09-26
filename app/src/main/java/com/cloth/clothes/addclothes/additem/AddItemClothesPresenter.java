package com.cloth.clothes.addclothes.additem;

import android.support.annotation.NonNull;

import com.cloth.clothes.addclothes.additem.domian.usecase.HttpAddItemClothesUseCase;
import com.cloth.clothes.addclothes.additem.domian.usecase.HttpGetStoreListUseCase;
import com.cloth.clothes.home.domain.model.StoreBean;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;

import java.util.ArrayList;
import java.util.List;

public class AddItemClothesPresenter implements AddItemClothesContract.IPresenter {


    private final UseCaseHandler mUseCaseHandler;
    private final HttpAddItemClothesUseCase mHttpAddItemClothesUseCase;
    private final HttpGetStoreListUseCase mHttpGetStoreListUseCase;
    private AddItemClothesContract.IView mIView;
    private List<StoreBean> mStoreBeans;

    public AddItemClothesPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull AddItemClothesContract.IView mIView, @NonNull HttpAddItemClothesUseCase httpStoreListUseCase, @NonNull HttpGetStoreListUseCase getStoreListUseCase) {
        mUseCaseHandler = useCaseHandler;
        mHttpAddItemClothesUseCase = httpStoreListUseCase;
        this.mHttpGetStoreListUseCase = getStoreListUseCase;
        this.mIView = mIView;
    }

    @Override
    public void addItemClothes(String color, String size, String number, String clothesId,String storeName) {
        String sid = null;
        for (StoreBean storeBean : mStoreBeans) {
            if (storeBean.name.equals(storeName)) {
                sid = storeBean.id;
                break;
            }
        }
        mUseCaseHandler.execute(mHttpAddItemClothesUseCase, new HttpAddItemClothesUseCase.RequestValue(size,color,number,clothesId,sid), new UseCase.UseCaseCallback<HttpAddItemClothesUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpAddItemClothesUseCase.ResponseValue response) {
                if (mIView !=null) {
                    mIView.addSuccess();
                }
            }

            @Override
            public void onError(int code, String msg) {
                if (mIView !=null) {
                    mIView.error(msg);
                }
            }
        });
    }

    @Override
    public void getStoreList(String uid) {
        mUseCaseHandler.execute(mHttpGetStoreListUseCase, new HttpGetStoreListUseCase.RequestValue(), new UseCase.UseCaseCallback<HttpGetStoreListUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpGetStoreListUseCase.ResponseValue response) {
                mStoreBeans = response.stores;
                if (mIView != null) {
                    List<String> strings = new ArrayList<>();
                    for (StoreBean storeBean : mStoreBeans) {
                        strings.add(storeBean.name);
                    }
                    mIView.setStores(strings);
                }
            }

            @Override
            public void onError(int code, String msg) {
                if (mIView !=null) {
                    mIView.error(msg);
                }
            }
        });
    }

    @Override
    public void onDetach() {
        mIView = null;
    }
}
