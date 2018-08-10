package com.cloth.kernel.service.http.httplog;

import com.cloth.kernel.service.LoggerWrapper;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogIntercepter implements HttpLoggingInterceptor.Logger{
    @Override
    public void log(String message) {
        LoggerWrapper.d(message);
    }
}
