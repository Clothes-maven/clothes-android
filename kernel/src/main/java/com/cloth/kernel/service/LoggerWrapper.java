package com.cloth.kernel.service;

import android.util.Log;

public class LoggerWrapper {
    private static ILog sILog = ILog.DEFAULT;
    private static boolean isDebug = true;

    public static void setILog(ILog ILog) {
        sILog = ILog;
    }

    public static void setIsDebug(boolean isDebug) {
        LoggerWrapper.isDebug = isDebug;
    }

    public static void i(String msg){
        if (sILog !=null && isDebug) {
            sILog.i(msg);
        }
    }

    public static void d(String msg){
        if (sILog !=null && isDebug) {
            sILog.d(msg);
        }
    }
    public static void w(String msg){
        if (sILog !=null && isDebug) {
            sILog.w(msg);
        }
    }
    public static void e(String msg){
        if (sILog !=null && isDebug) {
            sILog.e(msg);
        }
    }

    public static void wtf(Throwable tr){
        if (sILog !=null && isDebug) {
            sILog.wtf(tr);
        }
    }


    public interface ILog{
        void i(String msg);
        void d(String msg);
        void w(String msg);
        void e(String msg);
        void wtf(Throwable tr);

        ILog DEFAULT = new ILog() {
            private static final String TAG = "ILog";
            @Override
            public void i(String msg) {
                Log.i(TAG,msg);
            }

            @Override
            public void d(String msg) {
                Log.d(TAG,msg);
            }

            @Override
            public void w(String msg) {
                Log.w(TAG,msg);
            }

            @Override
            public void e(String msg) {
                Log.e(TAG,msg);
            }

            @Override
            public void wtf(Throwable tr) {
                Log.wtf(TAG,tr);
            }
        };
    }


}
