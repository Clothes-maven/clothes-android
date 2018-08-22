package com.cloth.clothes.model;

import android.content.SharedPreferences;

import com.cloth.kernel.base.mvpclean.IDataRepository;
import com.cloth.kernel.base.utils.SharedUtil;

import java.lang.reflect.ParameterizedType;


public class LcBaseDataRepository implements IDataRepository {

    private static volatile LcBaseDataRepository sLcBaseRepository;

    public static LcBaseDataRepository getLcBaseRepository() {
        if (sLcBaseRepository == null) {
            synchronized (LcBaseDataRepository.class) {
                if (sLcBaseRepository == null) {
                    sLcBaseRepository = new LcBaseDataRepository();
                }
            }
        }
        return sLcBaseRepository;
    }


    /**
     * 以{@link SharedPreferences#edit()} 的方式保存数据
     *
     * @param key   key
     * @param value value
     * @param <T>   value数据类型
     */
    @Override
    public <T> void saveLocalData(String key, T value) {
        if (value instanceof String) {
            SharedUtil.saveString(key, (String) value);
        } else if (value instanceof Boolean) {
            SharedUtil.saveBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            SharedUtil.saveInt(key, (Integer) value);
        } else if (value instanceof Float) {
            SharedUtil.saveFloat(key, (Float) value);
        } else if (value instanceof Long) {
            SharedUtil.saveLong(key, (Long) value);
        } else {
            SharedUtil.saveObject(key, value);
        }
    }

    /**
     * 以{@code defaultVal}的数据类型取出数据，对应{@link #saveLocalData(String, Object)}
     * 使用{@link SharedPreferences#getString(String, String)}等的方式取
     *
     * @param key        key
     * @param defaultVal 默认值
     * @param <T>        取的数据的类型
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getLocalData(String key, T defaultVal) {
        if (defaultVal instanceof String) {
            return (T) SharedUtil.getString(key, (String) defaultVal);
        } else if (defaultVal instanceof Boolean) {
            return (T) Boolean.valueOf(SharedUtil.getBoolean(key, (Boolean) defaultVal));
        } else if (defaultVal instanceof Integer) {
            return (T) Integer.valueOf(SharedUtil.getInt(key, (Integer) defaultVal));
        } else if (defaultVal instanceof Float) {
            return (T) Float.valueOf(SharedUtil.getFloat(key)); //todo 默认值
        } else if (defaultVal instanceof Long) {
            return (T) Long.valueOf(SharedUtil.getLong(key, (Long) defaultVal));
        } else {
            if ((T) SharedUtil.getObject(key, defaultVal.getClass()) == null) {
                return defaultVal;
            }
            return (T) SharedUtil.getObject(key, defaultVal.getClass());
        }
    }
}
