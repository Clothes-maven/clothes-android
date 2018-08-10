package com.cloth.kernel.base.utils;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.cloth.kernel.service.LoggerWrapper;

/**
 * Created by zhanghongjun on 2017/7/11.
 */

public class ToastUtil {
    private volatile static Toast sToast;
    private static String TAG = "ToastUtil";

    private static Toast getToast(Context context) {
        if (sToast == null) {
            synchronized (ToastUtil.class) {
                if (sToast == null) {
                    sToast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
                }
            }
        }
        return sToast;
    }


    /**
     * Toast 短时间Toast
     * 可在UI线程 跟子线程执行
     *
     * @param context activity
     */
    public static void showShortMsg(Context context, String msg) {
        showMsg(context,msg, Toast.LENGTH_SHORT, null);
    }

    /**
     * Toast 长时间Toast
     * 可在UI线程 跟子线程执行
     *
     * @param context activity
     */
    public static void showLongMsg(Context context, String msg) {
        showMsg(context, msg, Toast.LENGTH_LONG, null);
    }


    /**
     * 自定义Toast ，可自定义View
     * 可在UI线程 跟子线程执行
     *
     * @param context  activity
     * @param duration {@link Toast#LENGTH_LONG} {@link Toast#LENGTH_SHORT}
     * @param view     自定义Toast 的View
     */
    public static void showCustomMsg(Context context, String msg, int duration, View view) {
        showMsg(context, msg, duration, view);
    }

    private static void showMsg(final Context context, final String msg, final int duration, final View toastView) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            toast(context, msg, duration, toastView).show();
        } else {
            LoggerWrapper.i("Toast exec in not  UI thread");
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    toast(context, msg, duration, toastView).show();
                }
            });
        }
    }

    @NonNull
    private static Toast toast(Context context, String msg, int duration, View toastView) {
        Toast toast = getToast(context.getApplicationContext());
        toast.setDuration(duration);
        toast.setText(msg);
        return toast;
    }
}