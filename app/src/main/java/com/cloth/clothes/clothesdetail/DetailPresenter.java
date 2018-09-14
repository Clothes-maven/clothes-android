package com.cloth.clothes.clothesdetail;

import android.support.annotation.NonNull;

import com.cloth.clothes.clothesdetail.domain.usecase.HttpFixClothesUseCase;
import com.cloth.clothes.clothesdetail.domain.usecase.HttpSellClothesUseCase;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;

/**
 * 商品详情的控制类
 */
public class DetailPresenter implements DetailContranct.IPresenter {


    private  DetailContranct.IView mIView;
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
    public void sellClothes(ClothesBean clothesBean, long userId,double sell) {
        mUseCaseHandler.execute(mSellClothesUseCase, new HttpSellClothesUseCase.RequestValue(clothesBean.id,sell,1,userId), new UseCase.UseCaseCallback<HttpSellClothesUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpSellClothesUseCase.ResponseValue response) {
                mIView.sellSuccess();
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
                mIView.fixSuccess();
            }

            @Override
            public void onError(int code, String msg) {
                mIView.error(msg);
            }
        });
    }

    @Override
    public void onDetach() {
        mIView =null;
    }
}
