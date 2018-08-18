package com.cloth.clothes.detail;

import android.support.annotation.NonNull;

import com.cloth.clothes.detail.domain.usecase.HttpFixClothesUseCase;
import com.cloth.clothes.detail.domain.usecase.HttpSellClothesUseCase;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;

/**
 * 商品详情的控制类
 */
public class DetailPresenter implements DetailContranct.IPresenter {


    private final DetailContranct.IView mIView;
    private final UseCaseHandler mUseCaseHandler;
    private final HttpSellClothesUseCase mSellClothesUseCase;
    private final HttpFixClothesUseCase mHttpFixClothesUseCase;


    public DetailPresenter(@NonNull DetailContranct.IView IView, @NonNull UseCaseHandler useCaseHandler, @NonNull HttpSellClothesUseCase sellClothesUseCase, @NonNull HttpFixClothesUseCase httpFixClothesUseCase) {
        mIView = IView;
        mUseCaseHandler = useCaseHandler;
        this.mSellClothesUseCase = sellClothesUseCase;
        mHttpFixClothesUseCase = httpFixClothesUseCase;
    }


    @Override
    public void sellClothes(ClothesBean clothesBean, String userName) {
        mUseCaseHandler.execute(mSellClothesUseCase, new HttpSellClothesUseCase.RequestValue(clothesBean.name, userName), new UseCase.UseCaseCallback<HttpSellClothesUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpSellClothesUseCase.ResponseValue response) {
                mIView.success();
            }

            @Override
            public void onError(int code, String msg) {
                mIView.error(msg);
            }
        });
    }

    @Override
    public void fixClothes(ClothesBean clothesBean) {
        mUseCaseHandler.execute(mHttpFixClothesUseCase, new HttpFixClothesUseCase.RequestValue(clothesBean), new UseCase.UseCaseCallback<HttpFixClothesUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpFixClothesUseCase.ResponseValue response) {
                mIView.success();
            }

            @Override
            public void onError(int code, String msg) {
                mIView.error(msg);
            }
        });
    }
}
