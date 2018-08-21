package com.cloth.clothes.home;

import android.support.annotation.NonNull;

import com.cloth.clothes.home.homefragment.domain.usecase.HttpGetClothesUseCase;
import com.cloth.clothes.home.salelist.domain.usecase.HttpSaleListUseCase;
import com.cloth.clothes.model.UserManager;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;

import java.text.SimpleDateFormat;

public class HomeFrgPresenter implements HomeContract.IPresenter{

    private final UseCaseHandler mUseCaseHandler;
    private final HttpGetClothesUseCase mGetClothesUseCase;
    private final HttpSaleListUseCase mSaleListUseCase;


    public HomeFrgPresenter( @NonNull UseCaseHandler useCaseHandler, @NonNull HttpGetClothesUseCase getClothesUseCase, HttpSaleListUseCase saleListUseCase) {
        mUseCaseHandler = useCaseHandler;
        this.mGetClothesUseCase = getClothesUseCase;
        mSaleListUseCase = saleListUseCase;
    }

    @Override
    public void getClothes(final HomeContract.IStoreView iStoreView){
        mUseCaseHandler.execute(mGetClothesUseCase, new HttpGetClothesUseCase.RequestValue(UserManager.getInstance().getId(), 10, 1), new UseCase.UseCaseCallback<HttpGetClothesUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpGetClothesUseCase.ResponseValue response) {
                iStoreView.refresh(response.clothes,true);
            }

            @Override
            public void onError(int code, String msg) {
                iStoreView.refresh(null,false);
            }
        });
    }

    @Override
    public void saleList(HomeContract.ISaleView iSaleView,String time) {
        mUseCaseHandler.execute(mSaleListUseCase, new HttpSaleListUseCase.RequestValue(time), new UseCase.UseCaseCallback<HttpSaleListUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpSaleListUseCase.ResponseValue response) {

            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }
}
