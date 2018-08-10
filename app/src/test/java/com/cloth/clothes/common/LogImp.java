package com.cloth.clothes.common;

import com.cloth.kernel.service.LoggerWrapper;

public class LogImp implements LoggerWrapper.ILog{
    @Override
    public void i(String msg) {
        System.out.print(msg);
    }

    @Override
    public void d(String msg) {
        System.out.print(msg);
    }

    @Override
    public void w(String msg) {
        System.out.print(msg);
    }

    @Override
    public void e(String msg) {
        System.out.print(msg);
    }

    @Override
    public void wtf(Throwable tr) {
        System.out.print(tr.getMessage());
    }
}
