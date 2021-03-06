package com.cloth.clothes.login;

import com.alibaba.fastjson.JSON;
import com.cloth.clothes.common.LogImp;
import com.cloth.clothes.login.usecase.HttpLoginUseCase;
import com.cloth.clothes.model.Role;
import com.cloth.clothes.common.http.ApiService;
import com.cloth.kernel.service.LoggerWrapper;
import com.cloth.kernel.service.http.HttpClient;
import com.cloth.kernel.service.http.model.BaseResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RoleTest {

    @Before
    public void setUp() throws Exception {
        LoggerWrapper.setILog(new LogImp());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isOwner() {
        boolean owner = Role.isOwner(Role.OWNER);
        assert owner;
    }

    @Test
    public void isPurchase() {
        final int a = 10;
        final int b = 20;
        int c = Math.abs(a+b);
        System.out.print(c);
    }

    @Test
    public void isEmployee() {
    }

    @Test
    public void testmock () {
        HttpClient httpClient = new HttpClient.Builder()
                .async(false)
                .baseUrl("http://www.baidu.com")
//                .ipPort("10.99.40.38",8888)
                .build();
        httpClient.exec(ApiService.class)
                .login(new HttpLoginUseCase.RequestValue("dd","dd"))
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<HttpLoginUseCase.ResponseValue>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<HttpLoginUseCase.ResponseValue> response) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Test
    public void fastJsonTest() {
        HttpLoginUseCase.ResponseValue  responseValue = new HttpLoginUseCase.ResponseValue();
//        responseValue.address = "ddd";
        responseValue.phone= "2q435443654";
        HttpLoginUseCase.RequestValue requestValue = new HttpLoginUseCase.RequestValue("11","33");
        System.out.print(JSON.toJSONString(requestValue));
        System.out.print(JSON.toJSONString(responseValue));
    }

}