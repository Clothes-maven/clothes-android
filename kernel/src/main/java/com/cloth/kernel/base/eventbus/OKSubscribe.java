package com.cloth.kernel.base.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xmq on 2018/1/9.
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OKSubscribe {

    /**
     * 默认线程，当前的执行线程
     */
     int DEFAULT = 1;
    /**
     *主线程执行
     */
    int MAIN = 2;
    /**
     * 异步执行
     */
    int ASYNC = 3;

    int thread() default DEFAULT;
}
