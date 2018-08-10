package com.cloth.kernel.base.eventbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by xmq on 2018/1/9.
 * 使用动态代理实现最终调用
 */

class OKEventHandler {

    /**
     * 动态代理实现类
     */
    private Map<Class<? extends IOKEvent>, IOKEvent> mIOKEventMapProxy = new ConcurrentHashMap<>();

    /**
     * 注册的观察者
     */
    private Map<IOKEvent, OKRegister> mRegisterMap = new ConcurrentHashMap<>();

    /**
     * 主线程中的Handler
     */
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private OKCallback mOKCallback;
    /**
     * 来源于AsyncTask
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "EventHandler #" + mCount.getAndIncrement());
        }
    };
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>();
    private static final Executor THREAD_POOL_EXECUTOR
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
    /*vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv*/


    /**
     * 注册观察者
     */
    void register(IOKEvent subscriber) {

        if (mRegisterMap.containsKey(subscriber)) {
            return;
        }

        OKRegister okRegister = new OKRegister();
        okRegister.isRegister = true;
        okRegister.interfaceList = OKEventUtils.getInterfaces(subscriber);
        mRegisterMap.put(subscriber, okRegister);
    }

    /**
     * 取消观察者
     */
    void unRegister(IOKEvent register) {
        OKRegister remove = mRegisterMap.remove(register);
        remove.isRegister = false;
    }

    /**
     * 使用动态代理实现调用
     *
     * @param tClass {@link IOKEvent} 的子类
     * @return 注册者的接口代理类
     */
    @SuppressWarnings("unchecked")
    <T extends IOKEvent> T doEvent(final Class<T> tClass) {
        OKEventUtils.validateInterface(tClass);
        IOKEvent iokEventProxy = mIOKEventMapProxy.get(tClass);
        if (iokEventProxy == null) {
            synchronized (IOKEvent.class) {
                iokEventProxy = (IOKEvent) Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        for (Map.Entry<IOKEvent, OKRegister> entry : mRegisterMap.entrySet()) {
                            OKRegister value = entry.getValue();
                            if (value.interfaceList.contains(tClass)) {
                                if (mOKCallback != null) {
                                    mOKCallback.doBefore(method, args);
                                }
                                OKEventHandler.this.invoke(entry.getKey(), value, method, args);
                            }
                        }
                        return null;
                    }
                });
                mIOKEventMapProxy.put(tClass, iokEventProxy);
            }
        }
        return (T) iokEventProxy;
    }

    public void setOKCallback(OKCallback OKCallback) {
        this.mOKCallback = OKCallback;
    }

    /**
     * 根据注解确定执行线程
     */
    @SuppressWarnings("unchecked")
    private <T> void invoke(T subscriber, OKRegister register, Method method, Object[] args) {
        try {
            Map<Method, OKRegister.SubscribeInfo> map = register.methodInfoMap;
            getSubscribeInfo(map, subscriber, method);
            OKRegister.SubscribeInfo subscribeInfo = null;
            int threadMode;
            if (map != null) {
                subscribeInfo = map.get(method);
            }
            if (subscribeInfo == null) {
                threadMode = OKSubscribe.DEFAULT;
            } else {
                threadMode = subscribeInfo.threadMode;
            }

            switch (threadMode) {
                case OKSubscribe.DEFAULT:
                default:
                    method.invoke(subscriber, args);
                    if (mOKCallback != null) {
                        mOKCallback.doAfter(method, args);
                    }
                    break;
                case OKSubscribe.MAIN:
                    if (Looper.getMainLooper() == Looper.myLooper()) {
                        method.invoke(subscriber, args);
                        if (mOKCallback != null) {
                            mOKCallback.doAfter(method, args);
                        }
                    } else {
                        mHandler.post(new InvokeRunnable(subscriber, register, method, args,mOKCallback));
                    }
                    break;
                case OKSubscribe.ASYNC:
                    THREAD_POOL_EXECUTOR.execute(new InvokeRunnable(subscriber, register, method, args,mOKCallback));
                    break;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取方法的注解信息，信息有，在哪个线程执行threadMode，是否接收粘性消息sticky
     *
     * @param proxyMethod
     * @return
     */
    private <T> OKRegister.SubscribeInfo getSubscribeInfo(Map<Method, OKRegister.SubscribeInfo> map, T subscriber, Method proxyMethod) {
        OKRegister.SubscribeInfo subscribeInfo = map.get(proxyMethod);
        if (subscribeInfo == null) {
            synchronized (OKRegister.class) {
                subscribeInfo = map.get(proxyMethod);
                if (subscribeInfo == null) {
                    try {
                        Method rm = subscriber.getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
                        OKSubscribe annotation = rm.getAnnotation(OKSubscribe.class);
                        if (annotation == null) {
                            subscribeInfo = OKRegister.SubscribeInfo.NONE;
                        } else {
                            subscribeInfo = new OKRegister.SubscribeInfo(annotation.thread());
                        }
                        map.put(proxyMethod, subscribeInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return subscribeInfo;
    }

    /**
     * 执行方法的Runnable
     */
    private static class InvokeRunnable<T> implements Runnable {
        //执行的方法
        private final Method method;
        //参数
        private final Object[] args;
        //注册者，这里用弱引用
        private WeakReference<OKRegister> registerWeakReference;

        private WeakReference<T> subscriberWeakReference;

        private WeakReference<OKCallback> mCallbackWeakReference;

        InvokeRunnable(T subscriber, OKRegister register, Method method, Object[] args , OKCallback okCallback) {
            registerWeakReference = new WeakReference<>(register);
            subscriberWeakReference = new WeakReference<>(subscriber);
            mCallbackWeakReference = new WeakReference<>(okCallback);
            this.method = method;
            this.args = args;
        }

        @Override
        public void run() {
            try {
                OKRegister register = registerWeakReference.get();
                T iokEvent = subscriberWeakReference.get();
                if (register != null && register.isRegister && iokEvent != null) {
                    method.invoke(iokEvent, args);
                    OKCallback okCallback = mCallbackWeakReference.get();
                    if ( okCallback!= null) {
                        okCallback.doAfter(method, args);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
