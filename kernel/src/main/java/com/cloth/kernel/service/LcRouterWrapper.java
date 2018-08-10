package com.cloth.kernel.service;

import android.app.Application;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 路由包装类，对{@link ARouter} 的封装
 */
public class LcRouterWrapper {
    private static String SCHEME= "luckyClient";

    private volatile static LcRouterWrapper sLcRouterWrapper;

    public static LcRouterWrapper getLcRouterWrapper() {
        if (sLcRouterWrapper == null) {
            synchronized (LcRouterWrapper.class) {
                if (sLcRouterWrapper == null) {
                    sLcRouterWrapper = new LcRouterWrapper();
                }
            }
        }
        return sLcRouterWrapper;
    }

    private LcRouterWrapper() {
        if (LcAppWrapper.getLcAppWrapper().isDebug()) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init((Application) LcAppWrapper.getLcAppWrapper().getBaseContext());
    }

    /**
     * 以path跳转Activity
     */
    public void jumpActWithPath(@NonNull String path) {
        // 构建标准的路由请求
        ARouter.getInstance().build(path).navigation();
    }

    /**
     * 隐式跳转
     */
    public void jumpActWithUri(@NonNull Uri uri) {
        // 构建标准的路由请求
        ARouter.getInstance().build(uri).navigation();
    }

    /**
     * 根据{@link java.net.URI}解析并协议跳转
     * 可支持服务器下发Scheme、push、H5跳转
     * 注意：此处并非做的隐式跳转，区分{@link #jumpActWithUri(Uri)}隐式跳转
     * @param uriString 此处需要使用标准Uri scheme协议。
     * eg：{@code luckyClient://www.lucky.com/main/home?main_user=xmq&main_pass=123456}
     */
    public void jumpActParseUri (@NonNull String uriString) {
        parseUri(uriString);
    }

    /**
     * 跳转Activity并携带参数
     */
    public void jumpActWithBundle(@NonNull String path,@Nullable Bundle params) {
        // 直接传递Bundle
        ARouter.getInstance()
                .build(path)
                .with(params)
                .navigation();
    }

    @CheckResult
    public Fragment getFragment(@NonNull String path) {
        // 获取Fragment
        return (Fragment) ARouter.getInstance().build(path).navigation();
    }

    /**
     * 解析URI
     * luckyClient://www.lucky.com/main/home?user=xmq&pass=123456
     */
    private void parseUri(@NonNull String uriString) {
        Uri uri = Uri.parse(uriString);
        String scheme = uri.getScheme();
        if (!SCHEME.equals(scheme)) {
            return;
        }
        String path = uri.getPath();
        Bundle bundle = new Bundle();
        for (String param : uri.getQueryParameterNames()) {
            bundle.putString(param,uri.getQueryParameter(param));
        }
        ARouter.getInstance()
                .build(path)
                .with(bundle)
                .navigation();

    }
}
