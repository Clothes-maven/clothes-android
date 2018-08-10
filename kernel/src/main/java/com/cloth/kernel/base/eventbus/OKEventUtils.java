package com.cloth.kernel.base.eventbus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xmq on 2018/1/10.
 */

 class OKEventUtils {

    /**
     * 注解解析
     * @param listenerClass 注解对象
     */
     static Map<Method, OKRegister.SubscribeInfo> loadAnnotatedMethods(Class<?> listenerClass) {
        Map<Method, OKRegister.SubscribeInfo> methodInfoMap = new ConcurrentHashMap<>();
        Method[] arr$ = listenerClass.getDeclaredMethods();//将所有的方法都取出来
         for (Method method : arr$) {
             Class[] parameterTypes;
             Class eventType;
             if (method.isAnnotationPresent(OKSubscribe.class)) {  //自定义注解获取方法
                 parameterTypes = method.getParameterTypes();

                 eventType = parameterTypes[0];
                 if (eventType.isInterface()) {
                     throw new IllegalArgumentException("Method " + method + " has @OKSubscribe annotation on " + eventType + " which is an interface.  Subscription must be on a concrete class type.");
                 }

                 if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                     throw new IllegalArgumentException("Method " + method + " has @OKSubscribe annotation on " + eventType + " but is not \'public\'.");
                 }

                 OKSubscribe subscribe = method.getAnnotation(OKSubscribe.class);
                 methodInfoMap.put(method, new OKRegister.SubscribeInfo(subscribe.thread()));
             }
         }
        return methodInfoMap;
    }
    /**
     * 获得event的所有接口
     * @param event
     * @return
     */
    @SuppressWarnings("unchecked")
    static ArrayList<Class<? extends IOKEvent>> getInterfaces(IOKEvent event) {
        Class<?>[] interfaces = event.getClass().getInterfaces();
        ArrayList<Class<? extends IOKEvent>> eventClass = new ArrayList<>();
        for (Class<?> in : interfaces) {
            if (isExtendsInterface(in, IOKEvent.class)) {
                eventClass.add((Class<? extends IOKEvent>) in);
            }
        }
        return eventClass;
    }


    /**
     * 判断接口是否继承自{@link IOKEvent}
     * @param in
     * @param superClass
     * @return
     */
    static boolean isExtendsInterface(Class<?> in, Class<?> superClass) {
        Class<?>[] subIns = in.getInterfaces();
        for (Class<?> subIn : subIns) {
            if (superClass.equals(subIn)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断注册者是否实现了{@link IOKEvent}
     * @param service
     * @param <T>
     */
     static <T> void validateInterface(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException(
                    "API declarations must be interfaces.");
        }
        if (!isExtendsInterface(service, IOKEvent.class)) {
            throw new IllegalArgumentException(
                    "API declarations must be extends IEvent.");
        }

    }
}
