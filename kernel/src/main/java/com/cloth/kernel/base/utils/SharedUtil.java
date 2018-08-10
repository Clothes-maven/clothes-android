package com.cloth.kernel.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;

/**
 * SharedPreferences 工具类
 */
public class SharedUtil {
    private static SharedPreferences preferences;
    private static SharedUtil sharedUtil = null;

    public final static String APP_SP_CACHE = "lucky_store_sp_cache";


    private SharedUtil(Context context) {
        if (preferences == null) {
            context = context.getApplicationContext();
            preferences = context.getSharedPreferences(APP_SP_CACHE, Context.MODE_PRIVATE);
        }
    }

    public static void  init(Context context) {
        if (sharedUtil == null) {
            sharedUtil = new SharedUtil(context);
        }
    }

    public static String getString(String key) {
        return preferences.getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public static void saveString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public static int getInt(String key) {
        return preferences.getInt(key, -1);
    }

    public static int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public static void saveInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public static void saveBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public static float getFloat(String key) {
        return preferences.getFloat(key, 0f);
    }

    public static void saveFloat(String key, float value) {
        preferences.edit().putFloat(key, value).apply();
    }

    public static void saveLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    public static long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public static <T> void saveObject(String key, T clazz) {
        try {
            saveString(key, JSON.toJSONString(clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T getObject(String cacheKey, Class<T> clazz) {
        try {
            return JSON.parseObject(getString(cacheKey), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void remove(String key) {
        preferences.edit().remove(key).apply();
    }
}
