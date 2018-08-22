package com.cloth.clothes.model;

import com.cloth.clothes.common.http.ApiService;
import com.cloth.kernel.base.mvpclean.IHttpRepository;
import com.cloth.kernel.service.http.HttpClient;

public class BaseHttpRepository implements IHttpRepository {

    private static  volatile BaseHttpRepository mHttpRepository;
    public static BaseHttpRepository getBaseHttpRepository() {
        if (mHttpRepository == null) {
            synchronized (LcBaseDataRepository.class) {
                if (mHttpRepository == null) {
                    mHttpRepository = new BaseHttpRepository();
                }
            }
        }
        return mHttpRepository;
    }

    private final HttpClient httpClient;
    public BaseHttpRepository() {
        httpClient = new HttpClient.Builder()
//                .ipPort("10.99.40.38",8888)
                .baseUrl(ApiService.BASE_URL)
                .build();
    }

    @Override
    public <T> T exec(Class<T> service) {
       return httpClient.exec(service);
    }
}
