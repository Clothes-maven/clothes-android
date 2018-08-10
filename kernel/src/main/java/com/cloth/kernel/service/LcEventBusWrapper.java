package com.cloth.kernel.service;


import com.cloth.kernel.base.eventbus.IOKEvent;
import com.cloth.kernel.base.eventbus.OKCallback;
import com.cloth.kernel.base.eventbus.OKEventBus;

/**
 * EventBus 的包装类
 * 暂时以接口的形式出现
 */
public class LcEventBusWrapper {

    private static volatile LcEventBusWrapper sLcEventBus;

    public static LcEventBusWrapper getInstance() {
        if (sLcEventBus == null) {
            synchronized (LcEventBusWrapper.class) {
                if (sLcEventBus == null) {
                    sLcEventBus = new LcEventBusWrapper();
                }
            }
        }
        return sLcEventBus;
    }


    /**
     * 注册Event次类必须继承自{@link IOKEvent}
     * 同时{@link #register(IOKEvent)}跟{@link #unregister(IOKEvent)}必须成对存在
     * 否则会造成内存泄漏
     * @param event event
     */
    public void register(IOKEvent event) {
        OKEventBus.register(event);
    }


    public void unregister(IOKEvent event) {
        OKEventBus.unregister(event);
    }

    /**
     * 全局回调方法,暂时不对外暴漏，留作统计时使用
     */
    void setCallBack(OKCallback callBack) {
        OKEventBus.setCallBack(callBack);
    }

    /**
     * 执行某事件方法，以接口的形式调用方法
     */
    public <T extends IOKEvent> T doEvent(Class<T> tClass) {
        return OKEventBus.doEvent(tClass);
    }

}
