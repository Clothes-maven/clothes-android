package com.cloth.kernel.base.mvpclean;

/**
 * model的统一处理基类
 * 包含网络请求，{@link android.content.SharedPreferences} 存取等
 * 可单独定义此接口进行model控制
 */
public interface IDataRepository {

    <T> void saveLocalData(String key, T value);
    <T> void getLocalData(String key, T defaultVal);
    interface CallBack<R>{
        void success(R response);
        void error(@UseCase.CASE_TYPE int type, Throwable throwable);
    }
}
