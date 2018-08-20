package com.cloth.clothes.addclothes;

import com.cloth.clothes.detail.domain.usecase.HttpFixClothesUseCase;
import com.cloth.clothes.home.domain.model.ClothesBean;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;

public class AddPresenter implements AddContract.IPresenter{

    private final AddContract.IView mIView;
    private final UseCaseHandler mUseCaseHandler;
    private final HttpFixClothesUseCase mHttpAddClothesUseCase;

    public AddPresenter(AddContract.IView iView, UseCaseHandler useCaseHandler, HttpFixClothesUseCase httpAddClothesUseCase) {
        mIView = iView;
        mUseCaseHandler = useCaseHandler;
        mHttpAddClothesUseCase = httpAddClothesUseCase;
    }

    @Override
    public void addClothes(ClothesBean clothesBean) {
        mUseCaseHandler.execute(mHttpAddClothesUseCase, new HttpFixClothesUseCase.RequestValue(clothesBean), new UseCase.UseCaseCallback<HttpFixClothesUseCase.ResponseValue>() {
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
