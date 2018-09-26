package com.cloth.clothes.home;

import android.support.annotation.NonNull;

import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.clothes.home.homefragment.domain.usecase.HttpDeleteClothesUseCase;
import com.cloth.clothes.home.homefragment.domain.usecase.HttpGetClothesUseCase;
import com.cloth.clothes.home.salelist.domain.usecase.HttpSaleListUseCase;
import com.cloth.clothes.model.UserManager;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;

import java.util.List;

public class HomeFrgPresenter implements HomeContract.IPresenter {

    private final UseCaseHandler mUseCaseHandler;
    private final HttpGetClothesUseCase mGetClothesUseCase;
    private final HttpSaleListUseCase mSaleListUseCase;
    private final HttpDeleteClothesUseCase mHttpDeleteClothesUseCase;
    private List<ClothesBean> mClothesBeanList;


    public HomeFrgPresenter(@NonNull UseCaseHandler useCaseHandler, @NonNull HttpGetClothesUseCase getClothesUseCase, HttpSaleListUseCase saleListUseCase, @NonNull HttpDeleteClothesUseCase deleteClothesUseCase) {
        mUseCaseHandler = useCaseHandler;
        this.mGetClothesUseCase = getClothesUseCase;
        mHttpDeleteClothesUseCase = deleteClothesUseCase;
        mSaleListUseCase = saleListUseCase;
    }

    @Override
    public void getClothes(final HomeContract.IStoreViewFrg iStoreView) {
        mUseCaseHandler.execute(mGetClothesUseCase, new HttpGetClothesUseCase.RequestValue(UserManager.getInstance().getId(), 10, 1), new UseCase.UseCaseCallback<HttpGetClothesUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpGetClothesUseCase.ResponseValue response) {
                mClothesBeanList = response.clothes;
                iStoreView.refresh(response.clothes, true, null);
            }

            @Override
            public void onError(int code, String msg) {
                iStoreView.refresh(null, false, msg);
            }
        });
    }

    @Override
    public void saleList(final HomeContract.ISaleViewFrg iSaleView, String time, String name) {
        mUseCaseHandler.execute(mSaleListUseCase, new HttpSaleListUseCase.RequestValue(time, name), new UseCase.UseCaseCallback<HttpSaleListUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpSaleListUseCase.ResponseValue response) {
                iSaleView.success(response.sellOuts);
            }

            @Override
            public void onError(int code, String msg) {
                iSaleView.error(msg);
            }
        });
    }

    @Override
    public void deleteClothes(final HomeContract.IStoreViewFrg iStoreView, String cid, final int position) {
        mUseCaseHandler.execute(mHttpDeleteClothesUseCase, new HttpDeleteClothesUseCase.RequestValue(cid), new UseCase.UseCaseCallback<HttpDeleteClothesUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpDeleteClothesUseCase.ResponseValue response) {
                mClothesBeanList.remove(position);
                iStoreView.refresh(mClothesBeanList, true, null);
            }

            @Override
            public void onError(int code, String msg) {
                iStoreView.refresh(null, false, msg);
            }
        });
    }


    @Override
    public void onDetach() {

    }
}
