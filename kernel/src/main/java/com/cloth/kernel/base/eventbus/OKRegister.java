package com.cloth.kernel.base.eventbus;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xmq on 2018/1/10.
 * 数据处理类
 */

class OKRegister {

    List<Class<? extends IOKEvent>> interfaceList;

    boolean isRegister;
    /**
     * 方法的信息
     */
    Map<Method, SubscribeInfo> methodInfoMap = new ConcurrentHashMap<>();


    static class SubscribeInfo {
        /**
         * 在哪个线程执行threadMode
         */
        int threadMode;

         SubscribeInfo() {
            threadMode = OKSubscribe.DEFAULT;
        }

         SubscribeInfo(int threadMode) {
            this.threadMode = threadMode;
        }

         static final SubscribeInfo NONE = new SubscribeInfo();
    }
}
