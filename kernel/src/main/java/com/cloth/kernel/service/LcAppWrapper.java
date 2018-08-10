package com.cloth.kernel.service;

import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cloth.kernel.BuildConfig;
import com.cloth.kernel.base.utils.SharedUtil;

import java.util.List;


/**
 * Application context 的包装类
 */
public class LcAppWrapper extends ContextWrapper {

    private volatile static LcAppWrapper sLcAppWrapper;

    /**
     * debug的调试字段，应用在全局,请使用{@link #isDebug()}，
     * 禁止使用{@code BuildConfig.LOG_DEBUG}等变量
     */
    private boolean IS_DEBUG;

    public static void createAppWrapper(Context context , boolean isDebug) {
        if (sLcAppWrapper == null) {
            sLcAppWrapper = new LcAppWrapper(context,isDebug);
        }
    }

    public boolean isDebug() {
        return IS_DEBUG;
    }

    public static LcAppWrapper getLcAppWrapper() {
        return sLcAppWrapper;
    }

    public LcAppWrapper(Context base, boolean isDebug) {
        super(base);
        this.IS_DEBUG = isDebug;
        initShareUtils(base);
    }


    public static void initShareUtils(Context context) {
        SharedUtil.init(context);
    }

    /**
     * 判断是不是UI主进程，因为有些东西只能在UI主进程初始化
     */
    private static boolean isAppMainProcess(@NonNull Context context) {
        String processName = "";
        int myPid = android.os.Process.myPid();
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = null;
        if (activityManager != null) {
            runningProcesses = activityManager.getRunningAppProcesses();
        }
        if (runningProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo process : runningProcesses) {
                if (process.pid == myPid) {
                    processName = process.processName;
                }
            }
        }
        return TextUtils.isEmpty(processName) || BuildConfig.APPLICATION_ID.equalsIgnoreCase(processName);
    }

}
