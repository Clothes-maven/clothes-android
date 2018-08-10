package com.cloth.kernel.base.eventbus;

import java.lang.reflect.Method;

/**
 * Created by xmq on 2018/1/10.
 */

public interface OKCallback {
    void doBefore(Method method, Object[] args);


    void doAfter(Method method, Object[] args);
}
