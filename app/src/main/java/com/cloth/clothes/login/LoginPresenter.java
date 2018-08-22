package com.cloth.clothes.login;

import android.support.annotation.NonNull;

import com.cloth.clothes.login.usecase.HttpLoginUseCase;
import com.cloth.clothes.utils.StringUtils;
import com.cloth.kernel.base.mvpclean.IDataRepository;
import com.cloth.kernel.base.mvpclean.UseCase;
import com.cloth.kernel.base.mvpclean.UseCaseHandler;
import com.cloth.kernel.base.utils.ToastUtil;
import com.cloth.kernel.service.LcAppWrapper;
import com.cloth.kernel.service.LoggerWrapper;

public class LoginPresenter implements LoginContract.IPresenter {

    private final LoginContract.IView mView;
    private final HttpLoginUseCase mHttpLoginUsecase;
    private final UseCaseHandler mUseCaseHandler;

    LoginPresenter(@NonNull LoginContract.IView view,@NonNull UseCaseHandler useCaseHandler,@NonNull HttpLoginUseCase loginUsecase) {
        this.mHttpLoginUsecase = loginUsecase;
        this.mUseCaseHandler = useCaseHandler;
        mView = view;
        HttpLoginUseCase.RequestValue userPass = loginUsecase.getUserPass();
        if (!StringUtils.isEmpty(userPass.userName)) {
            mView.setUserPass(userPass.userName,userPass.pass);
        }
    }



    @Override
    public void login(String name, String pass ,boolean remember) {

        mUseCaseHandler.execute(mHttpLoginUsecase, new HttpLoginUseCase.RequestValue(name, pass), new UseCase.UseCaseCallback<HttpLoginUseCase.ResponseValue>() {
            @Override
            public void onSuccess(HttpLoginUseCase.ResponseValue response) {
                mView.toastStr("登录成功");
                mView.jumpHomeAct(response.role,response.id);
            }

            @Override
            public void onError(int code, String msg) {
                mView.toastStr(msg);
                LoggerWrapper.e(msg);
            }
        });
    }

}
