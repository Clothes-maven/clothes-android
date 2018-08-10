package com.cloth.kernel.service.http.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @param : api返回参数的类型
 * @创建时间: 2017/11/23
 * @负责人: 容芳志
 * @功能描述: 所有http请求request的基类
 * @页面进入路径: 无
 */
public abstract class BaseApiRequest implements Serializable {

    private ArrayList<String> noInRequestFields;

    /**
     * @return 返回HashMap<>作为Retrofit请求参数
     * @创建人 容芳志
     * @功能描述 通过反射获取当前request的成员变量为请求参数，添加签名和q参数加密
     */
    public HashMap<String, String> getRequsetParams() {
        //获取对象自己的属性
         return getKeyAndValue(this);
    }

    /**
     * @param obj 传入要获取成员变量为map的对象
     * @return 返回HashMap<>作为Retrofit请求参数
     * @创建人 容芳志
     * @功能描述 通过反射获取当前request的成员变量为请求参数
     */
    private HashMap<String, String> getKeyAndValue(Object obj) {
        HashMap<String, String> map = new HashMap<>();
        // 得到类对象
        Class userCla = obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            Object val = new Object();
            try {
                // 得到此属性的值
                val = f.get(obj);
                String fieldName = f.getName();

                //判断哪些字段不放到请求参数里。
                if (noInRequestFields != null && noInRequestFields.size() > 0) {
                    for (int j = 0; j < noInRequestFields.size(); j++) {
                        String noInField = noInRequestFields.get(j);
                        if (noInField.equals(fieldName)) {
                            continue;
                        }
                    }
                }

                map.put(fieldName, String.valueOf(val));// 设置键值
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }


    public void setNoInRequestFields(ArrayList<String> noInRequestFields) {
        this.noInRequestFields = noInRequestFields;
    }


}
