package com.cloth.clothes.model;

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

    private static final String BASE_URL = "http://www.cloth.clothes";
    private final HttpClient httpClient;
    public BaseHttpRepository() {
        httpClient = new HttpClient.Builder()
                .ipPort("10.99.40.38",8888)
                .baseUrl(BASE_URL)
                .build();
    }

    @Override
    public <T> T exec(Class<T> service) {
       return httpClient.exec(service);
    }
}
