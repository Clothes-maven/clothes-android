package com.cloth.clothes.login.usecase;

import android.support.annotation.NonNull;

import com.cloth.clothes.common.http.ApiService;
import com.cloth.clothes.home.domain.model.StoreBean;
import com.cloth.kernel.base.mvpclean.HttpUseCase;
import com.cloth.clothes.model.UserManager;
import com.cloth.kernel.base.mvpclean.IDataRepository;
import com.cloth.kernel.base.mvpclean.IHttpRepository;
import com.cloth.kernel.service.http.BaseObserver;
import com.cloth.kernel.service.http.model.BaseResponse;

import io.reactivex.Observable;

public class HttpLoginUseCase extends HttpUseCase<HttpLoginUseCase.RequestValue, HttpLoginUseCase.ResponseValue> {

    private final IHttpRepository mIHttpRepository;
    private final IDataRepository mIDataRepository;
    public static final String LOGIN_USER_PASS = "login_user_pass";


    public HttpLoginUseCase(@NonNull IHttpRepository httpRepository, @NonNull IDataRepository dataRepository) {
        this.mIDataRepository = dataRepository;
        this.mIHttpRepository = httpRepository;
    }

    @Override
    protected Observable<BaseResponse<ResponseValue>> params(RequestValue requestValues) {
        return mIHttpRepository.exec(ApiService.class)
                .login(requestValues);
    }

    /**
     * 获得用户名以及密码
     */
    public RequestValue getUserPass() {
       return mIDataRepository.getLocalData(LOGIN_USER_PASS,new RequestValue("",""));
    }

    @Override
    protected void exec(Observable<BaseResponse<ResponseValue>> observable) {
        observable.subscribe(new BaseObserver<BaseResponse<ResponseValue>>() {
            @Override
            public void success(BaseResponse<ResponseValue> response) {
                ResponseValue content = response.getContent();
                if (response.code == SUCCESS) {
                    mIDataRepository.saveLocalData(LOGIN_USER_PASS, getRequestValues());
                    if (content != null) {
                        UserManager.User user = new UserManager.User();
                        user.role = content.role;
                        user.address = content.address;
                        user.id = content.id;
                        user.name = content.name;
                        user.sex = content.sex;
                        user.phone = content.phone;
                        user.qq = content.qq;
                        UserManager.getInstance().setUser(user, mIDataRepository);
                        getUseCaseCallback().onSuccess(response.getContent());
                    } else {
                        getUseCaseCallback().onError(LOCAL_FAIL, "未传递user信息");
                    }
                }
            }

            @Override
            public void error(int code, String msg) {
                getUseCaseCallback().onError(code, msg);
            }
        });
    }

    public static class RequestValue extends HttpUseCase.RequestValues {
        public  String userName;
        public  String pass;

        public RequestValue() {
        }

        public RequestValue(String name, String pass) {
            this.userName = name;
            this.pass = pass;
        }
    }

    public static final class ResponseValue extends HttpUseCase.ResponseValue {
        public long role;
        public String name;
        public String sex;
        public StoreBean address;
        public String phone;
        public String qq;
        public long id;
    }
}
