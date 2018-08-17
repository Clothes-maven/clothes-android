package com.cloth.clothes.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cloth.clothes.home.domain.usecase.HttpGetClothesUseCase;
import com.cloth.clothes.model.UserManager;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;

public class HomeFrgPresenter implements HomeContract.IPresenter{

    private final HomeContract.IView mIView;
    private final UseCaseHandler mUseCaseHandler;
    private final HttpGetClothesUseCase mGetClothesUseCase;


    public HomeFrgPresenter(@NonNull HomeContract.IView IView, @NonNull UseCaseHandler useCaseHandler, @NonNull HttpGetClothesUseCase getClothesUseCase) {
        mIView = IView;
        mUseCaseHandler = useCaseHandler;
        this.mGetClothesUseCase = getClothesUseCase;
    }

    @Override
    public void getClothes(){
        mUseCaseHandler.execute(mGetClothesUseCase, new HttpGetClothesUseCase.RequestValue(UserManager.getInstance().getId()), new UseCase.UseCaseCallback<HttpGetClothesUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpGetClothesUseCase.ResponseValue response) {
                mIView.refresh(response.mClothesBeanList,true);
            }

            @Override
            public void onError(int code, String msg) {
                mIView.refresh(null,false);
            }
        });
    }

}
