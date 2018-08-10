package com.cloth.kernel.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理工厂类，暂时未使用
 * @param <T>
 */
public class ProxyFactory<T> {
    private T target;

    public ProxyFactory(T target) {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    public T getProxyInstance(final CallBack callBack){
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                callBack.before();
                Object returnVal = method.invoke(target,args);
                callBack.after();
                return returnVal;
            }
        });
    }


    @SuppressWarnings("unchecked")
    public static <T>  T getProxyInstance(final Class<T> service ,final T target ,final CallBack callBack){
        return (T) Proxy.newProxyInstance(service.getClassLoader(),new Class<?>[]{service}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                callBack.before();
                Object returnVal = method.invoke(target,args);
                callBack.after();
                return returnVal;
            }
        });
    }
    public interface CallBack {
        void before();
        void after();
    }
}
