package com.cloth.clothes.addclothes.addbatch;

import android.support.annotation.NonNull;

import com.cloth.clothes.clothesdetail.domain.usecase.HttpFixClothesUseCase;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;

public class AddPresenter implements AddContract.IPresenter{

    private  AddContract.IView mIView;
    private final UseCaseHandler mUseCaseHandler;
    private final HttpFixClothesUseCase mHttpAddClothesUseCase;

    public AddPresenter(@NonNull AddContract.IView iView, UseCaseHandler useCaseHandler, HttpFixClothesUseCase httpAddClothesUseCase) {
        mIView = iView;
        mUseCaseHandler = useCaseHandler;
        mHttpAddClothesUseCase = httpAddClothesUseCase;
    }

    @Override
    public void addClothes(ClothesBean clothesBean) {
        mUseCaseHandler.execute(mHttpAddClothesUseCase, new HttpFixClothesUseCase.RequestValue(clothesBean), new UseCase.UseCaseCallback<HttpFixClothesUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpFixClothesUseCase.ResponseValue response) {
                if (mIView !=null) {
                    mIView.success();
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
        mIView =null;
    }
}
