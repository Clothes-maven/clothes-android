package com.cloth.clothes.login;

import com.cloth.clothes.common.LogImp;
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
    }

    @Test
    public void isEmployee() {
    }

    @Test
    public void testmock () {
        HttpClient httpClient = new HttpClient.Builder()
                .async(false)
                .baseUrl("http://www.baidu.com")
                .ipPort("10.99.40.38",8888)
                .build();
        httpClient.exec(ApiService.class)
                .testMock("1")
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<Object> objectBaseResponse) {
                        String busiCode = objectBaseResponse.busiCode;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Throwable e1 = e;
                        LoggerWrapper.wtf(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}