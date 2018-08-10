package com.cloth.kernel.base.eventbus;

/**
 * Created by xmq on 2018/1/9.
 */

public class OKEventBus {
    /**
     * 使用单利模式，减少{@link OKEventBus}在各个module中的传递
     */
    private static OKEventHandler sOKEventHandler ;

    private static OKEventHandler getHandler() {
        if (sOKEventHandler ==null) {
            synchronized (OKEventBus.class) {
                if (sOKEventHandler ==null) {
                    sOKEventHandler = new OKEventHandler();
                }
            }
        }
        return sOKEventHandler;
    }


    /**
     * 注册观察者
     * @param event {@link IOKEvent 的子类}
     */
    public static void register(IOKEvent event){
        getHandler().register(event);
    }

    /**
     * 取消观察者，必须跟{@link #register(IOKEvent)}成对出现，否则会出现内存泄漏
     * @param event
     */
    public static void unregister(IOKEvent event) {
        getHandler().unRegister(event);
    }

    /**
     * 设置执行前后的回调，属全局回调，一般用来日志打印等等
     * @param callBack
     */
    public static void setCallBack(OKCallback callBack) {
        getHandler().setOKCallback(callBack);
    }
    /**
     * 获得{@link IOKEvent} 的执行类
     * @param tClass  继承自{@link IOKEvent}的class，名为 {@code *.class}
     */
    public  static  <T extends IOKEvent> T doEvent(Class<T> tClass){
       return getHandler().doEvent(tClass);
    }
}
