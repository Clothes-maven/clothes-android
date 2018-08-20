package com.cloth.clothes.login;

import com.cloth.clothes.login.usecase.HttpLoginUseCase;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;
import com.cloth.kernel.base.utils.ToastUtil;
import com.cloth.kernel.service.LcAppWrapper;
import com.cloth.kernel.service.LoggerWrapper;

public class LoginPresenter implements LoginContract.IPresenter {

    private final LoginContract.IView mView;
    private final HttpLoginUseCase mHttpLoginUsecase;
    private final UseCaseHandler mUseCaseHandler;

    LoginPresenter(LoginContract.IView view,UseCaseHandler useCaseHandler,HttpLoginUseCase loginUsecase) {
        this.mHttpLoginUsecase = loginUsecase;
        this.mUseCaseHandler = useCaseHandler;
        mView = view;
    }

    @Override
    public void login(String name, String pass) {

        mUseCaseHandler.execute(mHttpLoginUsecase, new HttpLoginUseCase.RequestValue(name, pass), new UseCase.UseCaseCallback<HttpLoginUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpLoginUseCase.ResponseValue response) {
                mView.jumpHomeAct(response.role,response.id);
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtil.showShortMsg(LcAppWrapper.getLcAppWrapper(),msg);
                LoggerWrapper.e(msg);
            }
        });
    }

}
